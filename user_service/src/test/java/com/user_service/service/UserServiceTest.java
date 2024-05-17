package com.user_service.service;

import com.user_service.entity.Users;
import com.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createOrUpdateUser() {
        Users user = Users.builder().name("Tom").age(12).occupation("Dev").createdAt(new Date()).updatedAt(new Date()).build();
        when(userRepository.save(user)).thenReturn(user);

        Users savedUser = userService.createOrUpdateUser(user);
        verify(userRepository, times(1)).save(user);
        assertNotNull(savedUser);
        assertEquals("Tom", savedUser.getName());
    }
}