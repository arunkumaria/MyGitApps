package com.own;

class FlipKart {
	private Delivery delivery;
	
	
	

	public FlipKart() {
		super();
		System.out.println("flipkart");
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	void trackDelivery() {
		delivery.track();

	}

}