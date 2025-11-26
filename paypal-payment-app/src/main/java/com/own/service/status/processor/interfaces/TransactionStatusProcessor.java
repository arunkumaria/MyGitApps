package com.own.service.status.processor.interfaces;

import com.own.payment.dto.TransactionDto;

public interface TransactionStatusProcessor {

	public TransactionDto processStatus(TransactionDto transactionDto) throws Exception ;

}
