package com.user_service.util.annotations.impl;

import com.user_service.entity.Users;
import com.user_service.util.annotations.ValidateUsers;

import java.lang.reflect.Field;

public class UserAgeValidator {
    public static void validateUserAge(Users user) throws IllegalAccessException {
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(user);
            if (field.getName().equals("age") && value instanceof Integer) {
                int age = (int) value;
                if (age < 18 || age > 100) {
                    throw new IllegalArgumentException("Age must be between 18 and 100");
                }
            } else if (value instanceof String) {
                String fieldValue = (String) value;
                if (fieldValue == null || fieldValue.trim().isEmpty()) {
                    throw new IllegalArgumentException("Field '" + field.getName() + "' cannot be null or empty");
                }
            }
        }
    }
}
