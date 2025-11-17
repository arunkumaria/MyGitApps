package com.own.payments.service.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.own.payments.service.impl.statusprocessor.CreatedStatusProcessor;
import com.own.payments.service.impl.statusprocessor.InitiatedStatusProcessor;
import com.own.payments.service.impl.statusprocessor.PendingStatusProcessor;
import com.own.payments.service.interfaces.TransactionStatusProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentStatusFactory {
	
	private final ApplicationContext applicationContext;
	
	public TransactionStatusProcessor getStatusProcessor(int statusId) {
		log.info("Getting status processor for statusId: {}", statusId);
		
		//switch based on statusId. If statusId = 1, return CreatedStatusProcessor object.
		switch (statusId) {
		case 1:
			log.info("Returning CreatedStatusProcessor for statusId: {}", statusId);
			return applicationContext.getBean(CreatedStatusProcessor.class);
		case 2:
			log.info("Returning InitiatedStatusProcessor for statusId: {}", statusId);
			return applicationContext.getBean(InitiatedStatusProcessor.class);
			
		case 3:
			log.info("Returning PendingStatusProcessor for statusId: {}", statusId);
			return applicationContext.getBean(PendingStatusProcessor.class);
		
		default:
			log.warn("No processor found for statusId: {}", statusId);
			return null;
		}
		
	}

}
