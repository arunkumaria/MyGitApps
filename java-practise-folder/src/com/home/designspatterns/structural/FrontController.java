package com.home.designspatterns.structural;

public class FrontController {

	public static Dispatcher dis = new Dispatcher();
	// authenticating the request

	public static void main(String[] args) {

		FrontController frontController = new FrontController();
		
		frontController.dispatcher("student");
		
	}

	public FrontController() {
		
	}

	public boolean authenticateUser() {
		return true;

	}

	public void trackReq(String request) {
		System.out.println("tracking the request:" + request);
	}

	public void dispatcher(String req) {

		trackReq(req);

		if (authenticateUser()) {
			dis.dispatcher(req);
		}

	}

}

class Dispatcher {

	StudentView sview = new StudentView();
	TeacherView tview = new TeacherView();
	
	public Dispatcher() {
		
	}

	public void dispatcher(String req) {
		if (req.equals("student")) {
			sview.display();
		} else {
			tview.display();
		}
	}
}

class StudentView {

	public void display() {
		System.out.println("student view");
	}

}

class TeacherView {
	public void display() {
		System.out.println("teacher view");
	}

}
