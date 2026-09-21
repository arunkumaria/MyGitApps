package com.own.notification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.notification.dto.NotificationRequest;
import com.own.notification.dto.NotificationResponse;
import com.own.notification.entity.Notification;
import com.own.notification.entity.NotificationStatus;
import com.own.notification.exception.NotificationNotFoundException;
import com.own.notification.repository.NotificationRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private final NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {

		this.notificationRepository = notificationRepository;
	}

	@Override
	@CachePut(value = "notifications", key = "#result.id")
	public NotificationResponse createNotification(NotificationRequest request) {

		log.info("Creating notification for customerId={}", request.getCustomerId());

		Notification notification = new Notification();

		notification.setCustomerId(request.getCustomerId());

		notification.setRecipient(request.getRecipient());

		notification.setSubject(request.getSubject());

		notification.setMessage(request.getMessage());

		notification.setType(request.getType());

		notification.setStatus(NotificationStatus.PENDING);

		Notification saved = notificationRepository.save(notification);

		log.info("Notification created successfully. notificationId={}", saved.getId());

		return mapToResponse(saved);
	}

	@Override
	@Cacheable(value = "notifications", key = "#id")
	@Transactional(readOnly = true)
	public NotificationResponse getNotification(UUID id) {

		log.info("Fetching notification. notificationId={}", id);

		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new NotificationNotFoundException("Notification not found: " + id));

		return mapToResponse(notification);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotificationResponse> getNotificationsByCustomer(UUID customerId) {

		return notificationRepository.findByCustomerId(customerId).stream().map(this::mapToResponse).toList();
	}

	@Override
	@CachePut(value = "notifications", key = "#id")
	public NotificationResponse sendNotification(UUID id) {

		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new NotificationNotFoundException("Notification not found: " + id));

		log.info("Sending notification. notificationId={}, type={}", id, notification.getType());

		/*
		 * Actual email/SMS/push provider integration will be added here.
		 */

		notification.setStatus(NotificationStatus.SENT);

		notification.setSentAt(LocalDateTime.now());

		Notification saved = notificationRepository.save(notification);

		log.info("Notification sent successfully. notificationId={}", id);

		return mapToResponse(saved);
	}

	@Override
	@CacheEvict(value = "notifications", key = "#id")
	public void deleteNotification(UUID id) {

		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new NotificationNotFoundException("Notification not found: " + id));

		notificationRepository.delete(notification);

		log.info("Notification deleted. notificationId={}", id);
	}

	private NotificationResponse mapToResponse(Notification notification) {

		NotificationResponse response = new NotificationResponse();

		response.setId(notification.getId());
		response.setCustomerId(notification.getCustomerId());
		response.setRecipient(notification.getRecipient());
		response.setSubject(notification.getSubject());
		response.setMessage(notification.getMessage());
		response.setType(notification.getType());
		response.setStatus(notification.getStatus());
		response.setCreatedAt(notification.getCreatedAt());
		response.setSentAt(notification.getSentAt());
		response.setUpdatedAt(notification.getUpdatedAt());

		return response;
	}
}
