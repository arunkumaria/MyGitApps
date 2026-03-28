package com.own.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.entity.Notification;
import com.own.entity.Status;
import com.own.model.EmailRequest;
import com.own.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper; // for converting Map -> JSON

    public String queueEmail(EmailRequest request) {
        String payloadJson;
        try {
            payloadJson = objectMapper.writeValueAsString(request.getData());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize email payload", e);
        }

        Notification entity = Notification.builder()
                .id(UUID.randomUUID())
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .templateName(request.getTemplate())
                .payload(payloadJson)
                .status(Status.PENDING)   // use enum, not string
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(entity);

        rabbitTemplate.convertAndSend("email.queue", entity.getId());

        return entity.getId().toString();
    }
}