package com.own.springboot_crud;

import java.io.IOException;

public enum ProductCategory {
	ELECTRONICS, GROCERY, HOME_APPLIANCES, POWER_TOOLS;

	public String toValue() {
		switch (this) {
		case ELECTRONICS:
			return "Electronics";
		case GROCERY:
			return "Grocery";
		case HOME_APPLIANCES:
			return "Home Appliances";
		case POWER_TOOLS:
			return "Power Tools";
		}
		return null;
	}

	public static ProductCategory forValue(String value) throws IOException {
		if (value.equals("Electronics"))
			return ELECTRONICS;
		if (value.equals("Grocery"))
			return GROCERY;
		if (value.equals("Home Appliances"))
			return HOME_APPLIANCES;
		if (value.equals("Power Tools"))
			return POWER_TOOLS;
		throw new IOException("Cannot deserialize ProductCategory");
	}
}
