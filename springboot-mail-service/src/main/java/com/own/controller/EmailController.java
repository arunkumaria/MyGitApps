package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.service.EmailReadService;
import com.own.service.EmailService;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

	private final EmailService emailService;
	private final EmailReadService emailReadService;

	public EmailController(EmailService emailService, EmailReadService emailReadService) {
		this.emailService = emailService;
		this.emailReadService = emailReadService;

	}

	@PostMapping("/send")
	public String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {

		emailService.sendSimpleEmail(to, subject, body);
		return "Email sent successfully";
	}

	@GetMapping("/read")
	public String readInbox() {
		emailReadService.readEmails();
		return "Emails fetched successfully. Check logs.";
	}
}
