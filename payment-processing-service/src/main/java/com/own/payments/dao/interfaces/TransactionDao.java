package com.own.payments.dao.interfaces;

import com.own.payments.entity.TransactionEntity;

public interface TransactionDao {
	
	public TransactionEntity createTransaction(TransactionEntity transaction);
	public TransactionEntity getTransactionByTxnReference(String txnReference);
	
	public void updateTransaction(TransactionEntity transaction);

}
