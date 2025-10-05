package com.home.designspatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {

	public static void main(String[] args) {
		
		//1. create the publisher 
		Publisher publisher = new Publisher();
		
		//2. create the subscriber
		Observer observer1 = new Subscriber("sub1");
		Observer observer2 = new Subscriber("sub2");
		Observer observer3 = new Subscriber("sub3");
		
		//3. register subscribers with the publisher
		publisher.register(observer1);
		publisher.register(observer2);
		publisher.register(observer3);
		
		//4. set the publisher
		observer1.setSubject(publisher);
		observer2.setSubject(publisher);
		observer3.setSubject(publisher);
		
		observer1.update();
		//5. publish a message from the publisher
		publisher.postMsg("new message");
		
		//6. check the updates from the subscribers
		observer1.update();
		observer2.update();
		observer3.update();


	}

}

interface Observable {
	public void register(Observer obj);

	public void unregister(Observer obj);

	public void notifyAllObs();

	public Object getUpdates(Observer obs);

}

interface Observer {
	public void setSubject(Observable obj);

	public void update();

}

class Publisher implements Observable {

	private Object MUTEX = new Object();
	private List<Observer> objList;
	private String msg;
	private boolean checkUp;

	public Publisher() {
		super();
		objList = new ArrayList<Observer>();
	}

	@Override
	public void register(Observer obj) {

		if (obj == null) {
			System.out.println("nothing to register");
		} else {
			synchronized (MUTEX) {
				objList.add(obj);
				System.out.println("registration successful");
			}
		}

	}

	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
			objList.remove(obj);
			System.out.println("unregistration successful");
		}

	}

	@Override
	public void notifyAllObs() {

		List<Observer> obsLocal = null;
		synchronized (MUTEX) {
			if (!checkUp) {
				return;
			}
			obsLocal = new ArrayList<Observer>();
			checkUp = false;
		}

		for (Observer obs : obsLocal) {
			obs.update();
		}

	}

	@Override
	public Object getUpdates(Observer obs) {
		return this.msg;
	}

	public void postMsg(String msg) {
		System.out.println("published message posted is" + msg);
		this.checkUp = true;
		this.msg = msg;
		notifyAllObs();
	}

}

class Subscriber implements Observer {

	private String name;
	private Observable topic;

	public Subscriber(String name) {
		super();
		this.name = name;
	}

	@Override
	public void setSubject(Observable obj) {

		this.topic = obj;

	}

	@Override
	public void update() {
		String msg = (String)this.topic.getUpdates(this);
		if(msg == null) {
			System.out.println("no message to display");
		}else {
			System.out.println("message receved is "+ msg);
		}
		
		
	}

}