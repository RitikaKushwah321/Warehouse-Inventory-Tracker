import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SalesAnalyticsService {
    private static final String LOG_FILE = "sales_log.txt";
    private static final long SEVEN_DAYS_MS = 7 * 24 * 60 * 60 * 1000L; 
    public synchronized void logSale(String warehouseName, String productId, int quantity) {
        
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            long timestamp = System.currentTimeMillis();
            writer.write(timestamp + "," + warehouseName + "," + productId + "," + quantity + "\n");
        } catch (IOException e) {
            System.err.println("[ERROR] Error logging sale: " + e.getMessage());
        }
    }
    public void runSmartAnalysis(Warehouse warehouse) {
        System.out.println("\n==================SMART SALES ANALYSIS (Last 7 Days) ================");
        System.out.println("Warehouse: " + warehouse.getWarehouseName());
        
        long sevenDaysAgo = System.currentTimeMillis() - SEVEN_DAYS_MS;
        
        Map<String, Integer> salesOverPeriod = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    long timestamp = Long.parseLong(parts[0]);
                    String whName = parts[1];
                    String productId = parts[2];
                    int quantity = Integer.parseInt(parts[3]);

                    if (whName.equals(warehouse.getWarehouseName()) && timestamp >= sevenDaysAgo) {
                        salesOverPeriod.put(productId, salesOverPeriod.getOrDefault(productId, 0) + quantity);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("[INFO] No sales data found or error reading log: " + e.getMessage());
        }

        Map<String, Product> currentInventory = warehouse.getInventoryMap();
        if (currentInventory.isEmpty()) {
            System.out.println("No products in this warehouse to analyze.");
            return;
        }

        for (Product product : currentInventory.values()) {
            int totalSold = salesOverPeriod.getOrDefault(product.getId(), 0);
            double averageDailySales = totalSold / 7.0; 
            int currentStock = product.getQuantity();

            System.out.printf("\n- Product: '%s'\n", product.getName());
            System.out.printf("  Stock: %d | Pichle 7 din mein bike: %d\n", currentStock, totalSold);
            System.out.printf("  Average Sales: %.1f units/day\n", averageDailySales);

            if (averageDailySales > 0) {
                double daysLeft = currentStock / averageDailySales;
                if (daysLeft <= 10.0) { 
                    System.out.printf("  [PREDICTION] Stock agle %.1f dinon mein khatam ho sakta hai!\n", daysLeft);
                } else {
                    System.out.printf("  [OK] Healthy Stock: Stock agle %.1f dinon mein khatam hoga.\n", daysLeft);
                }
            } else {
                System.out.println("  [OK] No sales in the last 7 days.");
            }
        }
        System.out.println("-------------------------------------------------------------------------");
    }
}