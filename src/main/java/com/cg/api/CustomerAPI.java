package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.Doctor;
import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.dtos.appointment.AppointmentResDTO;
import com.cg.model.dtos.customer.CustomerCreReqDTO;
import com.cg.model.dtos.customer.CustomerResDTO;
import com.cg.model.dtos.customer.CustomerUpReqDTO;
import com.cg.model.dtos.doctor.DoctorResDTO;
import com.cg.model.dtos.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.dtos.locationRegion.LocationRegionUpReqDTO;
import com.cg.model.enums.EGender;
import com.cg.service.customer.ICustomerService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    AppUtils appUtils;

    @Autowired
    IUserService userService;

    @Autowired
    ICustomerService customerService;


    @GetMapping
    public ResponseEntity<?> getAll(){
        List<CustomerResDTO> customerResDTOS = new ArrayList<>();
        List<Customer> customers = customerService.findAll();

        for (Customer customer: customers){
            CustomerResDTO customerResDTO = customer.toCustomerResDTO();
            customerResDTOS.add(customerResDTO);
        }

        return new ResponseEntity<>(customerResDTOS,HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId).orElseThrow(() -> {
            throw new DataInputException("Mã khách hàng không tồn tại");
        });
        CustomerResDTO customerResDTO = customer.toCustomerResDTO();
        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerCreReqDTO customerCreReqDTO,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            appUtils.mapErrorToResponse(bindingResult);
        }

        String eGenderName = customerCreReqDTO.getNameGender();
        EGender eGender;
        try {
            eGender = EGender.valueOf(eGenderName);
        }catch (IllegalArgumentException e){
            throw new DataInputException("Giới tính không tồn tại");
        }

        LocationRegionCreReqDTO locationRegionCreReqDTO = customerCreReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionCreReqDTO.toLocationRegion();

        Long userid = Long.parseLong(customerCreReqDTO.getUserId());
        User user = userService.findById(userid).orElseThrow(() -> new DataInputException("Ngưới dùng không tồn tại"));

        Customer customer = customerCreReqDTO.toCustomer(eGender, user);
        Customer newCustomer = customerService.create(locationRegion, customer);

        CustomerResDTO customerResDTO = newCustomer.toCustomerResDTO();
        return new ResponseEntity<>(customerResDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<?> update(@PathVariable("customerId") String customerIdStr,
                                    @RequestBody CustomerUpReqDTO customerUpReqDTO,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            appUtils.mapErrorToResponse(bindingResult);
        }

        if (!ValidateUtil.isNumberValid(customerIdStr)){
            Map<String, String> data = new HashMap<>();
            data.put("message", "Mã khách hàng không đúng định dạng");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Long customerId = Long.parseLong(customerIdStr);
        Customer customer = customerService.findById(customerId).orElseThrow(()->{
            throw new DataInputException("Khách hàng không tồn tại");
        });

        Long locationRegionId = customer.getLocationRegion().getId();
        User user = customer.getUser();

        String eGenderName = customerUpReqDTO.getNameGender();
        EGender eGender;
        try {
            eGender = EGender.valueOf(eGenderName);
        }catch (IllegalArgumentException e){
            throw new DataInputException("Giới tính không tồn tại");
        }

        LocationRegionUpReqDTO locationRegionUpReqDTO = customerUpReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionUpReqDTO.toLocationRegion(locationRegionId);

        Customer updatedCustomer = customerUpReqDTO.toCustomer(eGender,user,customerId,locationRegionId);
        Customer newCustomer = customerService.create(locationRegion, updatedCustomer);
        CustomerResDTO customerResDTO = newCustomer.toCustomerResDTO();

        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }
}
