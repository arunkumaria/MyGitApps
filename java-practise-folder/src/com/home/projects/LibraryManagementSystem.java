//Java Program to Illustrate book Class that
//takes Input from the books and related information

//Importing required classes
package com.home.projects;

import java.util.Scanner;

//Class
class book {

	// Class data members
	public int sNo;
	public String bookName;
	public String authorName;
	public int bookQty;
	public int bookQtyCopy;

	// Creating object of Scanner class to
	// read input from users
	Scanner input = new Scanner(System.in);

	// Method
	// To add book details
	public book() {
		// Display message for taking input later
		// taking input via
		// nextInt() and nextLine() standard methods
		System.out.println("Enter Serial No of Book:");
		this.sNo = input.nextInt();
		input.nextLine();

		System.out.println("Enter Book Name:");
		this.bookName = input.nextLine();

		System.out.println("Enter Author Name:");
		this.authorName = input.nextLine();

		System.out.println("Enter Quantity of Books:");
		this.bookQty = input.nextInt();
		bookQtyCopy = this.bookQty;
	}
}

//Java Program to Illustrate books class

//To Do all the Operations related to Books such as
//add, check-in, check-out,Valid books,Update books

//CLass
class books {

	// Class data members
	book theBooks[] = new book[50];
	public static int count;

	Scanner input = new Scanner(System.in);

	// Method 1
	// To compare books
	public int compareBookObjects(book b1, book b2) {

		// If book name matches
		if (b1.bookName.equalsIgnoreCase(b2.bookName)) {

			// Printing book exists
			System.out.println("Book of this Name Already Exists.");
			return 0;
		}

		// if book serial matches
		if (b1.sNo == b2.sNo) {

			// Print book exists
			System.out.println("Book of this Serial No Already Exists.");

			return 0;
		}
		return 1;
	}

	// Method 2
	// To add book
	public void addBook(book b) {

		for (int i = 0; i < count; i++) {

			if (this.compareBookObjects(b, this.theBooks[i]) == 0)
				return;
		}

		if (count < 50) {

			theBooks[count] = b;
			count++;
		} else {

			System.out.println("No Space to Add More Books.");
		}
	}

	// Method 3
	// To search book by serial number
	public void searchBySno() {

		// Display message
		System.out.println("\t\t\t\tSEARCH BY SERIAL NUMBER\n");

		// Class data members
		int sNo;
		System.out.println("Enter Serial No of Book:");
		sNo = input.nextInt();

		int flag = 0;
		System.out.println("S.No\t\tName\t\tAuthor\t\tAvailable Qty\t\tTotal Qty");

		for (int i = 0; i < count; i++) {
			if (sNo == theBooks[i].sNo) {
				System.out.println(theBooks[i].sNo + "\t\t" + theBooks[i].bookName + "\t\t" + theBooks[i].authorName
						+ "\t\t" + theBooks[i].bookQtyCopy + "\t\t" + theBooks[i].bookQty);
				flag++;
				return;
			}
		}
		if (flag == 0)
			System.out.println("No Book for Serial No " + sNo + " Found.");
	}

	// Method 4
	// To search author by name
	public void searchByAuthorName() {

		// Display message
		System.out.println("\t\t\t\tSEARCH BY AUTHOR'S NAME");

		input.nextLine();

		System.out.println("Enter Author Name:");
		String authorName = input.nextLine();

		int flag = 0;

		System.out.println("S.No\t\tName\t\tAuthor\t\tAvailable Qty\t\tTotal Qty");

		for (int i = 0; i < count; i++) {

			// if author matches any of its book
			if (authorName.equalsIgnoreCase(theBooks[i].authorName)) {

				// Print below corresponding credentials
				System.out.println(theBooks[i].sNo + "\t\t" + theBooks[i].bookName + "\t\t" + theBooks[i].authorName
						+ "\t\t" + theBooks[i].bookQtyCopy + "\t\t" + theBooks[i].bookQty);
				flag++;
			}
		}

		// Else no book matches for author
		if (flag == 0)
			System.out.println("No Books of " + authorName + " Found.");
	}

