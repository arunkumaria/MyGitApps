package com.own.service.impl;

import org.springframework.stereotype.Service;

import com.own.payment.dto.TransactionDto;
import com.own.service.status.factory.PaymentStatusFactory;
import com.own.service.status.processor.interfaces.TransactionStatusProcessor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentStatusService {
	
	
	private final PaymentStatusFactory paymentStatusFactory;
	
	
	public TransactionDto processPayment(TransactionDto transactionDto) throws Exception {
		
		int statusId = transactionDto.getTxnStatusId();
		TransactionStatusProcessor transactionStatusProcessor = paymentStatusFactory.getStatusProcessor(statusId);
		
		if(transactionStatusProcessor == null) {
			throw new Exception();
		}
		
		TransactionDto transactionDtoResponse = transactionStatusProcessor.processStatus(transactionDto);
		
		return transactionDtoResponse;
		
	}

}
