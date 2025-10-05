package com.own.springboot_10best.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Async
	public void sendEmail(String to) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignored) {
		}
		System.out.println("[ASYNC] Email sent to: " + to + " by " + Thread.currentThread().getName());
	}
}