// src/Main.java

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        StockObserver notifier = new LowStockNotifier();
        warehouse.addObserver(notifier);
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Warehouse Inventory System Initialized ---");

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add New Product");
            System.out.println("2. Receive Shipment (Add Stock)");
            System.out.println("3. Fulfill Order (Remove Stock)");
            System.out.println("4. View All Products");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Product ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Initial Quantity: ");
                        int qty = scanner.nextInt();
                        System.out.print("Enter Reorder Threshold: ");
                        int threshold = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        warehouse.addProduct(id, name, qty, threshold);
                        break;

                    case 2:
                        System.out.print("Enter Product ID to restock: ");
                        String shipId = scanner.nextLine();
                        System.out.print("Enter Quantity to add: ");
                        int shipQty = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        warehouse.receiveShipment(shipId, shipQty);
                        break;

                    case 3:
                        System.out.print("Enter Product ID to sell: ");
                        String orderId = scanner.nextLine();
                        System.out.print("Enter Quantity to sell: ");
                        int orderQty = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        warehouse.fulfillOrder(orderId, orderQty);
                        break;

                    case 4:
                        warehouse.displayInventory();
                        break;

                    case 5:
                        warehouse.saveInventoryToFile();
                        System.out.println("\n--- System Shutdown ---");
                        scanner.close();
                        return;

                    default:
                        System.out.println("❌ Invalid choice. Please select a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number only.");
                scanner.nextLine(); // Clear the bad input
            }
        }
    }
}