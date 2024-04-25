package com.user_service.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.UserServiceApplication;
import com.user_service.entity.Users;
import com.user_service.rest.dto.UserDto;
import com.user_service.service.UserService;
import com.user_service.util.exception.UserException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = UserServiceApplication.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void createOrSaveUser() throws Exception{
        when(userService.createOrUpdateUser(any())).thenReturn(Users.builder().name("Tom").age(12).occupation("Dev").build());
        mvc.perform(MockMvcRequestBuilders.
                        post("/api/user").contentType(MediaType.APPLICATION_JSON_VALUE).
                        content(new ObjectMapper().writeValueAsString(UserDto.builder().name("Tom").age(12).occupation("Dev").build())).
                        accept(MediaType.APPLICATION_JSON_VALUE)).
                andDo(print()).
                andExpect(status().isCreated()).
                andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(4)));
    }

    @Test
    void getById_Success() throws Exception {
        // Mocking a successful scenario where user with id exists
        int userId = 1;
        Users user = Users.builder().id(userId).name("John").age(30).occupation("Engineer").build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value("Engineer"));
    }

    @Test
    void getById_UserNotFound() throws Exception {
        // Mocking scenario where user with id does not exist
        int userId = 999; // User with this ID does not exist
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUser_Success() throws Exception {
        // Mocking a successful scenario where users exist
        List<Users> users = Arrays.asList(
                Users.builder().id(1).name("John").age(30).occupation("Engineer").build(),
                Users.builder().id(2).name("Alice").age(25).occupation("Doctor").build()
        );
        when(userService.getAllUser()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Alice"));
    }

    @Test
    void updateUser_UserNotFound() throws Exception {
        // Mocking scenario where user with id does not exist
        int userId = 999; // User with this ID does not exist
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Trying to update a non-existing user
        String updatedName = "Updated John";
        String updatedOccupation = "Updated Engineer";
        int updatedAge = 35;

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/user/{id}", userId)
                        .param("name", updatedName)
                        .param("occupation", updatedOccupation)
                        .param("age", String.valueOf(updatedAge))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_Success() throws Exception {
        // Mocking a successful scenario where user with id exists
        int userId = 1;
        doNothing().when(userService).deleteUserById(userId);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));
    }

    @Test
    void deleteUser_UserNotFound() throws Exception {
        // Mocking scenario where user with id does not exist
        int userId = 999; // User with this ID does not exist
        doThrow(new UserException("User not found with id: " + userId)).when(userService).deleteUserById(userId);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}