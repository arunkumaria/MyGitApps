package com.home.designspatterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class ObserveerPattern2 {

	public static void main(String[] args) {
		//1. create the publisher
			Obsl pub = new Pub();
		
		//2. create the subscribers
			Obsr sub = new Sub("sub1");
			
		//3. register the subscribers from the publisher
			pub.register(sub);
			
		//4. set the publisher to the subscribers
			sub.setPub(pub);
			
		//5. publish the message from the publisher
			pub.post("Hi Arun");
			
		//6. check the message from the subscribers
			sub.update();

	}

}

interface Obsl{
	public void register(Obsr observer);
	public void unregister(Obsr Observer);
	public void post(String msg);
	public String getUpdates(Obsr obsr);
	public void notifyAllObsr();
	
}


class Pub implements Obsl{
	
	private Object MUTEX = new Object();
	private List<Obsr> list;
	private String msg;
	private boolean checkup;
	
	public Pub() {
		list = new ArrayList<Obsr>();
	}
	
	public void register(Obsr observer) {
		
		if(list == null) {
			System.out.println("nothing to register");
		}else {
		
		synchronized(MUTEX) {
			list.add(observer);
			System.out.println("registering the subscriber "+observer);
		
		}
		}
		
	}
	public void unregister(Obsr observer) {
		synchronized(MUTEX) {
			list.remove(observer);
			System.out.println("unregistering the subscriber "+observer);
		}
	}


	@Override
	public void post(String msg) {
		this.msg = msg;
		this.checkup = true;
		notifyAllObsr();
		
		
	}

	@Override
	public String getUpdates(Obsr obsr) {
		return this.msg;
	}

	@Override
	public void notifyAllObsr() {
		
		
		List<Obsr> local = null;
		synchronized(MUTEX) {
		
			if(!checkup) {
				return;
			}
			
			checkup = false;
			local = new ArrayList();
		
		}
		
		for(Obsr o: local) {
			o.update();
		}
		
	}
	
	
	
	
	
	
}

interface Obsr {
	public void setPub(Obsl observable);
	public void update();
}

class Sub implements Obsr {
	private String name;
	Obsl topic;
	
	public Sub(String name) {
		this.name = name; 
	}
	public void setPub(Obsl observable) {
		this.topic = observable;
		
	}
	public void update() {
		String msg = this.topic.getUpdates(this);
		if(msg == null) {
			System.out.println("nothing to display");
		}else {
			System.out.println("The message is"+ msg);
		}
		
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
