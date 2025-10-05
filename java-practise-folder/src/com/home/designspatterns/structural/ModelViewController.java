package com.home.designspatterns.structural;

public class ModelViewController {

	public static void main(String[] args) {
		StudentModel studentModel = retrieveDatabase();
		StudentVw studentView = new StudentVw();

		StudentController controller = new StudentController(studentModel, studentView);
		controller.updateView();
		controller.setStudentName("Varun");
		controller.setStudentRollno(999);
		controller.updateView();
	}

	public static StudentModel retrieveDatabase() {

		StudentModel studentModel = new StudentModel();
		studentModel.setName("Arun");
		studentModel.setRno(777);
		return studentModel;

	}

}

class StudentModel {

	private String name;
	private int rno;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

}

class StudentVw {

	public StudentVw() {

	}

	public void printDetails(String name, int rno) {
		System.out.println("displaying the details: ");
		System.out.println("student name: " + name);
		System.out.println("student rno: " + rno);
	}
}

class StudentController {

	private StudentModel studentModel;
	private StudentVw studentView;

	public StudentController(StudentModel studentModel, StudentVw studentView) {
		this.studentModel = studentModel;
		this.studentView = studentView;
	}

	public String getStudentName() {
		return studentModel.getName();
	}

	public int getStudentRollNo() {
		return studentModel.getRno();
	}

	public void setStudentName(String name) {
		studentModel.setName(name);
	}

	public void setStudentRollno(int rno) {
		studentModel.setRno(rno);
	}

	public void updateView() {
		studentView.printDetails(studentModel.getName(), studentModel.getRno());
	}

}
