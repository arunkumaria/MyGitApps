package com.home.designspatterns.behavioral;

import java.awt.print.Book;

public class Visitor {

	public static void main(String[] args) {
		// 1. create the items like books and fruits
		ItemTypes itemTypes[] = { new Books("Wings of fire", 100), new Books("Ignited minds", 25),
				new Fruits("Mango", 200, 10), new Fruits("Apple", 50, 1 / 2) };

		// 4. get the calculated cost
		double total = calculate(itemTypes);

		// 5. print the total
		System.out.println("The total cost is: " + total);

	}

	public static double calculate(ItemTypes[] itemTypes) {
		ShoppingCartVisitor shoppingCartVisitor = new ShoppingCartVisitorImp();
		double sum = 0;

		for (ItemTypes i : itemTypes) {
			sum += i.accept(shoppingCartVisitor);
		}

		return sum;

	}

}

//2. visit the fruits and books with the interface to load the prices
interface ItemTypes {
	public double accept(ShoppingCartVisitor visitor);
}

class Books implements ItemTypes {
	private String bName;
	private long isbn;
	private double price;

	public Books(String bName, double price) {
		this.bName = bName;
		this.price = price;
	}

	public long getIsbn() {
		return isbn;
	}

	public double getPrice() {
		return price;
	}

	public String getBName() {
		return bName;
	}

	@Override
	public double accept(ShoppingCartVisitor visitor) {
		return visitor.visitor(this);

	}

}

class Fruits implements ItemTypes {

	private String fName;
	private long weight;
	private double price;

	public Fruits(String fName, double price, long weight) {
		this.fName = fName;
		this.price = price;
		this.weight = weight;
	}

	public long getWeight() {
		return weight;
	}

	public double getPrice() {
		return price;
	}

	public String getFName() {
		return fName;
	}

	@Override
	public double accept(ShoppingCartVisitor visitor) {
		return visitor.visitor(this);

	}

}

interface ShoppingCartVisitor {
	public double visitor(Books item);

	public double visitor(Fruits item);

}

//3. implement the own cost standards for both fruits and books in the visitor
class ShoppingCartVisitorImp implements ShoppingCartVisitor {

	@Override
	public double visitor(Books item) {
		double cost = 0;
		if (item.getPrice() > 50) {
			cost = item.getPrice() - 5;
		} else {
			cost = item.getPrice();
		}
		return cost;
	}

	@Override
	public double visitor(Fruits item) {
		double cost = 0;
		if (item.getWeight() > 1) {
			cost = item.getPrice() * item.getWeight();
		} else {
			cost = item.getPrice();
		}
		return cost;
	}

}
