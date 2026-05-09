package com.own.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDao {

	public final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public TransactionEntity createTransaction(TransactionEntity transactionEntity) {
//		// TODO Auto-generated method stub
//		String sql = "INSERT INTO `TRANSACTION` (" + "userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, "
//				+ "amount, currency, merchantTransactionReference, txnReference, providerReference,"
//				+ "errorCode, errorMessage, retryCount" + ")VALUES ("
//				+ ":userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, "
//				+ ":amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, "
//				+ ":errorCode, :errorMessage, :retryCount" + ")";
		
		String sql = "INSERT INTO `Transaction`(userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, amount, currency, merchantTransactionReference, txnReference, providerReference, errorCode, errorMessage, retryCount) VALUES(:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, :errorCode, :errorMessage, :retryCount)";

		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(transactionEntity);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[] { "id" });

		Number generatedId = keyHolder.getKey();
		if (generatedId != null) {
			transactionEntity.setId(generatedId.intValue());
		}

		return transactionEntity;
	}

	@Override
	public TransactionEntity getTransaction(String txnReference) {
		// TODO Auto-generated method stub

		//String sql = "SELECT * FROM `TRANSACTION` WHERE txnReference = :txnReference LIMIT 1";
		
		String sql = "SELECT * FROM `Transaction` WHERE txnReference = :txnReference LIMIT 1";

		Map<String, Object> params = new HashMap<>();
		params.put("txnReference", txnReference);

		TransactionEntity transactionEntity = namedParameterJdbcTemplate.queryForObject(sql, params,
				new BeanPropertyRowMapper<>(TransactionEntity.class));

		return transactionEntity;
	}

	@Override
	public void updateTransaction(TransactionEntity transactionEntity) throws Exception {
		// TODO Auto-generated method stub

		//String sql = "UPDATE `TRANSACTION` SET txnStatusId = :txnStatusId, providerReference = :providerReference WHERE id = :id";
		
		String sql = "UPDATE `Transaction` SET txnStatusId = :txnStatusId, providerReference = :providerReference WHERE id = :id";

		Map<String, Object> params = new HashMap();
		params.put("txnStatusId", transactionEntity.getTxnStatusId());
		params.put("providerReference", transactionEntity.getProviderReference());
		params.put("id", transactionEntity.getId());

		int rowsEffected = namedParameterJdbcTemplate.update(sql, params);

		if (rowsEffected == 0) {
			throw new Exception();
		}

	}

}
