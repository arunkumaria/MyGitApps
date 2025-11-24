package com.own.service.helper;

import java.util.Collections;
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
import com.own.paypal.request.Amount;
import com.own.paypal.request.ExperienceContext;
import com.own.paypal.request.OrderRequest;
import com.own.paypal.request.PaymentSource;
import com.own.paypal.request.Paypal;
import com.own.paypal.request.PurchaseUnit;
import com.own.paypal.response.PaypalLink;
import com.own.paypal.response.PaypalOrder;
import com.own.paypal.response.error.PaypalErrorResponse;
import com.own.pojo.CreateOrderRequest;
import com.own.pojo.OrderResponse;
import com.own.util.JsonUtil;
import com.own.util.PaypalOrderUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrderHelper {

	@Value("${paypal.create.order.url}")
	private String createOrderUrl;

	private final JsonUtil jsonUtil;

	public HttpRequest prepareHttpRequest(CreateOrderRequest createOrderRequest, String accessToken)
			throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(accessToken);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String uuid = UUID.randomUUID().toString();

		httpHeaders.add(Constant.PAY_PAL_REQUEST_ID, uuid);

		Amount amount = new Amount();
		amount.setCurrencyCode(createOrderRequest.getCurrencyCode());
		String value = String.format(Constant.TWO_DECIMAL_FORMAT, createOrderRequest.getAmount());
		amount.setValue(value);

		PurchaseUnit purchaseUnit = new PurchaseUnit();
		purchaseUnit.setAmount(amount);

		ExperienceContext experienceContext = new ExperienceContext();
		experienceContext.setShippingPreference(Constant.SHIPPING_PREF_NO_SHIPPING);
		experienceContext.setPaymentMethodPreference(Constant.IMMEDIATE_PAYMENT_REQUIRED);
		experienceContext.setLandingPage(Constant.LANDINGPAGE_LOGIN);
		experienceContext.setUserAction(Constant.USER_ACTION_PAY_NOW);
		experienceContext.setReturnUrl(createOrderRequest.getReturnUrl());
		experienceContext.setCancelUrl(createOrderRequest.getCancelUrl());

		Paypal paypal = new Paypal();
		paypal.setExperienceContext(experienceContext);

		PaymentSource paymentSource = new PaymentSource();
		paymentSource.setPaypal(paypal);

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setIntent(Constant.INTENT_CAPTURE);
		orderRequest.setPaymentSource(paymentSource);
		orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));

		String requestBody = jsonUtil.toJson(orderRequest);

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl(createOrderUrl);
		httpRequest.setHttpHeaders(httpHeaders);
		httpRequest.setBody(requestBody);

		return httpRequest;

	}

	public OrderResponse handleHttpResponse(ResponseEntity<String> response)
			throws JsonMappingException, JsonProcessingException {

		if (response.getStatusCode().is2xxSuccessful()) {
			OrderResponse orderResponse = new OrderResponse();
			PaypalOrder paypalOrder = jsonUtil.fromJson(response.getBody(), PaypalOrder.class);
			orderResponse.setOrderId(paypalOrder.getId());
			orderResponse.setPaypalStatus(paypalOrder.getStatus());
			String redirectUrl = paypalOrder.getLinks().stream()
					.filter(links -> "payer-action".equalsIgnoreCase(links.getRel())).findFirst()
					.map(PaypalLink::getHref).orElse(null);
			orderResponse.setRedirectUrl(redirectUrl);

			if (orderResponse != null && orderResponse.getOrderId() != null && !orderResponse.getOrderId().isEmpty()
					&& orderResponse.getPaypalStatus() != null && !orderResponse.getPaypalStatus().isEmpty()
					&& orderResponse.getRedirectUrl() != null && !orderResponse.getRedirectUrl().isEmpty()) {

				return orderResponse;
			}

		}

		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {

			PaypalErrorResponse paypalErrorResponse = jsonUtil.fromJson(response.getBody(), PaypalErrorResponse.class);

			// PaypalOrderUtil orderUtil;

			throw new PaypalProviderException(ErrorCodeEnum.PAYPAL_ERROR.getErrorCode(),
					PaypalOrderUtil.getPaypalErrorSummary(paypalErrorResponse),
					HttpStatus.valueOf(response.getStatusCode().value()));

		}

		throw new PaypalProviderException(ErrorCodeEnum.PAYPAL_ERROR.getErrorCode(),
				ErrorCodeEnum.PAYPAL_ERROR.getErrorMessage(), HttpStatus.BAD_GATEWAY);

	}

}
