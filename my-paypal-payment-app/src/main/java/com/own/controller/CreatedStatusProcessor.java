package com.own.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatedStatusProcessor implements TransactionStatusProcessor {

	private final TransactionDao transactionDao;
	private final ModelMapper modelMapper;

	@Override
	public TransactionDto processStatus(TransactionDto transactionDto) {

		TransactionEntity transactionEntity = modelMapper.map(transactionDto, TransactionEntity.class);

		TransactionEntity responseEntity = transactionDao.createTransaction(transactionEntity);

		transactionDto.setId(responseEntity.getId());

		return transactionDto;
	}

}
