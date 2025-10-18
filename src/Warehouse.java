// src/Warehouse.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private Map<String, Product> inventory = new HashMap<>();
    private List<StockObserver> observers = new ArrayList<>();
    private static final String FILENAME = "inventory.txt";

    public Warehouse() {
        loadInventoryFromFile();
    }

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }



    private void notifyObservers(Product product) {
        for (StockObserver observer : observers) {
            observer.update(product);
        }
    }

    public void addProduct(String id, String name, int initialQuantity, int threshold) {
        Product newProduct = new Product(id, name, initialQuantity, threshold);
        inventory.put(id, newProduct);
        System.out.println("✅ Product '" + name + "' added with quantity " + initialQuantity + ".");
    }

    public void receiveShipment(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product != null) {
            product.increaseQuantity(quantity);
            System.out.println("🚚 Received " + quantity + " units of '" + product.getName() + "'. New quantity: " + product.getQuantity());
        } else {
            System.out.println("❌ Error: Product with ID " + productId + " not found.");
        }
    }

    public void fulfillOrder(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("❌ Error: Product with ID " + productId + " not found.");
            return;
        }

        if (product.getQuantity() >= quantity) {
            product.decreaseQuantity(quantity);
            System.out.println("🛒 Fulfilled order for " + quantity + " units of '" + product.getName() + "'. Remaining: " + product.getQuantity());
            if (product.getQuantity() < product.getThreshold()) {
                notifyObservers(product);
            }
        } else {
            System.out.println("❌ Error: Insufficient stock for '" + product.getName() + "'. Required: " + quantity + ", Available: " + product.getQuantity());
        }
    }

    public void saveInventoryToFile() {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            for (Product product : inventory.values()) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getQuantity() + "," + product.getThreshold() + "\n");
            }
            System.out.println("💾 Inventory saved successfully to " + FILENAME);
        } catch (IOException e) {
            System.err.println("❌ Error saving inventory to file: " + e.getMessage());
        }
    }

    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    int threshold = Integer.parseInt(parts[3]);
                    inventory.put(id, new Product(id, name, quantity, threshold));
                }
            }
            System.out.println("📂 Inventory loaded successfully from " + FILENAME);
        } catch (IOException e) {
            System.out.println("📝 No existing inventory file found. Starting fresh.");
        } catch (NumberFormatException e) {
            System.err.println("❌ Error parsing number from file: " + e.getMessage());
        }
    }

    /**
     * Saare products ko ek formatted table mein console par display karta hai.
     */
    public void displayInventory() {
        System.out.println("\n-------------------- CURRENT INVENTORY --------------------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "ID", "Name", "Quantity", "Threshold");
        System.out.println("---------------------------------------------------------");

        if (inventory.isEmpty()) {
            System.out.println("| No products in the warehouse yet.                     |");
        } else {
            for (Product product : inventory.values()) {
                System.out.printf("| %-10s | %-20s | %-10d | %-10d |\n",
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    product.getThreshold());
            }
        }
        System.out.println("---------------------------------------------------------");
    }
}