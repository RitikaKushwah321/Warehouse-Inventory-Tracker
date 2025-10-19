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
    private String warehouseName;
    private String filename;

    public Warehouse(String name) {
        this.warehouseName = name;
        this.filename = name + "_inventory.txt";
        loadInventoryFromFile();
    }

    // === YEH METHOD CLASS KE ANDAR HONA CHAHIYE ===
    /**
     * Warehouse ka naam return karta hai.
     */
    public String getWarehouseName() {
        return warehouseName;
    }
    // ===============================================

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
        System.out.println("✅ [" + warehouseName + "] Product '" + name + "' added with quantity " + initialQuantity + ".");
    }

    public void receiveShipment(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product != null) {
            product.increaseQuantity(quantity);
            System.out.println("🚚 [" + warehouseName + "] Received " + quantity + " units of '" + product.getName() + "'. New quantity: " + product.getQuantity());
        } else {
            System.out.println("❌ [" + warehouseName + "] Error: Product with ID " + productId + " not found.");
        }
    }

    public void fulfillOrder(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("❌ [" + warehouseName + "] Error: Product with ID " + productId + " not found.");
            return;
        }

        if (product.getQuantity() >= quantity) {
            product.decreaseQuantity(quantity);
            System.out.println("🛒 [" + warehouseName + "] Fulfilled order for " + quantity + " units of '" + product.getName() + "'. Remaining: " + product.getQuantity());
            if (product.getQuantity() < product.getThreshold()) {
                notifyObservers(product);
            }
        } else {
            System.out.println("❌ [" + warehouseName + "] Error: Insufficient stock. Required: " + quantity + ", Available: " + product.getQuantity());
        }
    }

    public void saveInventoryToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Product product : inventory.values()) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getQuantity() + "," + product.getThreshold() + "\n");
            }
            System.out.println("💾 [" + warehouseName + "] Inventory saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("❌ [" + warehouseName + "] Error saving inventory: " + e.getMessage());
        }
    }

    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    inventory.put(parts[0], new Product(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                }
            }
            System.out.println("📂 [" + warehouseName + "] Inventory loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("📝 No existing inventory file found for " + warehouseName + ". Starting fresh.");
        } catch (NumberFormatException e) {
            System.err.println("❌ [" + warehouseName + "] Error parsing number from file: " + e.getMessage());
        }
    }

    public void displayInventory() {
        System.out.println("\n----------- INVENTORY FOR " + warehouseName.toUpperCase() + " WAREHOUSE -----------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "ID", "Name", "Quantity", "Threshold");
        System.out.println("---------------------------------------------------------");

        if (inventory.isEmpty()) {
            System.out.println("| No products in this warehouse yet.                     |");
        } else {
            for (Product product : inventory.values()) {
                System.out.printf("| %-10s | %-20s | %-10d | %-10d |\n",
                    product.getId(), product.getName(), product.getQuantity(), product.getThreshold());
            }
        }
        System.out.println("---------------------------------------------------------");
    }

} // <<<<<<<<<<<< YEH WAREHOUSE CLASS KA AAKHRI BRACE HAI