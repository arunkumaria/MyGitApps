
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//public class AddingEntryDemo {
//
//	public static void main(String[] args) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(StudentGfgDetail.class).buildSessionFactory();
//
//		try (factory; Session session = factory.getCurrentSession()) {
//
//			Student student = new Student("Vyom", "Yadav", "vyom@gmail.com");
//			StudentGfgDetail studentGfgDetail = new StudentGfgDetail("GFG College", 20);
//
//			student.setStudentGfgDetail(studentGfgDetail);
//
//			session.beginTransaction();
//			session.save(student); // CascadeType.ALL saves both
//			session.getTransaction().commit();
//
//			System.out.println("Transaction Successfully Completed!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}

//public class UpdateEntryDemo {
//
//    public static void main(String[] args) {
//        SessionFactory factory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(Student.class)
//                .addAnnotatedClass(StudentGfgDetail.class)
//                .buildSessionFactory();
//
//        try (factory; Session session = factory.getCurrentSession()) {
//            session.beginTransaction();
//
//            Student student = session.get(Student.class, 1);
//            StudentGfgDetail detail = student.getStudentGfgDetail();
//
//            student.setEmail("vyom@geeksforgeeks.com");
//            detail.setNoOfProblemsSolved(40);
//
//            session.update(student); // CascadeType.ALL updates both
//            session.getTransaction().commit();
//
//            System.out.println("Transaction Successfully Completed!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

//public class AddEntryBidirectionalDemo {
//
//	public static void main(String[] args) {
//
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(StudentGfgDetail.class).buildSessionFactory();
//
//		try (factory; Session session = factory.getCurrentSession()) {
//
//			Student student = new Student("JJ", "Olatunji", "jj@gmail.com");
//			StudentGfgDetail studentGfgDetail = new StudentGfgDetail("GFG College", 0);
//
//			student.setStudentGfgDetail(studentGfgDetail);
//			studentGfgDetail.setStudent(student);
//
//			session.beginTransaction();
//			session.save(studentGfgDetail); // Saves both StudentGfgDetail and Student
//			session.getTransaction().commit();
//
//			System.out.println("Transaction Successfully Completed!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}


public class ReadEntryBidirectionalDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(StudentGfgDetail.class)
                .buildSessionFactory();

        try (factory; Session session = factory.getCurrentSession()) {

            session.beginTransaction();

            int theId = 1; // Change according to your DB
            StudentGfgDetail studentGfgDetail = session.get(StudentGfgDetail.class, theId);

            System.out.println(studentGfgDetail.getStudent());
            System.out.println(studentGfgDetail);

            session.getTransaction().commit();

            System.out.println("Transaction Successfully Completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}