package com.own.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;

@Service
public class EmailReadService {

	@Value("${mail.imap.host}")
	private String host;

	@Value("${mail.imap.port}")
	private int port;

	@Value("${mail.imap.username}")
	private String username;

	@Value("${mail.imap.password}")
	private String password;

	public void readEmails() {

		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");

		try {
			Session session = Session.getDefaultInstance(properties);
			Store store = session.getStore("imaps");

			store.connect(host, port, username, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			Message[] messages = inbox.getMessages();

			System.out.println("Total Emails: " + messages.length);

			for (Message message : messages) {
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Subject: " + message.getSubject());
				System.out.println("Received Date: " + message.getReceivedDate());
				System.out.println("---------------------------");
			}

			inbox.close(false);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
