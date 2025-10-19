import java.util.HashMap;
import java.util.Map;

/**
 * Yeh class alag-alag warehouses ko manage karti hai.
 * Yeh ek head office ki tarah hai.
 */
public class WarehouseManager {
    // Ek map jo warehouse ke naam (e.g., "Indore") ko Warehouse object se jodta hai
    private Map<String, Warehouse> warehouses = new HashMap<>();
    private StockObserver notifier;

    public WarehouseManager() {
        // Ek hi notifier banayenge jo sabhi warehouses ke liye kaam karega
        this.notifier = new LowStockNotifier();
    }

    /**
     * Yeh method ek warehouse object deta hai.
     * Agar warehouse pehle se nahi hai, toh ek naya banakar deta hai.
     * @param name Warehouse ka naam (e.g., "Indore")
     * @return Warehouse object
     */
    public Warehouse getWarehouse(String name) {
        // check karta hai ki kya 'warehouses' map mein is naam ka warehouse pehle se hai
        if (!warehouses.containsKey(name)) {
            System.out.println("Creating a new warehouse instance for: " + name);
            // Agar nahi hai, toh ek naya Warehouse object banata hai
            Warehouse newWarehouse = new Warehouse(name); // 'name' pass kiya taaki file ka naam alag ho
            newWarehouse.addObserver(notifier); // Naye warehouse mein jasoos (notifier) ko add karta hai
            warehouses.put(name, newWarehouse); // Naye warehouse ko map mein store karta hai
        }
        // Us naam ka warehouse return kar deta hai
        return warehouses.get(name);
    }
}