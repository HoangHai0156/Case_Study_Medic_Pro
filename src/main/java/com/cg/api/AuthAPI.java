package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.*;
import com.cg.model.dtos.user.UserCreReqDTO;
import com.cg.model.dtos.user.UserRegisterReqDTO;
import com.cg.model.dtos.user.UserResDTO;
import com.cg.repository.UserRepository;
import com.cg.service.jwt.JwtService;
import com.cg.service.role.IRoleService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AppUtils appUtils;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterReqDTO userRegisterReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Boolean existsByUsername = userService.existsByUsername(userRegisterReqDTO.getUsername());

        if (existsByUsername) {
            throw new EmailExistsException("Account already exists");
        }
        String password = userRegisterReqDTO.getPassword();
        String rePassword = userRegisterReqDTO.getRePassword();

        if (!Objects.equals(password, rePassword)) {
            throw new DataInputException("Mật khẩu không trùng khớp");
        }
        Optional<Role> optRole = roleService.findById(userRegisterReqDTO.getRoleId());

        if (!optRole.isPresent()) {
            throw new DataInputException("Invalid account role");
        }
        try {
            userService.save(userRegisterReqDTO.toUser(optRole.get()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.getByUsername(user.getUsername());

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(1000L * 60 * 60 * 24 * 30)
                    .domain("localhost")
                    .build();

            System.out.println(jwtResponse);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserResDTO> userResDTOList = new ArrayList<>();
        List<User> users = userService.findAll();

        for (User user : users) {
            UserResDTO userResDTO = user.toUserResDTO();
            userResDTOList.add(userResDTO);
        }
        return new ResponseEntity<>(userResDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreReqDTO userCreReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Map<String, String> data = new HashMap<>();

        String username = userCreReqDTO.getUsername().trim();
        if (userService.existsByUsername(username)) {
            data.put("message", "Username đã tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        Long roleId = Long.valueOf(userCreReqDTO.getRoleId());
        Role role = roleService.findById(roleId).orElseThrow(() -> {
            throw new DataInputException("Role không tồn tại");
        });
        User user = userCreReqDTO.toUser(role);
        User newUser = userService.save(user);
        UserResDTO userResDTO = newUser.toUserResDTO();
        return new ResponseEntity<>(userResDTO, HttpStatus.CREATED);
    }

}