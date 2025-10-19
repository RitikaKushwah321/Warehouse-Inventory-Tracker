# Warehouse Inventory Tracker (Event-Based)

## 📝 Project Description

This is a Java-based console application designed to simulate a real-time, event-driven warehouse inventory management system. It allows for tracking products, managing stock levels, and automatically triggers alerts when inventory for a product falls below a predefined reorder threshold. The system is built using core OOP principles and leverages the Observer design pattern for handling low-stock notifications.

---

## ✨ Features

* **Product Management**: Add new products with unique IDs, names, initial quantities, and reorder thresholds.
* **Stock Control**:
    * `Receive Shipment`: Increase the quantity of a product when new stock arrives.
    * `Fulfill Order`: Decrease the quantity when a customer order is processed.
* **Real-time Alerts (Observer Pattern)**: Automatically triggers a "Restock Alert" on the console whenever a product's quantity drops below its threshold.
* **Data Persistence**: The inventory state for each warehouse is saved to a separate text file (e.g., `Indore_inventory.txt`), so data is not lost between sessions.
* **Multi-Warehouse Simulation**: The application can manage inventories for multiple warehouse locations (e.g., Indore, Bhopal) independently.
* **Interactive Console Menu**: A user-friendly, menu-driven interface to perform all operations.
* **Robust Error Handling**: Gracefully handles invalid inputs (like non-numeric choices) and logical errors (like insufficient stock or invalid product IDs).

---

## 🛠️ Built With

* **Language**: Java
* **Core Concepts**: Object-Oriented Programming (Encapsulation, Abstraction), Design Patterns (Observer).
* **Data Storage**: In-memory `HashMap` for efficient data retrieval and simple `.txt` files for persistence.

---

## 🚀 How to Run the Project

1.  **Prerequisites**:
    * Java Development Kit (JDK) 11 or higher installed.
    * Git installed on your system.
    * Visual Studio Code with the "Extension Pack for Java" installed.

2.  **Clone the Repository**:
    ```bash
    git clone [Aapki GitHub Repository ka Link Yahan Daalein]
    ```

3.  **Open in VS Code**:
    * Open the cloned project folder in Visual Studio Code.

4.  **Run the Application**:
    * Open the `src/Main.java` file.
    * Click the "Run" button that appears above the `main` method.
    * Follow the on-screen menu prompts in the terminal.