package project;
import java.util.ArrayList;
import java.util.Scanner;

// Class for Product
class Product {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return "Product Name: " + name + ", Quantity: " + quantity + ", Price: $" + price;
    }
}

// Main Inventory Management System
public class InventoryManagementSystem {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final int LOW_STOCK_THRESHOLD = 5;
    private static ArrayList<Product> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (login()) {
            System.out.println("Login Successful! Welcome to the Inventory Management System.");
            while (true) {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> removeProduct();
                    case 3 -> displayProducts();
                    case 4 -> updateProduct();
                    case 5 -> alertLowStock();
                    case 6 -> searchProduct();
                    case 7 -> generateStockReport();
                    case 8 -> {
                        System.out.println("Exiting the system. Goodbye!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid login credentials. Exiting...");
        }
    }

    private static boolean login() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }

    private static void displayMenu() {
        System.out.println("\nInventory Management System Menu:");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Display Products");
        System.out.println("4. Update Product");
        System.out.println("5. Low Stock Alert");
        System.out.println("6. Search Product");
        System.out.println("7. Generate Stock Report");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        inventory.add(new Product(name, quantity, price));
        System.out.println("Product added successfully!");
    }

    private static void removeProduct() {
        System.out.print("Enter Product Name to Remove: ");
        String name = scanner.nextLine();
        inventory.removeIf(product -> product.getName().equalsIgnoreCase(name));
        System.out.println("Product removed successfully (if it existed).");
    }

    private static void displayProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            System.out.println("Inventory:");
            for (Product product : inventory) {
                System.out.println(product);
            }
        }
    }

    private static void updateProduct() {
        System.out.print("Enter Product Name to Update: ");
        String name = scanner.nextLine();
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter New Quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter New Price: ");
                double price = scanner.nextDouble();
                product.setQuantity(quantity);
                product.setPrice(price);
                System.out.println("Product updated successfully!");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void alertLowStock() {
        System.out.println("Low Stock Products (Quantity < " + LOW_STOCK_THRESHOLD + "):");
        boolean found = false;
        for (Product product : inventory) {
            if (product.getQuantity() < LOW_STOCK_THRESHOLD) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products with low stock.");
        }
    }

    private static void searchProduct() {
        System.out.print("Enter Product Name to Search: ");
        String name = scanner.nextLine();
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("Product Found: " + product);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void generateStockReport() {
        if (inventory.isEmpty()) {
            System.out.println("No products in inventory to generate a report.");
        } else {
            double totalValue = 0;
            System.out.println("\nStock Report:");
            System.out.println("----------------------------------------------------");
            System.out.println("Product Name\tQuantity\tPrice\tTotal Value");
            System.out.println("----------------------------------------------------");
            for (Product product : inventory) {
                double value = product.getTotalValue();
                totalValue += value;
                System.out.printf("%-15s\t%-10d\t$%-8.2f\t$%-10.2f\n", 
                                  product.getName(), product.getQuantity(), 
                                  product.getPrice(), value);
            }
            System.out.println("----------------------------------------------------");
            System.out.printf("Total Inventory Value: $%.2f\n", totalValue);
        }
    }
}
