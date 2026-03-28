package com.own.worker;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.entity.Notification;
import com.own.entity.Status;
import com.own.repository.NotificationRepository;
import com.own.service.TemplateService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailWorker {

    private final NotificationRepository repository;
    private final TemplateService templateService;
    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "email.queue")
    public void processEmail(UUID notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + notificationId));

        try {
            // Convert payload JSON -> Map
            Map<String, Object> data = objectMapper.readValue(notification.getPayload(), new TypeReference<Map<String, Object>>() {});

            // Render HTML content from template
            String htmlContent = templateService.renderTemplate(notification.getTemplateName(), data);

            // Send email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getRecipient());
            message.setSubject(notification.getSubject());
            message.setText(htmlContent); // For plain text; can switch to MimeMessage for HTML

            mailSender.send(message);

            // Update notification status
            notification.setStatus(Status.SENT);
            notification.setSentAt(LocalDateTime.now());
            repository.save(notification);

        } catch (Exception e) {
            notification.setStatus(Status.FAILED);
            notification.setError(e.getMessage());
            repository.save(notification);
            e.printStackTrace(); // or use logger
        }
    }
}