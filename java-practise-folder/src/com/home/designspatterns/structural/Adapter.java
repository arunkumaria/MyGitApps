package com.home.designspatterns.structural;

public class Adapter {

	public static void main(String[] args) {

		testSocketAdapter();

	}

	public static void testSocketAdapter() {

		SocketAdapter socketAdapter = new SocketAdapterImpl();
		Volt v3 = getVolts(socketAdapter, 3);
		Volt v12 = getVolts(socketAdapter, 12);
		Volt v120 = getVolts(socketAdapter, 120);

		System.out.println("v3 supply->" + v3.getVolt());
		System.out.println("v3 supply->" + v12.getVolt());
		System.out.println("v3 supply->" + v120.getVolt());

	}

	public static Volt getVolts(SocketAdapter socketAdapter, int i) {
		switch (i) {
		case 3:
			return socketAdapter.get3Volts();
		case 12:
			return socketAdapter.get12Volts();
		case 120:
			return socketAdapter.get120Volts();
		default:
			return socketAdapter.get120Volts();

		}
	}

}

class Volt {
	private int volt;

	public Volt(int v) {
		this.volt = v;
	}

	public int getVolt() {
		return volt;
	}

	public void setVolt(int volt) {
		this.volt = volt;
	}

}

class Socket {

	public Volt getVolts() {
		return new Volt(120);
	}

}

interface SocketAdapter {

	public Volt get3Volts();

	public Volt get12Volts();

	public Volt get120Volts();

}

class SocketAdapterImpl implements SocketAdapter {
	private Socket socket = new Socket();

	public Volt get3Volts() {
		Volt conVolt = socket.getVolts();
		return voltConverter(conVolt, 40);
	}

	public Volt get12Volts() {
		Volt conVolt = socket.getVolts();
		return voltConverter(conVolt, 10);
	}

	public Volt get120Volts() {
		return socket.getVolts();
	}

	public Volt voltConverter(Volt c, int v) {
		return new Volt(c.getVolt() / v);
	}
}
