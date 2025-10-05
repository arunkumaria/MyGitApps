package manyToMany;

import java.util.HashSet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GeekEmployeeManytoManyExample {
	private static SessionFactory factory;

	public static void main(String[] args) {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		GeekEmployeeManytoManyExample app = new GeekEmployeeManytoManyExample();

		HashSet<SkillsetData> skillSets = new HashSet();
		skillSets.add(new SkillsetData("Java"));
		skillSets.add(new SkillsetData("Python"));
		skillSets.add(new SkillsetData("R Programming"));

		HashSet<SkillsetData> databaseSkillSets = new HashSet();
		databaseSkillSets.add(new SkillsetData("MySQL"));
		databaseSkillSets.add(new SkillsetData("SQL Server"));
		databaseSkillSets.add(new SkillsetData("MongoDB"));

		app.addEmployee("GeekA", "GeekA", 100000, skillSets);
		app.addEmployee("GeekB", "GeekB", 50000, databaseSkillSets);
		app.addEmployee("GeekC", "GeekC", 50000, skillSets);

		//app.listGeekEmployeeData();

		app.listGeekEmployeesByNameAndSalaryCriteria();
	}

	public Integer addEmployee(String fname, String lname, int salary, Set skillSets) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			GeekEmployeeData employee = new GeekEmployeeData(fname, lname, salary);
			employee.setSkillSets(skillSets);
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}

	public void listGeekEmployeeData() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Use JPA Criteria API instead of session.createCriteria()
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<GeekEmployeeData> cq = cb.createQuery(GeekEmployeeData.class);
			Root<GeekEmployeeData> root = cq.from(GeekEmployeeData.class);

			cq.select(root); // SELECT * FROM GeekEmployeeData

			List<GeekEmployeeData> employees = session.createQuery(cq).getResultList();

			for (GeekEmployeeData emp : employees) {
				System.out.print("First Name: " + emp.getFirstName());
				System.out.print(" Last Name: " + emp.getLastName());
				System.out.println(" Salary: " + emp.getSalary());

				Set skillSets = emp.getSkillSets();
				for (Iterator it = skillSets.iterator(); it.hasNext();) {
					SkillsetData skill = (SkillsetData) it.next();
					System.out.println("SkillName: " + skill.getSkillName());
				}
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void listGeekEmployeesByNameAndSalaryCriteria() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// --- Use CriteriaBuilder ---
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<GeekEmployeeData> cq = cb.createQuery(GeekEmployeeData.class);
			Root<GeekEmployeeData> root = cq.from(GeekEmployeeData.class);

			// Define predicates
			Predicate salaryPredicate = cb.gt(root.get("salary"), 50000);
			Predicate namePredicate = cb.like(cb.lower(root.get("firstName")), "geek%"); // case-insensitive

			// Combine predicates with AND
			cq.select(root).where(cb.and(salaryPredicate, namePredicate));

			// Execute query
			List<GeekEmployeeData> geekEmployeeList = session.createQuery(cq).getResultList();

			for (GeekEmployeeData employeeData : geekEmployeeList) {
				System.out.print("First Name: " + employeeData.getFirstName());
				System.out.print("  Last Name: " + employeeData.getLastName());
				System.out.println("  Salary: " + employeeData.getSalary());

				// Iterate over skill sets
				Set<SkillsetData> skillSets = employeeData.getSkillSets();
				for (SkillsetData skill : skillSets) {
					System.out.println("SkillName: " + skill.getSkillName());
				}
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
