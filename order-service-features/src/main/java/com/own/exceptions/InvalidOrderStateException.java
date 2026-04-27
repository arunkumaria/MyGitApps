package com.own.exceptions;

public class InvalidOrderStateException extends RuntimeException {
	public InvalidOrderStateException(String msg) {
		super(msg);
	}
}
