package com.company.dtos;

import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record TokenRequest(@NonNull String username, @NonNull String password) {
}
