package com.user_service.service;


import com.user_service.entity.Users;
import com.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

}
