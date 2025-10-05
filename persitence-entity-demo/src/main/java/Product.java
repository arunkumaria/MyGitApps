
import jakarta.persistence.*;

@Entity
@Table(name = "products") // customize table name
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment PK
  private Long id;

  private String name;

  private double price;

  // JPA requires a no-arg constructor
  public Product() {}

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }
}