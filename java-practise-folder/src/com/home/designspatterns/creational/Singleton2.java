package com.home.designspatterns.creational;

public class Singleton2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// early binding
		SingnEarly singnEarly1 = SingnEarly.getInstance();
		SingnEarly singnEarly2 = SingnEarly.getInstance();
		System.out.println(singnEarly1.hashCode());
		System.out.println(singnEarly2.hashCode());

		// late binding
		SingnLate singnLate1 = SingnLate.getInstance();
		SingnLate singnLate2 = SingnLate.getInstance();
		System.out.println(singnLate1.hashCode());
		System.out.println(singnLate2.hashCode());

	}

}

class SingnEarly {

	private static SingnEarly instance = new SingnEarly();

	public SingnEarly() {

	}

	public static SingnEarly getInstance() {
		return instance;
	}

}

class SingnLate {
	private static SingnLate instance;

	public SingnLate() {

	}

	public static SingnLate getInstance() {
		if (instance == null) {
			instance = new SingnLate();
			return instance;
		}

		return instance;
	}

}
