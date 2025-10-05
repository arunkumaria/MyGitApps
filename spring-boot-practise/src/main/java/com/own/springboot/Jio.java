package com.own.springboot;

import org.springframework.stereotype.Component;

@Component("jioBean")
class Jio implements Sim {
	@Override
	public void calling() {
		System.out.println("Jio Calling");
	}

	@Override
	public void data() {
		System.out.println("Jio Data");
	}
}