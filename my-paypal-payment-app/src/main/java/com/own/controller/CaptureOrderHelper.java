package com.own.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaptureOrderHelper {

	private static final String ORDER_ID_REF = "{orderId}";
	@Value("${paypal.capture.order.url}")
	private String captureOrderUrl;
	private final JsonUtil jsonUtil;

	public HttpRequest prepareCaptureOrderHttpRequest(String orderId, String accessToken) {

		HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
		httpHeaders.setBearerAuth(accessToken);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String uuid = UUID.randomUUID().toString();
		httpHeaders.add(Constants.PAY_PAL_REQUEST_ID, uuid);

		String url = captureOrderUrl.replace(ORDER_ID_REF, orderId);
		String requestAsJson = "";

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(url);
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(requestAsJson);

		return httpRequest;

	}

	public OrderResponse toCaptureOrderResponse(PaypalOrder paypalOrder) {
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderId(paypalOrder.getId());
		orderResponse.setPaypalStatus(paypalOrder.getStatus());

		return orderResponse;
	}

	public OrderResponse handleCaptureOrderResponse(ResponseEntity<String> httpResponse) {

		if (httpResponse.getStatusCode().is2xxSuccessful()) {
			log.info("Received successful response from PayPal service");

			PaypalOrder paypalOrder = jsonUtil.fromJson(httpResponse.getBody(), PaypalOrder.class);
			log.info("PayPal order details: {}", paypalOrder);

			return toCaptureOrderResponse(paypalOrder);
		}

		if (httpResponse.getStatusCode().is4xxClientError() || httpResponse.getStatusCode().is5xxServerError()) {
			log.error("Received 4xx, 5xx error response from PayPal service");

			PaypalErrorResponse paypalErrorRes = jsonUtil.fromJson(httpResponse.getBody(), PaypalErrorResponse.class);
			log.info("PayPal error response details: {}", paypalErrorRes);

			String errorCode = ErrorEnum.PAYPAL_ERROR.getErrorCode();
			String errorMessage = PaypalOrderUtil.getPaypalErrorSummary(paypalErrorRes);
			log.info("Generated PayPal error summary: {}", errorMessage);

			throw new PaymentException(errorCode, errorMessage,
					HttpStatus.valueOf(httpResponse.getStatusCode().value()));
		}

		log.error("Unexpected response from PayPal service. " + "httpResponse: {}", httpResponse);

		throw new PaymentException(ErrorEnum.PAYPAL_ERROR.getErrorCode(), ErrorEnum.PAYPAL_ERROR.getErrorMessage(),
				HttpStatus.BAD_GATEWAY);
	}
}
