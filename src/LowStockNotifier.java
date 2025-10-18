// src/LowStockNotifier.java

public class LowStockNotifier implements StockObserver {
    @Override
    public void update(Product product) {
        System.out.println("🚨 ALERT: Low stock for " + product.getName() +
                           " – only " + product.getQuantity() + " left! Please restock.");
    }
}