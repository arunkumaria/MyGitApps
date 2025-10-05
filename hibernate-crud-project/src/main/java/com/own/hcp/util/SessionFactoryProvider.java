package com.own.hcp.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
	public static SessionFactory provideSessionFactory() {
		Configuration config = new Configuration();
		config.configure(); // Reads hibernate.cfg.xml
		return config.buildSessionFactory();
	}
}
