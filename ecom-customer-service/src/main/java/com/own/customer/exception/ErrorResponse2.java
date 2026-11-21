package com.own.customer.exception;

import java.time.LocalDateTime;

public class ErrorResponse2 {
	private String error;
	private String message;
	private int status;
	private String path;
	private LocalDateTime timestamp;

	public ErrorResponse2(String error, String message, int status, String path, LocalDateTime timestamp) {
		this.error = error;
		this.message = message;
		this.status = status;
		this.path = path;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public String getPath() {
		return path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getError() {
		return error;
	}

}
