package com.home.designspatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class Strategy {

	public static void main(String[] args) {
		// 1. create the items with prices and add
		Item item1 = new Item("laptop", 50000);
		Item item2 = new Item("bat", 1000);
		Item item3 = new Item("bowl", 500);

		ShoppingCart cart = new ShoppingCart();
		cart.addItem(item1);
		cart.addItem(item2);
		cart.addItem(item3);

		// 2. define payment method for the items at the runtime - Strategy
		OnlinePayment onlinePayment = new OnlinePayment("canara", 0044);
		CreditCard creditCard = new CreditCard("arun", 0077);

		cart.pay(onlinePayment);
		cart.pay(creditCard);

		// 3. calculate the amount and display
		System.out.println(cart.calculate());

	}

}

interface PaymentStrategy {
	public void pay(int amount);
}

class CreditCard implements PaymentStrategy {

	private String cName;
	private int cNum;

	public CreditCard(String cName, int cNum) {
		this.cName = cName;
		this.cNum = cNum;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getcNum() {
		return cNum;
	}

	public void setcNum(int cNum) {
		this.cNum = cNum;
	}

	@Override
	public void pay(int amount) {
		System.out.println("credit payment for " + this.getcName());

	}

}

class OnlinePayment implements PaymentStrategy {
	private String bName;
	private int bNum;

	public OnlinePayment(String bName, int bNum) {
		this.bName = bName;
		this.bNum = bNum;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public int getbNum() {
		return bNum;
	}

	public void setbNum(int bNum) {
		this.bNum = bNum;
	}

	@Override
	public void pay(int amount) {
		System.out.println("online payment at " + this.getbName());

	}

}

class Item {

	private String iName;
	private int iPrice;

	public Item(String iName, int iPrice) {
		this.iName = iName;
		this.iPrice = iPrice;
	}

	public String getiName() {
		return iName;
	}

	public void setiName(String iName) {
		this.iName = iName;
	}

	public int getiPrice() {
		return iPrice;
	}

	public void setiPrice(int iPrice) {
		this.iPrice = iPrice;
	}

}

class ShoppingCart {

	private List<Item> items;

	public ShoppingCart() {
		items = new ArrayList();
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public int calculate() {
		int sum = 0;
		for (Item i : items) {
			sum += i.getiPrice();

		}
		return sum;
	}

	public void pay(PaymentStrategy paymentStrategy) {
		paymentStrategy.pay(calculate());
	}

}