import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void startSystem(WarehouseManager manager) {
        Warehouse currentWarehouse = null;

        manager.displayGlobalDashboard();
        System.out.print("\nPress Enter to continue to warehouse selection...");
        scanner.nextLine();

        while (currentWarehouse == null) {
            System.out.println("\n======================= Select a Warehouse =======================");
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
                        System.out.println("------------ System Shutdown (from selection) ------------");
                        return;
                    default:
                        System.out.println("[ERROR] Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }

        System.out.println("\n--------------------------> Welcome to the " + currentWarehouse.getWarehouseName() + " inventory system! <--------------------------");

        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n===================== MENU for " + currentWarehouse.getWarehouseName().toUpperCase() + " =======================");
            System.out.println("1. Add Product");
            System.out.println("2. Add Stock (Receive Shipment)");
            System.out.println("3. Process Order (Sell Stock)");
            System.out.println("4. Display Inventory");
            System.out.println("5. Update Product");
            System.out.println("6. Delete Product");
            System.out.println("7. Change Warehouse");
            System.out.println("8. Run Smart Analysis");
            System.out.println("9. Exit");
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
                        System.out.print("Enter Product ID to update: ");
                        String updateId = scanner.nextLine();
                        System.out.print("Enter new Product Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new Reorder Threshold: ");
                        int newThreshold = scanner.nextInt();
                        scanner.nextLine();
                        currentWarehouse.updateProduct(updateId, newName, newThreshold);
                        break;
                    case 6:
                        System.out.print("Enter Product ID to delete: ");
                        String deleteId = scanner.nextLine();
                        currentWarehouse.deleteProduct(deleteId);
                        break;
                    case 7:
                        currentWarehouse.saveInventoryToFile();
                        startSystem(manager);
                        return;
                    case 8:
                        manager.runSmartAnalysisFor(currentWarehouse);
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;
                    case 9:
                        currentWarehouse.saveInventoryToFile();
                        System.out.println("\n---------------- System Shutdown (from menu) ------------");
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("[ERROR] Invalid choice. Please select a number between 1 and 9.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Invalid input! Please enter a number only.");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("\n<------------------------ Multi-Warehouse Inventory System --------------------------->\n");
        WarehouseManager manager = new WarehouseManager();
        startSystem(manager);
        scanner.close(); 
        System.out.println("Program terminated.");
    }
}