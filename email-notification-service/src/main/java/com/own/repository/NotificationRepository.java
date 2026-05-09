package com.own.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.own.entity.Notification;
import com.own.entity.Status;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // 🔍 Find by status (for retries, monitoring)
    List<Notification> findByStatus(Status status);

    // 🔍 Find all failed notifications
    List<Notification> findByStatusOrderByCreatedAtDesc(Status status);

    // 🔍 Find by recipient
    List<Notification> findByRecipient(String recipient);

    // 🔍 Find recent notifications
    List<Notification> findTop10ByOrderByCreatedAtDesc();

}
