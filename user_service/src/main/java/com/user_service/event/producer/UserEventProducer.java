package com.user_service.event.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class UserEventProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendEvent() throws Exception {
        // Create a list of 2 users
        List<Users> userList = IntStream.range(0, 2)
                .mapToObj(i -> new Users(1,"name" + i, "occupation" + i, i + 20))
                .collect(Collectors.toList());

        // Convert list of users to JSON string
        String json = objectMapper.writeValueAsString(userList);

        rabbitTemplate.convertAndSend("user_exchange", "user_routing_key", json);
    }
}