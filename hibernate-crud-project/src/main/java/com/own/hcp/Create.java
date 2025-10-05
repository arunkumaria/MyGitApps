package com.own.hcp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.own.hcp.entity.*;
import com.own.hcp.util.*;

public class Create {
	public static void main(String[] args) {
		SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		// Create a new student
		Student s = new Student(101, "John", 10);
		session.saveOrUpdate(s);

		tx.commit();
		session.close();
		sessionFactory.close();

		System.out.println("Student record created successfully!");
	}
}

//public class Retrieve {
//	public static void main(String[] args) {
//		SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();
//		Session session = sessionFactory.openSession();
//
//		Student s = session.get(Student.class, 101); // Fetch by ID
//		if (s != null) {
//			System.out.println("Id: " + s.getId());
//			System.out.println("Name: " + s.getName());
//			System.out.println("Class: " + s.getStd());
//		} else {
//			System.out.println("Student not found.");
//		}
//		session.close();
//		sessionFactory.close();
//	}
//}
//
//public class Retrieve {
//	public static void main(String[] args) {
//		SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();
//		Session session = sessionFactory.openSession();
//
//		// Fetching object using get()
//		System.out.println("Fetching object using get:");
//		Student s1 = session.get(Student.class, 102);
//		if (s1 != null) {
//			System.out.println("Id: " + s1.getId());
//			System.out.println("Name: " + s1.getName());
//			System.out.println("Class: " + s1.getStd());
//		} else {
//			System.out.println("Student not found.");
//		}
//
//		sessionFactory.close();
//	}
//}
//
//public class Update {
//	public static void main(String[] args) {
//		SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//
//		Student s = session.get(Student.class, 101);
//		s.setStd(11); // Update class
//		session.saveOrUpdate(s);
//
//		tx.commit();
//		session.close();
//		sessionFactory.close();
//
//		System.out.println("Student record updated successfully!");
//	}
//}
//
//
//public class Delete {
//	public static void main(String[] args) {
//		SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//
//		Student s = session.get(Student.class, 101);
//		if (s != null) {
//			session.delete(s);
//			System.out.println("Student record deleted successfully!");
//		} else {
//			System.out.println("Student not found.");
//		}
//
//		tx.commit();
//		session.close();
//		sessionFactory.close();
//	}
//}
