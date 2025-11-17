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
public class InitiatedStatusProcessor implements TransactionStatusProcessor {
	
	private final TransactionDao transactionDao;
	
	private final ModelMapper modelMapper;

	@Override
	public TransactionDto processStatus(TransactionDto txnDto) {
		log.info("Processing 'INITIATED' status for txnDto: {}", txnDto);
		
		// convert DTO to Entity
		TransactionEntity txnEntity = modelMapper.map(
				txnDto, TransactionEntity.class);
		
		transactionDao.updateTransaction(txnEntity);
		log.info("Updated TransactionEntity in DB for 'INITIATED' "
				+ "status: {}", txnEntity);
		
		return txnDto;
	}

}
