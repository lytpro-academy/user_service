package com.user_service.rest.dto;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NonNull;

@Data
@Valid
public class UserDto {
    @NonNull
    private String name;

    private String occupation;

    @NonNull
    private Integer age;
}
