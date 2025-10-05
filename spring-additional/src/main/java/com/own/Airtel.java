package com.own;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component("airtelBean")
public class Airtel implements Sim{

	@Override
	public void calling() {
		System.out.println("Airtel calling");		
	}

}
