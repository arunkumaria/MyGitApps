package com.own.pojo;

import lombok.Data;

@Data
public class PPOrderResponse {
	private String orderId;
	private String paypalStatus;
	private String redirectUrl;

}
