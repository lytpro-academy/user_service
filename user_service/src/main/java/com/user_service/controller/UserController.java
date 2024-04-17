package com.user_service.controller;

import com.user_service.entity.Users;
import com.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "APIs for User management")
public class UserController {

    private final UserService userService;
    Users users = new Users();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @Operation(summary = "Create user", description = "This api is creating Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Users> createUsers() {
        //users.setId(1);
        users.setName("John Doe");
        users.setAge(23);
        users.setOccupation("Engineer");
        users = userService.createUser(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "This will provide all the users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<List<Users>> getAllUser() {
        List<Users> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update user", description = "This will update the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Users> updateUser() {
        users.setName("David");
        users = userService.updateUser(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Delete User", description = "This will delete specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser(users.getId());
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }


}
