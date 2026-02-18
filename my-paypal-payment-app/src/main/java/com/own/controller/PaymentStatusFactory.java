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
		case 2:

			return applicationContext.getBean(InitiatedStatusProcessor.class);
		case 3:

			return applicationContext.getBean(PendingStatusProcessor.class);

		case 4:

			return applicationContext.getBean(ApprovedStatusProcessor.class);

		case 5:

			return applicationContext.getBean(SuccessStatusProcessor.class);

		default:
			return null;
		}
	}

}
