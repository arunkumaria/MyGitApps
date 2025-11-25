package com.own.payment.dao.interfaces;

import com.own.payment.entity.TransactionEntity;

public interface TransactionDaoInterface {

	public TransactionEntity createTransaction(TransactionEntity transactionEntity);

	public TransactionEntity getTransaction(String txnReference);

	public void updateTransaction(TransactionEntity transactionEntity) throws Exception;

}
