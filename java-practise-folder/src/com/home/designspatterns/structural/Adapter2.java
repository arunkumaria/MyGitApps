package com.home.designspatterns.structural;

public class Adapter2 {

	public static void main(String[] args) {

		testConv();
	}

	public static void testConv() {
		A a = new AI();

		V v3 = getVots(a, 3);
		V v12 = getVots(a, 12);

		System.out.println("the adapted voltage for 3 volts is:" + v3.getV());
		System.out.println("the adapted voltage for 12 volts is:" + v12.getV());

	}

	public static V getVots(A a, int v) {

		switch (v) {

		case 3:
			return a.get3Volts();

		case 12:
			return a.get12Volts();

		default:
			return a.get120Volts();

		}
	}

}

class V {

	int v;

	public V(int v) {
		this.v = v;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

}

class S {

	public V getVolts() {
		return new V(120);
	}

}

interface A {

	public V get3Volts();

	public V get12Volts();

	public V get120Volts();
}

class AI implements A {

	S socket = new S();

	@Override
	public V get3Volts() {
		return converter(socket.getVolts(), 40);

	}

	@Override
	public V get12Volts() {
		return converter(socket.getVolts(), 10);

	}

	public V converter(V v, int c) {
		return new V(v.getV() / c);
	}

	@Override
	public V get120Volts() {
		// TODO Auto-generated method stub
		return socket.getVolts();
	}

}
