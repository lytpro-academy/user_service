package com.user_service.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
@Builder
public class UserDto {
    @NonNull
    @NotEmpty
    private String name; // 12TOm

    private String occupation;

    @NonNull
    private Integer age;
}
