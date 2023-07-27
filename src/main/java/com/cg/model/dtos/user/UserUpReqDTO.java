package com.cg.model.dtos.user;

import com.cg.model.Role;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpReqDTO {
    private String username;

    private String password;

    public User toUser(Long userId, Role role) {
        return new User()
                .setId(userId)
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                ;
    }
}
