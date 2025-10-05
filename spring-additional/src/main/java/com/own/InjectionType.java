
package com.own;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionType {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		FlipKart flipKart = (FlipKart) applicationContext.getBean("flipkart");
		flipKart.trackDelivery();

	}

}
