package com.own.payment.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.own.payment.dao.interfaces.TransactionDaoInterface;
import com.own.payment.entity.TransactionEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDaoInterface {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public TransactionEntity createTransaction(TransactionEntity transactionEntity) {

		String sql = "INSERT INTO `Transaction`(userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, amount, currency, merchantTransactionReference, txnReference, providerReference, errorCode, errorMessage, retryCount) VALUES(:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, :errorCode, :errorMessage, :retryCount)";
//		String sql = "INSERT INTO `TRANSACTION` (" + "userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, "
//				+ "amount, currency, merchantTransactionReference, txnReference, providerReference,"
//				+ "errorCode, errorMessage, retryCount" + ")VALUES ("
//				+ ":userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, "
//				+ ":amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, "
//				+ ":errorCode, :errorMessage, :retryCount" + ")";


		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(transactionEntity);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[] { "id" });

		Number numberId = keyHolder.getKey();

		if (numberId != null) {
			transactionEntity.setId(numberId.intValue());
		}

		return transactionEntity;

	}

	public TransactionEntity getTransaction(String txnReference) {

		String sql = "SELECT * FROM `Transaction` WHERE txnReference= :txnReference LIMIT 1";

		Map<String, Object> param = new HashMap<>();
		param.put("txnReference", txnReference);

		TransactionEntity transactionEntity = jdbcTemplate.queryForObject(sql, param,
				new BeanPropertyRowMapper<>(TransactionEntity.class));

		return transactionEntity;

	}

	public void updateTransaction(TransactionEntity transactionEntity) throws Exception {

		String sql = "UPDATE `Transaction` SET txnStatusId = :txnStatusId, providerReference = :providerReference WHERE id = :id";

		Map<String, Object> params = new HashMap<>();
		params.put("txnStatusId", transactionEntity.getTxnStatusId());
		params.put("providerReference", transactionEntity.getProviderReference());
		params.put("id", transactionEntity.getId());

		int rowsEffected = jdbcTemplate.update(sql, params);

		if (rowsEffected == 0) {
			throw new Exception();
		}

	}

}
