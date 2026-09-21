package com.own.notification.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.own.notification.dto.NotificationRequest;
import com.own.notification.dto.NotificationResponse;
import com.own.notification.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {

		this.notificationService = notificationService;
	}

	@PostMapping
	public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<NotificationResponse> getNotification(@PathVariable UUID id) {

		return ResponseEntity.ok(notificationService.getNotification(id));
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<NotificationResponse>> getCustomerNotifications(@PathVariable UUID customerId) {

		return ResponseEntity.ok(notificationService.getNotificationsByCustomer(customerId));
	}

	@PostMapping("/{id}/send")
	public ResponseEntity<NotificationResponse> sendNotification(@PathVariable UUID id) {

		return ResponseEntity.ok(notificationService.sendNotification(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {

		notificationService.deleteNotification(id);

		return ResponseEntity.noContent().build();
	}
}