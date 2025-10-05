package com.home.designspatterns.creational;

public class Builder {

	public static void main(String[] args) {

		Computer computer = new Computer.ComputerBuilder("4gb", "128tb").setBluetooth(false).setGraphics(true).build();

		System.out.println("ram->" + computer.getRam());
		System.out.println("hdd->" + computer.getHdd());
		System.out.println("bluetooth->" + computer.isBluetooth());
		System.out.println("ram->" + computer.isGraphics());

	}

}

class Computer {

	private String ram;
	private String hdd;
	private boolean bluetooth;
	private boolean graphics;

	public Computer(ComputerBuilder computerbuilder) {
		this.ram = computerbuilder.ram;
		this.hdd = computerbuilder.hdd;
		this.bluetooth = computerbuilder.bluetooth;
		this.graphics = computerbuilder.graphics;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getHdd() {
		return hdd;
	}

	public void setHdd(String hdd) {
		this.hdd = hdd;
	}

	public boolean isBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(boolean bluetooth) {
		this.bluetooth = bluetooth;
	}

	public boolean isGraphics() {
		return graphics;
	}

	public void setGraphics(boolean graphics) {
		this.graphics = graphics;
	}

	public static class ComputerBuilder {
		private String ram;
		private String hdd;
		private boolean bluetooth;
		private boolean graphics;

		public ComputerBuilder(String ram, String hdd) {
			this.ram = ram;
			this.hdd = hdd;
		}

		public ComputerBuilder setBluetooth(boolean bluetooth) {
			this.bluetooth = bluetooth;
			return this;
		}

		public ComputerBuilder setGraphics(boolean graphics) {
			this.graphics = graphics;
			return this;
		}

		public Computer build() {

			return new Computer(this);

		}
	}

}
