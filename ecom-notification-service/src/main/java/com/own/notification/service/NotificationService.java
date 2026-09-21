package com.own.notification.service;

import java.util.List;
import java.util.UUID;

import com.own.notification.dto.NotificationRequest;
import com.own.notification.dto.NotificationResponse;

public interface NotificationService {

	NotificationResponse createNotification(NotificationRequest request);

	NotificationResponse getNotification(UUID id);

	List<NotificationResponse> getNotificationsByCustomer(UUID customerId);

	NotificationResponse sendNotification(UUID id);

	void deleteNotification(UUID id);
}