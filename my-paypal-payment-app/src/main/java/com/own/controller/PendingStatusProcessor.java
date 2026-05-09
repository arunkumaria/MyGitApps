package com.own.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PendingStatusProcessor implements TransactionStatusProcessor {

	private final ModelMapper modelMapper;
	private final TransactionDao transactionDao;

	public TransactionDto processStatus(TransactionDto transactionDto) throws Exception {

		TransactionEntity transactionEntity = modelMapper.map(transactionDto, TransactionEntity.class);
		transactionDao.updateTransaction(transactionEntity);

		return transactionDto;

	}

}
