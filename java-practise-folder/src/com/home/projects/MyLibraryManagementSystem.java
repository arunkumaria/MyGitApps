package com.home.projects;

import java.util.Scanner;

class Bk {
	private int sNo;
	private String bName;
	private String aName;
	private int bQty;
	private int bCopy;

	public Bk(int sNo, String bName, String aName, int bQty, int bCopy) {
		super();
		this.sNo = sNo;
		this.bName = bName;
		this.aName = aName;
		this.bQty = bQty;
		this.bCopy = bCopy;
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public int getbQty() {
		return bQty;
	}

	public void setbQty(int bQty) {
		this.bQty = bQty;
	}

	public int getbCopy() {
		return bCopy;
	}

	public void setbCopy(int bCopy) {
		this.bCopy = bCopy;
	}

}

class Bks {

	private Bk books[] = new Bk[50];
	int bCnt = 0;

	void addBook(Bk bk) {
		if (bCnt < 50) {
			books[bCnt] = bk;
			bCnt++;
			System.out.println("book added");

		} else {
			System.out.println("book not added");
			return;
		}

	}

	void searchBk(Bk bk) {

		for (int i = 0; i < 50; i++) {
			if (books[i].getsNo() == bk.getsNo() && books[i].getbName().equals(bk.getbName())) {
				System.out.println("book found. book name is: " + bk.getbName());
				return;
			}
		}
		System.out.println("book not found.");
		return;

	}

	void showBks() {

		for (int i = 0; i < 50; i++) {
			System.out.println("book sNo is " + books[i].getsNo());
			System.out.println("book name is " + books[i].getbName());
			System.out.println("book author name  is " + books[i].getaName());
		}

	}

	void upgradeBk(Bk bk, int num) {

		for (int i = 0; i < 50; i++) {
			if (books[i].getsNo() == bk.getsNo()) {
				bk.setbQty(num);
				System.out.println("upgradation successful");
				return;
			}

		}
		System.out.println("book not found.");
		return;

	}

}

class Std {

	private String sname;
	private int regNo;
	Bk borrowed[] = new Bk[3];

	public Std(String sname, int regNo) {
		super();
		this.sname = sname;
		this.regNo = regNo;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getRegNo() {
		return regNo;
	}

	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}

}

class Stds {

	private Std stds[] = new Std[5];
	private int sCnt = 0;
	Scanner scn = new Scanner(System.in);

	void register() {

		System.out.println("enter your name");
		String n = scn.nextLine();
		System.out.println("enter your rno");
		int r = scn.nextInt();

		if (sCnt <= 5) {

			stds[sCnt] = new Std(n, r);
			sCnt++;
			System.out.println("registration successful");

		} else {
			System.out.println("registration failed.");
		}

	}

	void showAll() {
		for (int i = 0; i < 5; i++) {
			System.out.println("student rno: " + stds[i].getRegNo());
			System.out.println("student name: " + stds[i].getSname());
		}
	}

	void checkIn(Bk bk) {
		System.out.println("enter the registration number");
		int r = scn.nextInt();

		for (int i = 0; i <= 5; i++) {
			if (this.stds[i].getRegNo() == r) {
				Std temp = this.stds[i];
				for (int j = 0; j < 3; j++) {
					if (temp.borrowed[j] == null) {
						temp.borrowed[j] = bk;
						System.out.println("book checked in");
						return;
					}

				}

			}
		}
		System.out.println("book not checked in");
		return;
	}

	void checkOut(Bk bk) {
		System.out.println("enter the registration number");
		int r = scn.nextInt();

		for (int i = 0; i <= 5; i++) {
			if (this.stds[i].getRegNo() == r) {
				Std temp = this.stds[i];
				for (int j = 0; j < 3; j++) {
					if (temp.borrowed[j] != null) {
						temp.borrowed[j] = null;
						System.out.println("book checked out");
						return;
					}

				}

			}
		}
		System.out.println("book not checked out");
		return;
	}

}

public class MyLibraryManagementSystem {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);

		while (true) {
			System.out.println("Welcome to Library Management System");
			System.out.println("enter the your choice");
			System.out.println("1. add book");
			System.out.println("2. upgrade book");
			System.out.println("3. show book");
			System.out.println("4. register student");
			System.out.println("5. show all");
			System.out.println("6. check in");
			System.out.println("7. check out");
			System.out.println("8. search book");

			int ch = scn.nextInt();
			Bks bks = new Bks();
			Stds stds = new Stds();

			switch (ch) {
			case 1:
				Bk bk = new Bk(1, "silent pat", "archi", 10, 10);
				bks.addBook(bk);
				break;

			case 2:
				;
				bks.upgradeBk(new Bk(1, "silent pat", "archi", 10, 10), 10);
				break;

			case 3:
				bks.showBks();
				break;

			case 4:
				stds.register();
				break;

			case 5:
				stds.showAll();
				break;

			case 6:
				stds.checkIn(new Bk(1, "silent pat", "archi", 10, 10));
				break;

			case 7:
				stds.checkIn(new Bk(1, "silent pat", "archi", 10, 10));
				break;

			case 8:
				bks.searchBk(new Bk(1, "silent pat", "archi", 10, 10));
				break;

			default:
				System.out.println("wrong entry");
				break;

			}
		}

	}

}
