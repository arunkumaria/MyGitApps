package com.home.designspatterns.creational;

public class Factory {

	public static void main(String[] args) {
		Comp pc = CompFactory.getComputer("Pc", "4gb", "256gb", false);
		Comp server = CompFactory.getComputer("Server", "32gb", "1tb", true);

		System.out.println("pc->" + pc);
		System.out.println("server->" + server);

	}

}

abstract class Comp {

	public abstract String getRam();

	public abstract String getHdd();

	public abstract boolean getBluetooth();

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ram usage" + "->" + getRam() + " " + "hdd usage" + "->" + getHdd() + " " + "bluetooth usage" + "->"
				+ getBluetooth();
	}

}

class CompFactory {

	public static Comp getComputer(String type, String ram, String hdd, boolean bluetooth) {
		if (type.equalsIgnoreCase("PC")) {
			return new Pc(ram, hdd, bluetooth);
		} else if (type.equalsIgnoreCase("Server")) {
			return new Server(ram, hdd, bluetooth);
		} else {
			return null;
		}
	}

}

class Pc extends Comp {

	private String ram;
	private String hdd;
	private boolean bluetooth;

	public Pc(String ram, String hdd, boolean bluetooth) {
		this.ram = ram;
		this.hdd = hdd;
		this.bluetooth = bluetooth;
	}

	@Override
	public String getRam() {
		// TODO Auto-generated method stub
		return this.ram;
	}

	@Override
	public String getHdd() {
		// TODO Auto-generated method stub
		return this.hdd;
	}

	@Override
	public boolean getBluetooth() {
		// TODO Auto-generated method stub
		return this.bluetooth;
	}

}

class Server extends Comp {

	private String ram;
	private String hdd;
	private boolean bluetooth;

	public Server(String ram, String hdd, boolean bluetooth) {
		this.ram = ram;
		this.hdd = hdd;
		this.bluetooth = bluetooth;
	}

	@Override
	public String getRam() {
		// TODO Auto-generated method stub
		return this.ram;
	}

	@Override
	public String getHdd() {
		// TODO Auto-generated method stub
		return this.hdd;
	}

	@Override
	public boolean getBluetooth() {
		// TODO Auto-generated method stub
		return this.bluetooth;
	}

}
