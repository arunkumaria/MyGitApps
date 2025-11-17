package com.own.payments.service.impl.statusprocessor;

import org.springframework.stereotype.Service;

import com.own.payments.dto.TransactionDto;
import com.own.payments.service.interfaces.TransactionStatusProcessor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuccessStatusProcessor implements TransactionStatusProcessor {

	@Override
	public TransactionDto processStatus(TransactionDto txnDto) {
		log.info("Processing 'SUCCESS' status for txnDto: {}", txnDto);
		//TODO
		
		return txnDto;
	}

}
