package com.company.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO implements Serializable {

    @NotBlank(message = "Username can not be blank")
    private String username;

    @NotBlank(message = "Email can not be blank")
    private String email;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @NotBlank(message = "Confirm Password can not be blank")
    private String confirmPassword;

}
