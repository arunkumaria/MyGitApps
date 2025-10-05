package com.own;

import org.springframework.stereotype.Component;

@Component("jioBean")
public class Jio implements Sim {

	@Override
	public void calling() {
		System.out.println("Jio calling");
	}

}
