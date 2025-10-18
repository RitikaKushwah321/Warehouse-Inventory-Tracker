// src/Product.java

public class Product {
    private String id;
    private String name;
    private int quantity;
    private int threshold;

    public Product(String id, String name, int quantity, int threshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    // Getter methods
    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getThreshold() { return threshold; }

    // Methods to change quantity
    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}