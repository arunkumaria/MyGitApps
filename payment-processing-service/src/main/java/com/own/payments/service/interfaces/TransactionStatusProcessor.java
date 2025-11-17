package com.own.payments.service.interfaces;

import com.own.payments.dto.TransactionDto;

public interface TransactionStatusProcessor {
	
	public TransactionDto processStatus(TransactionDto txnDto);

}
