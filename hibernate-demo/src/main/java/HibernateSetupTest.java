import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSetupTest {
	public static void main(String[] args) {
		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			System.out.println("Hibernate setup successful!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}