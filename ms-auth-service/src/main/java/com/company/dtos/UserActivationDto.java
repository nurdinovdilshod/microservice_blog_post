package com.company.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserActivationDto(@NotBlank(message = "Username can not be blank") String username,
                                @NotBlank(message = "Code can not be blank") Integer code) {
}
