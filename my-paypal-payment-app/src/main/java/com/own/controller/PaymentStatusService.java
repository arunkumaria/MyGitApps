package com.own.controller;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentStatusService {
	
	private final PaymentStatusFactory paymentStatusFactory;
	
	
	
	public TransactionDto processPayment(TransactionDto transactionDto) throws Exception {
		
		int txnStatusId = transactionDto.getTxnStatusId();
		
		 TransactionStatusProcessor transactionStatusProcessor = paymentStatusFactory.getStatusProcessor(txnStatusId);
		 
		 if(transactionStatusProcessor == null) {
			 throw new Exception();
		 }
		 
		 TransactionDto response = transactionStatusProcessor.processStatus(transactionDto);
		 
		 return response;
		
	}

}
