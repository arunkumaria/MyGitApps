package com.own.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStatusFactory {

	private final ApplicationContext applicationContext;

	public TransactionStatusProcessor getStatusProcessor(int statusId) {
		switch (statusId) {
		case 1:

			return applicationContext.getBean(CreatedStatusProcessor.class);

		default:
			return null;
		}
	}

}
