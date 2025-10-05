package com.own;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Mobile {

	@Autowired
	@Qualifier("jioBean")
	Sim sim;

	public void useSim() {
		sim.calling();
	}

	public static void main(String[] args) {

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		Mobile mobile = (Mobile) applicationContext.getBean(Mobile.class);
		mobile.useSim();

	}

}
