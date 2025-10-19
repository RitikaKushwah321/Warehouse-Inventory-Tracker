import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Main logic ko ek alag method mein daala taaki recursion saaf dikhe
    public static void startSystem() {
        Scanner scanner = new Scanner(System.in);
        WarehouseManager manager = new WarehouseManager();
        Warehouse currentWarehouse = null;

        // Loop 1: Warehouse Selection
        while (currentWarehouse == null) {
            System.out.println("\n===== Select a Warehouse =====");
            System.out.println("1. Indore Warehouse");
            System.out.println("2. Bhopal Warehouse");
            System.out.println("3. Exit System");
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        currentWarehouse = manager.getWarehouse("Indore");
                        break;
                    case 2:
                        currentWarehouse = manager.getWarehouse("Bhopal");
                        break;
                    case 3:
                        System.out.println("--- System Shutdown ---");
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        System.out.println("\n---> Welcome to the " + currentWarehouse.getWarehouseName() + " inventory system! <---");

        // Loop 2: Operations Menu
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n===== MENU for " + currentWarehouse.getWarehouseName().toUpperCase() + " =====");
            System.out.println("1. Add New Product");
            System.out.println("2. Receive Shipment");
            System.out.println("3. Fulfill Order");
            System.out.println("4. View All Products");
            System.out.println("5. Switch to another Warehouse");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

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
                        scanner.nextLine();
                        currentWarehouse.addProduct(id, name, qty, threshold);
                        break;
                    case 2:
                        System.out.print("Enter Product ID to restock: ");
                        String shipId = scanner.nextLine();
                        System.out.print("Enter Quantity to add: ");
                        int shipQty = scanner.nextInt();
                        scanner.nextLine();
                        currentWarehouse.receiveShipment(shipId, shipQty);
                        break;
                    case 3:
                        System.out.print("Enter Product ID to sell: ");
                        String orderId = scanner.nextLine();
                        System.out.print("Enter Quantity to sell: ");
                        int orderQty = scanner.nextInt();
                        scanner.nextLine();
                        currentWarehouse.fulfillOrder(orderId, orderQty);
                        break;
                    case 4:
                        currentWarehouse.displayInventory();
                        break;
                    case 5:
                        currentWarehouse.saveInventoryToFile();
                        startSystem(); // Poore system ko restart kar dega
                        return;
                    case 6:
                        currentWarehouse.saveInventoryToFile();
                        System.out.println("\n--- System Shutdown ---");
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please select a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number only.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        System.out.println("--- Multi-Warehouse Inventory System ---");
        startSystem();
    }
}