package com.user_service;

import com.user_service.event.producer.UserEventProducer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
@Log
public class EventScheduler implements ApplicationListener<ContextRefreshedEvent> {

    private final UserEventProducer userEventProducer;
    private final ScheduledExecutorService executorService;

    @Autowired
    public EventScheduler(UserEventProducer userEventProducer) {
        this.userEventProducer = userEventProducer;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ScheduledFuture<?> scheduledFuture = this.executorService.schedule(this::executeTask, 5, TimeUnit.MINUTES);

        // to close executor after scheduling
        executorService.shutdown();
    }

    private void executeTask() {
        try {
            userEventProducer.sendEvent();
            log.info("Event sent at: " + Instant.now());
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }
}