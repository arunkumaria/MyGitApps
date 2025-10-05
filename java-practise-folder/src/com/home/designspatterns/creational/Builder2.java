package com.home.designspatterns.creational;

public class Builder2 {
	public static void main(String[] args) {

		Com comp = new Com.CompBuild("4gb", "256gb").setBluetooth("internal").setGraphics("external").build();
		System.out.println("hdd:- " + comp.getHdd());
		System.out.println("ram:- " + comp.getRam());
		System.out.println("bluetooth:- " + comp.getBluetooth());
		System.out.println("graphics:- " + comp.getGraphics());

	}

}

class Com {
	private String hdd;
	private String ram;
	private String bluetooth;
	private String graphics;

	public Com(CompBuild compBuild) {
		this.hdd = compBuild.hdd;
		this.ram = compBuild.ram;
		this.bluetooth = compBuild.bluetooth;
		this.graphics = compBuild.graphics;

	}

	public String getHdd() {
		return hdd;
	}

	public String getRam() {
		return ram;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public String getGraphics() {
		return graphics;
	}

	static class CompBuild {
		private String hdd;
		private String ram;
		private String bluetooth;
		private String graphics;

		public CompBuild(String hdd, String ram) {
			this.hdd = hdd;
			this.ram = ram;

		}

		public CompBuild setBluetooth(String bluetooth) {
			this.bluetooth = bluetooth;
			return this;
		}

		public CompBuild setGraphics(String graphics) {
			this.graphics = graphics;
			return this;
		}

		public Com build() {
			return new Com(this);
		}

	}
}
