package com.own.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscriber implements MessageListener {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {

		String notification = new String(message.getBody());

		messagingTemplate.convertAndSend("/topic/notifications", notification);

	}
}