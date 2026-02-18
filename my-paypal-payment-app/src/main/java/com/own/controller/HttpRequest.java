package com.own.controller;



import org.springframework.http.HttpMethod;

import lombok.Data;

@Data
public class HttpRequest {
	private HttpMethod httpMethod;
	private String url;
	private org.springframework.http.HttpHeaders httpHeaders;
	private Object body;

}