	// Method 5
	// To display all books
	public void showAllBooks() {

		System.out.println("\t\t\t\tSHOWING ALL BOOKS\n");
		System.out.println("S.No\t\tName\t\tAuthor\t\tAvailable Qty\t\tTotal Qty");

		for (int i = 0; i < count; i++) {

			System.out.println(theBooks[i].sNo + "\t\t" + theBooks[i].bookName + "\t\t" + theBooks[i].authorName
					+ "\t\t" + theBooks[i].bookQtyCopy + "\t\t" + theBooks[i].bookQty);
		}
	}

	// Method 6
	// To edit the book
	public void upgradeBookQty() {

		System.out.println("\t\t\t\tUPGRADE QUANTITY OF A BOOK\n");
		System.out.println("Enter Serial No of Book");

		int sNo = input.nextInt();

		for (int i = 0; i < count; i++) {

			if (sNo == theBooks[i].sNo) {

				// Display message
				System.out.println("Enter No of Books to be Added:");

				int addingQty = input.nextInt();
				theBooks[i].bookQty += addingQty;
				theBooks[i].bookQtyCopy += addingQty;

				return;
			}
		}
	}

	// Method 7
	// To create menu
	public void dispMenu() {

		// Displaying menu
		System.out.println(
				"----------------------------------------------------------------------------------------------------------");
		System.out.println("Press 1 to Add new Book.");
		System.out.println("Press 0 to Exit Application.");
		System.out.println("Press 2 to Upgrade Quantity of a Book.");
		System.out.println("Press 3 to Search a Book.");
		System.out.println("Press 4 to Show All Books.");
		System.out.println("Press 5 to Register Student.");
		System.out.println("Press 6 to Show All Registered Students.");
		System.out.println("Press 7 to Check Out Book. ");
		System.out.println("Press 8 to Check In Book");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------");
	}

	// Method 8
	// To search the library
	public int isAvailable(int sNo) {

		for (int i = 0; i < count; i++) {
			if (sNo == theBooks[i].sNo) {
				if (theBooks[i].bookQtyCopy > 0) {

					System.out.println("Book is Available.");
					return i;
				}
				System.out.println("Book is Unavailable");
				return -1;
			}
		}

		System.out.println("No Book of Serial Number " + " Available in Library.");
		return -1;
	}

	// Method 9
	// To remove the book from the library
	public book checkOutBook() {

		System.out.println("Enter Serial No of Book to be Checked Out.");
		int sNo = input.nextInt();

		int bookIndex = isAvailable(sNo);

		if (bookIndex != -1) {
			theBooks[bookIndex].bookQtyCopy--;
			return theBooks[bookIndex];
		}
		return null;
	}

	// Method 10
	// To add the Book to the Library
	public void checkInBook(book b) {
		for (int i = 0; i < count; i++) {
			if (b.equals(theBooks[i])) {
				theBooks[i].bookQtyCopy++;
				return;
			}
		}
	}
}

//Java Program to Illustrate Student Class
//to take Input from the student and related Information

//Importing required classes

//Class
class student {

	// Class member variables
	String studentName;
	String regNum;

	book borrowedBooks[] = new book[3];
	public int booksCount = 0;

	// Creating object of Scanner class to
	// take input from user
	Scanner input = new Scanner(System.in);

	// Constructor
	public student() {
		// Print statement
		System.out.println("Enter Student Name:");

		// This keywords refers to current instance
		this.studentName = input.nextLine();

		// Print statement
		System.out.println("Enter Registration Number:");
		this.regNum = input.nextLine();
	}
}

//Java Program to Illustrate students Class
//To Do all the Operations related to students:
//add Students,check-in books,check out books,ValidStudent

//Importing required classes

//Class
class students {

	// Creating objects of Scanner and students class
	Scanner input = new Scanner(System.in);
	student theStudents[] = new student[50];

	public static int count = 0;

	// Method 1
	// To add books
	public void addStudent(student s) {
		for (int i = 0; i < count; i++) {

			if (s.regNum.equalsIgnoreCase(theStudents[i].regNum)) {

				// Print statement
				System.out.println("Student of Reg Num " + s.regNum + " is Already Registered.");

				return;
			}
		}

		if (count <= 50) {
			theStudents[count] = s;
			count++;
		}
	}

	// Method 2
	// Displaying all students
	public void showAllStudents() {
		// Printing student name and
		// corresponding registered number
		System.out.println("Student Name\t\tReg Number");
		for (int i = 0; i < count; i++) {

			System.out.println(theStudents[i].studentName + "\t\t" + theStudents[i].regNum);
		}
	}

