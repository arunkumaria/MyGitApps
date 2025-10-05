package com.own.springboot_crud;

public class UserNotFoundException2 extends RuntimeException {

	public UserNotFoundException2(long id) {
		super("user id " + id + " not found");
	}

}
