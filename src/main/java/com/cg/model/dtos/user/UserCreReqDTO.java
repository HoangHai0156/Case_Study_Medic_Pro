package com.cg.model.dtos.user;
import com.cg.model.Role;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreReqDTO implements Validator {

    private String username;

    private String password;

    private String roleId;

    public User toUser(Role role) {
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                ;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreReqDTO userCreReqDTO= (UserCreReqDTO) target;
        String username = userCreReqDTO.username;
        String password = userCreReqDTO.password;
        if (username == null || username.trim().length() == 0) {
            errors.rejectValue("username", "username.length", "Tên đăng nhập không được để trống");
        } else {
                if (username.trim().length() < 6 || username.trim().length() > 20) {
                    errors.rejectValue("username", "username.length", "Tên đăng nhập 6-20 ký tự");
                }
        }
        if (password == null || password.trim().length() == 0) {
            errors.rejectValue("password", "password.length", "Password không được để trống");
        } else {
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9\\s]).{8,}$")) {
                errors.rejectValue("password", "password.number", "Password phải chứa chữ hoa, chữ thường, " +
                        "số và ký tự đặc biệt, không được chứa khoảng trắng, độ dài 8 ký tự trở lên");
            }
        }
    }
}
