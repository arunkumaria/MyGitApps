package com.own.service.status.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.own.service.status.processor.impl.CreatedStatusProcessor;
import com.own.service.status.processor.interfaces.TransactionStatusProcessor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStatusFactory {

	private final ApplicationContext applicationContext;

	public TransactionStatusProcessor getStatusProcessor(int txnStatusId) {

		switch (txnStatusId) {
		case 1:
			return applicationContext.getBean(CreatedStatusProcessor.class);

		default:
			return null;
		}

	}

}
