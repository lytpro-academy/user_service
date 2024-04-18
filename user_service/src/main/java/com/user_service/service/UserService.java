package com.user_service.service;


import com.user_service.entity.Users;
import com.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createOrUpdateUser(Users user) {
        return userRepository.save(user);
    }

    public Optional<Users> getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
