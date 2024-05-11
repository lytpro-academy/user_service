package com.user_service.event.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.entity.Users;
import com.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;


@Component
@AllArgsConstructor
@Log
public class UserEventConsumer {
    private final UserRepository usersRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "user_queue")
    public void receiveEvent(String event) {
        // Process the received event
        log.info("consumer event--> "+ event);

        List<Users> usersList = null;
        try {
            // Parse JSON string to list of Users
            usersList = objectMapper.readValue(event, new TypeReference<List<Users>>() {});

            // Save users to the database
            usersRepository.saveAll(usersList);
            log.info("<--users are created-->"+ usersList);
        } catch (JsonProcessingException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }
}
