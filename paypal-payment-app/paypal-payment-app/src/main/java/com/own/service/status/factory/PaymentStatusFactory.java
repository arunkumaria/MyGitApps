package com.own.service.status.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.own.service.status.processor.impl.ApprovedStatusProcessor;
import com.own.service.status.processor.impl.CreatedStatusProcessor;
import com.own.service.status.processor.impl.InitiatedStatusProcessor;
import com.own.service.status.processor.impl.PendingStatusProcessor;
import com.own.service.status.processor.impl.SuccessStatusProcessor;
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
