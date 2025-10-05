
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class MainApp {
	public static void main(String[] args) {
		// Step 1: Initialize EntityManagerFactory (connects to persistence unit defined
		// in persistence.xml)
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

		// Step 2: Create EntityManager (used to interact with database)
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// Step 3: Persist sample data
		persistSampleData(entityManager);

		// Step 4: Retrieve and display data
		displaySampleData(entityManager);

		// Step 5: Close resources
		entityManager.close();
		entityManagerFactory.close();
	}

	// Persisting sample Product records into the database
	private static void persistSampleData(EntityManager entityManager) {
		entityManager.getTransaction().begin(); // Begin transaction

		// Create sample products
		Product product1 = new Product();
		product1.setName("Product 1");
		product1.setPrice(10.99);

		Product product2 = new Product();
		product2.setName("Product 2");
		product2.setPrice(20.99);

		// Save to DB
		entityManager.persist(product1);
		entityManager.persist(product2);

		entityManager.getTransaction().commit(); // Commit transaction
	}

	// Fetching and displaying product records from database
	private static void displaySampleData(EntityManager entityManager) {
		List<Product> products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();

		System.out.println("Products:");
		for (Product product : products) {
			System.out.println(
					"ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice());
		}
	}
}