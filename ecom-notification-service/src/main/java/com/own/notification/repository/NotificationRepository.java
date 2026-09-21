package com.own.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.own.notification.entity.Notification;
import com.own.notification.entity.NotificationStatus;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

	List<Notification> findByCustomerId(UUID customerId);

	List<Notification> findByStatus(NotificationStatus status);
}