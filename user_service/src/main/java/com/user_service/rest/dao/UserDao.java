package com.user_service.rest.dao;

import com.user_service.entity.Users;
import com.user_service.rest.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    public Users mapRequestToUserEntity(UserDto userDto) {
        return Users.builder().
                name(userDto.getName()).
                age(userDto.getAge()).
                occupation(userDto.getOccupation()).
                createdAt(userDto.getCreatedAt()).
                updatedAt(userDto.getUpdatedAt()).build();
    }
}
