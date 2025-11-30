package com.own.controller;

public interface TransactionDao {
	
	public TransactionEntity createTransaction(TransactionEntity transactionEntity);
	public TransactionEntity getTransaction(String txnReference);
	public void updateTransaction(TransactionEntity transactionEntity) throws Exception;

}
