import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WarehouseManager {
    private Map<String, Warehouse> warehouses = new HashMap<>();
    private StockObserver notifier;
    private SalesAnalyticsService analyticsService;

    public WarehouseManager() {
        this.notifier = new LowStockNotifier();
        this.analyticsService = new SalesAnalyticsService(); 
        loadWarehouse("Indore");
        loadWarehouse("Bhopal");
    }
    private void loadWarehouse(String name) {
        Warehouse wh = new Warehouse(name, analyticsService);
        wh.addObserver(notifier);
        warehouses.put(name, wh);
    }
    public synchronized Warehouse getWarehouse(String name) {
        return warehouses.get(name);
    }
    public void displayGlobalDashboard() {
        System.out.println("======================= SYSTEM-WIDE DASHBOARD ======================");
        System.out.println("Aap abhi " + warehouses.size() + " warehouse locations manage kar rahi hain.");
        System.out.println("\n[!! GLOBAL LOW-STOCK WARNINGS !!]");
        System.out.println("---------------------------------------------------------------------");
        
        boolean allOk = true;
        for (Warehouse wh : warehouses.values()) {
            List<Product> lowStock = wh.getLowStockProducts();
            if (!lowStock.isEmpty()) {
                allOk = false;
                for (Product p : lowStock) {
                    System.out.printf("- '%s' (%s) - Sirf %d bache hain! (Threshold: %d)\n",
                        p.getName(), wh.getWarehouseName(), p.getQuantity(), p.getThreshold());
                }
            }
        }

        if (allOk) {
            System.out.println("[OK] All warehouse stock levels are healthy.");
        }
        System.out.println("---------------------------------------------------------------------");
    }
    public void runSmartAnalysisFor(Warehouse warehouse) {
        analyticsService.runSmartAnalysis(warehouse);
    }
}