	// Method 3
	// To check the Student
	public int isStudent() {
		// Display message only
		System.out.println("Enter Reg Number:");

		String regNum = input.nextLine();

		for (int i = 0; i < count; i++) {

			if (theStudents[i].regNum.equalsIgnoreCase(regNum)) {
				return i;
			}
		}

		// Print statements
		System.out.println("Student is not Registered.");
		System.out.println("Get Registered First.");

		return -1;
	}

	// Method 4
	// To remove the book
	public void checkOutBook(books book) {
		int studentIndex = this.isStudent();

		if (studentIndex != -1) {
			System.out.println("checking out");

			book.showAllBooks();
			book b = book.checkOutBook();

			System.out.println("checking out");
			if (b != null) {

				if (theStudents[studentIndex].booksCount <= 3) {

					System.out.println("adding book");
					theStudents[studentIndex].borrowedBooks[theStudents[studentIndex].booksCount] = b;
					theStudents[studentIndex].booksCount++;

					return;
				} else {

					System.out.println("Student Can not Borrow more than 3 Books.");
					return;
				}
			}
			System.out.println("Book is not Available.");
		}
	}

	// Method 5
	// To add the book
	public void checkInBook(books book) {
		int studentIndex = this.isStudent();
		if (studentIndex != -1) {

			// Printing credentials corresponding to student
			System.out.println("S.No\t\t\tBook Name\t\t\tAuthor Name");

			student s = theStudents[studentIndex];

			for (int i = 0; i < s.booksCount; i++) {

				System.out.println(s.borrowedBooks[i].sNo + "\t\t\t" + s.borrowedBooks[i].bookName + "\t\t\t"
						+ s.borrowedBooks[i].authorName);
			}

			// Display message only
			System.out.println("Enter Serial Number of Book to be Checked In:");

			int sNo = input.nextInt();

			for (int i = 0; i < s.booksCount; i++) {
				if (sNo == s.borrowedBooks[i].sNo) {
					book.checkInBook(s.borrowedBooks[i]);
					s.borrowedBooks[i] = null;

					return;
				}
			}

			System.out.println("Book of Serial No " + sNo + "not Found");
		}
	}
}

//Java Program to Illustrate Application CLass
//To Create The Menu For the Program

//Importing required classes

//Class
public class LibraryManagementSystem {

	// Main driver method
	public static void main(String[] args) {
		// Creating object of Scanner class
		// to take input from user
		Scanner input = new Scanner(System.in);

		// Displaying menu
		System.out.println("********************Welcome to the GFG Library!********************");
		System.out.println("                  Select From The Following Options:               ");
		System.out.println("**********************************************************************");

		// Creating object of book class
		books ob = new books();
		// Creating object of students class
		students obStudent = new students();

		int choice;
		int searchChoice;

		// Creating menu
		// using do-while loop
		do {

			ob.dispMenu();
			choice = input.nextInt();

			// Switch case
			switch (choice) {

			// Case
			case 1:
				book b = new book();
				ob.addBook(b);
				break;

			// Case
			case 2:
				ob.upgradeBookQty();
				break;

			// Case
			case 3:

				System.out.println(" press 1 to Search with Book Serial No.");
				System.out.println(" Press 2 to Search with Book's Author Name.");
				searchChoice = input.nextInt();

				// Nested switch
				switch (searchChoice) {

				// Case
				case 1:
					ob.searchBySno();
					break;

				// Case
				case 2:
					ob.searchByAuthorName();
				}
				break;

			// Case
			case 4:
				ob.showAllBooks();
				break;

			// Case
			case 5:
				student s = new student();
				obStudent.addStudent(s);
				break;

			// Case
			case 6:
				obStudent.showAllStudents();
				break;

			// Case
			case 7:
				obStudent.checkOutBook(ob);
				break;

			// Case
			case 8:
				obStudent.checkInBook(ob);
				break;

			// Default case that will execute for sure
			// if above cases does not match
			default:

				// Print statement
				System.out.println("ENTER BETWEEN 0 TO 8.");
			}

		}

		// Checking condition at last where we are
		// checking case entered value is not zero
		while (choice != 0);
	}
}