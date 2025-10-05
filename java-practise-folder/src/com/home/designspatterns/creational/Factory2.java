package com.home.designspatterns.creational;

public class Factory2 {

	public static void main(String[] args) {
		// Computer factory returns PC or Server based on the Computer types

		Compr compr1 = new PC(" 256gb", " 2th");
		compr1 = ComprFact.getComp("PC", compr1);
		System.out.println(compr1);

		Compr compr2 = new Ser(" 25600gb", " 2000th");
		compr2 = ComprFact.getComp("Server", compr2);
		System.out.println(compr2);
	}

}

interface Compr {
	public String getHardisk();

	public String getBluetooth();

}

class PC implements Compr {
	private String hdd;
	private String bt;

	public PC(String hdd, String bt) {
		super();
		this.hdd = hdd;
		this.bt = bt;
	}

	@Override
	public String getHardisk() {
		// TODO Auto-generated method stub
		return this.hdd;
	}

	@Override
	public String getBluetooth() {
		// TODO Auto-generated method stub
		return this.bt;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "this is PC" + this.getHardisk() + " " + getBluetooth();
	}

}

class Ser implements Compr {
	private String hdd;
	private String bt;

	public Ser(String hdd, String bt) {
		super();
		this.hdd = hdd;
		this.bt = bt;
	}

	@Override
	public String getHardisk() {
		// TODO Auto-generated method stub
		return this.hdd;
	}

	@Override
	public String getBluetooth() {
		// TODO Auto-generated method stub
		return this.bt;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "this is Server" + this.getHardisk() + " " + getBluetooth();
	}

}

class ComprFact {

	public static Compr getComp(String type, Compr compr) {
		Compr compr1 = null;
		if (type.equalsIgnoreCase("PC")) {
			compr1 = new PC(compr.getHardisk(), compr.getBluetooth());
		} else if (type.equalsIgnoreCase("Server")) {

			compr1 = new Ser(compr.getHardisk(), compr.getBluetooth());

		}

		return compr1;
	}
}
