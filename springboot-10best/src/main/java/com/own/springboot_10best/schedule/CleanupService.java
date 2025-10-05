package com.own.springboot_10best.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanupService {
	@Scheduled(cron = "0 */1 * * * *")
	public void cleanup() {
		System.out.println("[SCHEDULED] Cleanup running...");
	}
}