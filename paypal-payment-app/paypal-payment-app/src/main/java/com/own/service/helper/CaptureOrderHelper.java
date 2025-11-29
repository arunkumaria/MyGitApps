package com.own.service.helper;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.own.constant.Constant;
import com.own.constant.ErrorCodeEnum;
import com.own.exceptions.PaypalProviderException;
import com.own.http.HttpRequest;
import com.own.paypal.response.PaypalOrder;
import com.own.paypal.response.error.PaypalErrorResponse;
import com.own.pojo.OrderResponse;
import com.own.util.JsonUtil;
import com.own.util.PaypalOrderUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CaptureOrderHelper {

	@Value("${paypal.capture.order.url}")
	private String captureOrderUrl;

	private final JsonUtil jsonUtil;

	private String REF_ORDER_ID = "{orderId}";

	public HttpRequest prepareCaptureOrderRequest(String orderId, String accessToken) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(accessToken);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String uuid = UUID.randomUUID().toString();
		httpHeaders.add(Constant.PAY_PAL_REQUEST_ID, uuid);

		String finalUrl = captureOrderUrl.replace(REF_ORDER_ID, orderId);

		String requestBody = "";

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(finalUrl);
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(requestBody);

		return httpRequest;

	}

	public OrderResponse handleResponse(ResponseEntity<String> response)
			throws JsonMappingException, JsonProcessingException {

		if (response.getStatusCode().is2xxSuccessful()) {

			OrderResponse orderResponse = new OrderResponse();

			PaypalOrder order = jsonUtil.fromJson(response.getBody(), PaypalOrder.class);
			orderResponse.setOrderId(order.getId());
			orderResponse.setPaypalStatus(order.getStatus());

			return orderResponse;

		}

		if (response.getStatusCode().is4xxClientError() || (response.getStatusCode().is5xxServerError())) {

			PaypalErrorResponse paypalErrorResponse = jsonUtil.fromJson(response.getBody(), PaypalErrorResponse.class);

			throw new PaypalProviderException(ErrorCodeEnum.PAYPAL_ERROR.getErrorCode(),
					PaypalOrderUtil.getPaypalErrorSummary(paypalErrorResponse),
					HttpStatus.valueOf(response.getStatusCode().value()));

		}

		throw new PaypalProviderException(ErrorCodeEnum.PAYPAL_ERROR.getErrorCode(),
				ErrorCodeEnum.PAYPAL_ERROR.getErrorMessage(), HttpStatus.BAD_GATEWAY);

	}

}
