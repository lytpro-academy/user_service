package com.user_service.rest.controller;

import com.user_service.entity.Users;
import com.user_service.rest.dao.UserDao;
import com.user_service.rest.dto.UserDto;
import com.user_service.service.UserService;
import com.user_service.util.exception.UserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "APIs for User management")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDao userDao;

    @PostMapping()
    @Operation(summary = "Create user", description = "This api is creating Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Users> createUsers(@RequestBody UserDto userDto) {
        Users user = userDao.mapRequestToUserEntity(userDto);
        user = userService.createOrUpdateUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get all users", description = "This will provide all the users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Users> getById(@PathVariable Integer id) {
        Users user = userService.getUserById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
        return new ResponseEntity<>(user, HttpStatus.OK);
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

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "This will update the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestParam String name, @RequestParam String occupation, @RequestParam Integer age) {
        Optional<Users> users = userService.getUserById(id);
        if (users.isPresent()) {
            Users user = users.get();
            user.setName(name);
            user.setOccupation(occupation);
            user.setAge(age);
            user = userService.createOrUpdateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            throw new UserException("User not found with id: " + id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "This will delete specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Users> patchUser(@PathVariable Integer id, @RequestBody  UserDto userDto) {
        Optional<Users> users = userService.getUserById(id);
        if (users.isPresent()) {
            Users user = users.get();
            user.setName(userDto.getName());
            if (userDto.getOccupation() != null) user.setOccupation(userDto.getOccupation());
            user.setAge(userDto.getAge());
            user = userService.createOrUpdateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else throw new UserException("User not found with id: " + id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public void checkUserExistence(@PathVariable int id) {
        Optional<Users> users = userService.getUserById(id);
        if (!users.isPresent()) {
            throw new RuntimeException("User Doesnt exist");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public String allowedMethods() {
        return "Allowed methods: GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.TRACE)
    public String echoRequest(@PathVariable int id, @RequestBody String body) {
        return "Echoing request for user with id " + id + ": " + body;
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserException ex) {
        return ex.getMessage();
    }
}
