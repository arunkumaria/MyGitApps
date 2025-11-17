package com.own.payments.service.impl.statusprocessor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.own.payments.dao.interfaces.TransactionDao;
import com.own.payments.dto.TransactionDto;
import com.own.payments.entity.TransactionEntity;
import com.own.payments.service.interfaces.TransactionStatusProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatedStatusProcessor implements TransactionStatusProcessor {
	
	private final ModelMapper modelMapper;
	
	private final TransactionDao transactionDao;

	@Override
	public TransactionDto processStatus(TransactionDto txnDto) {
		log.info("Processing 'CREATED' status for txnDto: {}", txnDto);
		
		TransactionEntity txnEntity = modelMapper.map(txnDto, TransactionEntity.class);
		log.info("Mapped TransactionEntity: {}", txnEntity);
		
		TransactionEntity responseEntity = transactionDao.createTransaction(txnEntity);
		log.info("Created TransactionEntity in DB: {}", responseEntity);
		
		txnDto.setId(responseEntity.getId());
		
		log.info("Updated TransactionDto with ID: {}", txnDto);
		return txnDto;
	}

}
