# Warehouse Inventory Tracker (Event-Based) 🚀

## 📝 Project Description

This is a Java-based console application designed to simulate a real-time, event-driven warehouse inventory management system. It allows for tracking products, managing stock levels, and automatically triggers alerts when inventory for a product falls below a predefined reorder threshold.

This project goes beyond the basic requirements by implementing **all 3 bonus challenges** (Persistence, Multi-Warehouse, Multithreading) plus **2 innovative custom features**: a global dashboard and a predictive sales analysis system.

---

## ✨ Features

* **Full Product Management**: Add, **Update**, and **Delete** products with unique IDs, names, quantities, and reorder thresholds.
* **Stock Control**:
    * `Receive Shipment`: Increase the quantity of a product when new stock arrives.
    * `Fulfill Order`: Decrease the quantity when a customer order is processed.
* **Real-time Alerts (Observer Pattern)**: Automatically triggers a console alert (`[!! ALERT !!]`) whenever a product's quantity drops below its threshold.
* **Data Persistence**: The inventory state for each warehouse is saved to a separate text file (e.g., `Indore_inventory.txt`), so data is not lost between sessions.
* **Multi-Warehouse Simulation**: The application can manage inventories for multiple warehouse locations (e.g., Indore, Bhopal) independently.
* **Thread-Safe Operations (Multithreading)**: All inventory operations are `synchronized`, preventing data corruption or "race conditions" from simultaneous updates.
* **Global Dashboard**: Starts with a high-level summary showing low-stock warnings from *all* warehouses at a glance.
* **Predictive "Smart" Analysis**: A custom feature that analyzes sales data (`sales_log.txt`) to forecast how many days of stock are left based on recent sales trends.
* **Robust Error Handling**: Gracefully handles invalid inputs (like non-numeric choices) and logical errors (like insufficient stock or invalid product IDs).

---

## 🛠️ Built With

* **Language**: Java
* **Core Concepts**: Object-Oriented Programming (Encapsulation, Abstraction), Design Patterns (Observer), Java Multithreading (`synchronized`).
* **Data Storage**: In-memory `HashMap` (for efficient product lookup) and simple `.txt` files for persistence and sales logging.
* **Data Analysis**: Custom logic for simple sales forecasting from a log file.

---

## 🚀 How to Run the Project

1.  **Prerequisites**:
    * Java Development Kit (JDK) 11 or higher installed.
    * Git installed on your system.
    * Visual Studio Code with the "Extension Pack for Java" installed.

2.  **Clone the Repository**:
    ```bash
    git clone (https://github.com/RitikaKushwah321/Warehouse-Inventory-Tracker.git)
    ```

3.  **Open in VS Code**:
    * Open the cloned project folder in Visual Studio Code.

4.  **Run the Application**:
    * Open the `src/Main.java` file.
    * Click the "Run" button that appears above the `main` method.
    * Follow the on-screen menu prompts in the terminal.