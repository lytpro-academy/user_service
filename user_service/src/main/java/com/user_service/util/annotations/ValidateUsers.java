package com.user_service.util.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateUsers {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
    String message() default "Field value is not valid";
}
