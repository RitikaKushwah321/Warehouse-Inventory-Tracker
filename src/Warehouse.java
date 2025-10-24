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
    private SalesAnalyticsService analyticsService;

    public Warehouse(String name, SalesAnalyticsService service) {
        this.warehouseName = name;
        this.filename = name + "_inventory.txt";
        this.analyticsService = service;
        loadInventoryFromFile();
    }
    
    public String getWarehouseName() { return warehouseName; }
    public synchronized Map<String, Product> getInventoryMap() { return new HashMap<>(inventory); }
    public synchronized void addObserver(StockObserver observer) { observers.add(observer); }

    private synchronized void notifyObservers(Product product) {
        for (StockObserver observer : observers) {
            observer.update(product);
        }
    }

    public synchronized void addProduct(String id, String name, int initialQuantity, int threshold) {
        Product newProduct = new Product(id, name, initialQuantity, threshold);
        inventory.put(id, newProduct);
        System.out.println("[OK] [" + warehouseName + "] Product '" + name + "' added with quantity " + initialQuantity + ".");
    }
    
    public synchronized void receiveShipment(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product != null) {
            product.increaseQuantity(quantity);
            System.out.println("[SHIPMENT] [" + warehouseName + "] Received " + quantity + " units of '" + product.getName() + "'. New quantity: " + product.getQuantity());
        } else {
            System.out.println("[ERROR] [" + warehouseName + "] Error: Product with ID " + productId + " not found.");
        }
    }

    public synchronized void fulfillOrder(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("[ERROR] [" + warehouseName + "] Error: Product with ID " + productId + " not found.");
            return;
        }

        if (product.getQuantity() >= quantity) {
            product.decreaseQuantity(quantity);
            System.out.println("[ORDER] [" + warehouseName + "] Fulfilled order for " + quantity + " units of '" + product.getName() + "'. Remaining: " + product.getQuantity());
            
            analyticsService.logSale(this.warehouseName, productId, quantity);
            
            if (product.getQuantity() < product.getThreshold()) {
                notifyObservers(product);
            }
        } else {
            System.out.println("[ERROR] [" + warehouseName + "] Error: Insufficient stock. Required: " + quantity + ", Available: " + product.getQuantity());
        }
    }
    
    public synchronized void updateProduct(String id, String newName, int newThreshold) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setName(newName);
            product.setThreshold(newThreshold);
            System.out.println("[OK] [" + warehouseName + "] Product " + id + " updated successfully.");
        } else {
            System.out.println("[ERROR] [" + warehouseName + "] Error: Product with ID " + id + " not found.");
        }
    }

    public synchronized void deleteProduct(String id) {
        if (inventory.containsKey(id)) {
            inventory.remove(id);
            System.out.println("[DELETED] [" + warehouseName + "] Product " + id + " deleted successfully.");
        } else {
            System.out.println("[ERROR] [" + warehouseName + "] Error: Product with ID " + id + " not found.");
        }
    }
    
    public synchronized void displayInventory() {
        System.out.println("\n----------- INVENTORY FOR " + warehouseName.toUpperCase() + " WAREHOUSE -----------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s |\n", "ID", "Name", "Quantity", "Threshold");
        System.out.println("-----------------------------------------------------------");
        if (inventory.isEmpty()) {
            System.out.println("| No products in this warehouse yet.                     |");
        } else {
            for (Product product : inventory.values()) {
                System.out.printf("| %-10s | %-20s | %-10d | %-10d |\n",
                    product.getId(), product.getName(), product.getQuantity(), product.getThreshold());
            }
        }
        System.out.println("-----------------------------------------------------------\n");
    }

    public synchronized void saveInventoryToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Product product : inventory.values()) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getQuantity() + "," + product.getThreshold() + "\n");
            }
            System.out.println("[SAVED] [" + warehouseName + "] Inventory saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("[ERROR] [" + warehouseName + "] Error saving inventory: " + e.getMessage());
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
            System.out.println("[LOADED] [" + warehouseName + "] Inventory loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("[INFO] No existing inventory file found for " + warehouseName + ". Starting fresh.\n");
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] [" + warehouseName + "] Error parsing number from file: " + e.getMessage());
        }
    }

    public synchronized List<Product> getLowStockProducts() {
        List<Product> lowStockItems = new ArrayList<>();
        for (Product product : inventory.values()) {
            if (product.getQuantity() < product.getThreshold()) {
                lowStockItems.add(product);
            }
        }
        return lowStockItems;
    }
}