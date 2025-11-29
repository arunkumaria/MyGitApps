package com.own.service.status.processor.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.own.payment.dao.interfaces.TransactionDaoInterface;
import com.own.payment.dto.TransactionDto;
import com.own.payment.entity.TransactionEntity;
import com.own.service.status.processor.interfaces.TransactionStatusProcessor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreatedStatusProcessor implements TransactionStatusProcessor {

	private final ModelMapper modelMapper;
	private final TransactionDaoInterface transactionDaoInterface;

	@Override
	public TransactionDto processStatus(TransactionDto transactionDto) {

		TransactionEntity transactionEntity = modelMapper.map(transactionDto, TransactionEntity.class);

		TransactionEntity responseTransactionEntity = transactionDaoInterface.createTransaction(transactionEntity);

		transactionDto.setId(responseTransactionEntity.getId());

		return transactionDto;
	}

}
