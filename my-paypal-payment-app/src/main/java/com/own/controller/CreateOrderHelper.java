package com.own.controller;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrderHelper {

	@Value("${paypal.create.order.url}")
	private String createOrderUrl;
	private final JsonUtil jsonUtil;

	public HttpRequest prepareHttpRequest(CreateOrderReq createOrderReq, String accessToken) {

		HttpHeaders httpHeaders = prepareHeader(accessToken);

		String requestBodyAsJson = prepareReqBodyAsJson(createOrderReq);

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(requestBodyAsJson);
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(createOrderUrl);

		return httpRequest;
	}

	public HttpHeaders prepareHeader(String accessToken) {
		org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
		httpHeaders.setBearerAuth(accessToken);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String uuid = UUID.randomUUID().toString();

		httpHeaders.add(Constants.PAY_PAL_REQUEST_ID, uuid);

		return httpHeaders;
	}

	public String prepareReqBodyAsJson(CreateOrderReq createOrderReq) {

		Amount amount = new Amount();
		amount.setCurrencyCode(createOrderReq.getCurrencyCode());

		String value = String.format(Constants.TWO_DECIMAL_FORMAT, createOrderReq.getAmount());
		amount.setValue(value);

		PurchaseUnit purchaseUnit = new PurchaseUnit();
		purchaseUnit.setAmount(amount);

		ExperienceContext experienceContext = new ExperienceContext();
		experienceContext.setPaymentMethodPreference(Constants.IMMEDIATE_PAYMENT_REQUIRED);
		experienceContext.setLandingPage(Constants.LANDINGPAGE_LOGIN);
		experienceContext.setShippingPreference(Constants.SHIPPING_PREF_NO_SHIPPING);
		experienceContext.setUserAction(Constants.USER_ACTION_PAY_NOW);
		experienceContext.setReturnUrl(createOrderReq.getReturnUrl());
		experienceContext.setCancelUrl(createOrderReq.getCancelUrl());

		Paypal paypal = new Paypal();
		paypal.setExperienceContext(experienceContext);

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setPaypal(paypal);

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setIntent(Constants.INTENT_CAPTURE);
		orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));
		orderRequest.setPaymentSource(paymentSource);

		String requestAsJson = jsonUtil.toJson(orderRequest);
		return requestAsJson;

	}

	public OrderResponse toOrderResponse(PaypalOrder paypalOrder) {

		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setOrderId(paypalOrder.getId());
		orderResponse.setPaypalStatus(paypalOrder.getStatus());
		String redirectLink = paypalOrder.getLinks().stream()
				.filter(link -> "payer-action".equalsIgnoreCase(link.getRel())).findFirst().map(PaypalLink::getHref)
				.orElse(null);
		orderResponse.setRedirectUrl(redirectLink);

		return orderResponse;
	}

	public OrderResponse handlePaypalResponse(ResponseEntity<String> response) {

		// 2xx
		if (response.getStatusCode().is2xxSuccessful()) {
			PaypalOrder paypalOrder = jsonUtil.fromJson(response.getBody(), PaypalOrder.class);

			OrderResponse orderResponse = toOrderResponse(paypalOrder);

			if (orderResponse != null && orderResponse.getOrderId() != null && !orderResponse.getOrderId().isEmpty()
					&& orderResponse.getPaypalStatus() != null && !orderResponse.getPaypalStatus().isEmpty()
					&& orderResponse.getRedirectUrl() != null && !orderResponse.getRedirectUrl().isEmpty()) {
				return orderResponse;
			}

		}

		// 4xx
		if (response.getStatusCode().is4xxClientError()) {
			// PaypalErrorResponse paypalErrorResponse =
			// jsonUtil.fromJson(response.getBody(), PaypalErrorResponse.class);
			throw new PaymentException(ErrorEnum.PAYPAL_ERROR.getErrorCode(), ErrorEnum.PAYPAL_ERROR.getErrorMessage(),
					HttpStatus.EXPECTATION_FAILED);

		}

		throw new PaymentException(ErrorEnum.UNKNOWN_ERROR.getErrorCode(), ErrorEnum.UNKNOWN_ERROR.getErrorMessage(),
				HttpStatus.BAD_REQUEST);

	}

}
