package com.mycompany.app;

import java.util.Scanner;

/**
 * The App class serves as the entry point for the application.
 * It demonstrates the creation and display of a list of products.
 */
public class App {
    /**
     * The main method initializes a list of products and prints their details to the console.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        final String FILE_NAME = "products.csv";
        ProductList productList = new ProductList();
        productList.loadFromCSV(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Product Management Menu ---");
            System.out.println("1. Load Product List");
            System.out.println("2. Save Product List (only if changed)");
            System.out.println("3. Show Product List");
            System.out.println("4. Add a Product");
            System.out.println("5. Find Products in a Price Range");
            System.out.println("6. Find A Product's Price");
            System.out.println("7. Delete A Product");
            System.out.println("8. Modify Product Price");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    productList.loadFromCSV(FILE_NAME);
                    System.out.println("Product list loaded.");
                    break;
                case "2":
                    if (productList.hasChanges()) {
                        productList.saveToCSV(FILE_NAME);
                    } else {
                        System.out.println("No changes to save.");
                    }
                    break;
                case "3":
                    productList.listProducts();
                    System.out.println("Total Inventory Value: " + productList.getTotalValue());
                    break;
                case "4":
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    try {
                        double price = Double.parseDouble(scanner.nextLine());
                        productList.addProduct(new Product(name, price));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price format.");
                    }
                    break;
                case "5":
                    try {
                        System.out.print("Enter min price: ");
                        double min = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter max price: ");
                        double max = Double.parseDouble(scanner.nextLine());
                        System.out.println("Results:");
                        productList.findProductsInRange(min, max).forEach(p -> 
                            System.out.println(p.getName() + " - " + p.getPrice()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid numeric input.");
                    }
                    break;
                case "6":
                    System.out.print("Enter product name: ");
                    String searchName = scanner.nextLine();
                    double price = productList.getPrice(searchName);
                    if (price != -1) {
                        System.out.println("Price: " + price);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case "7":
                    System.out.print("Enter product name to delete: ");
                    productList.deleteProduct(scanner.nextLine());
                    break;
                case "8":
                    System.out.print("Enter product name: ");
                    String modName = scanner.nextLine();
                    System.out.print("Enter new price: ");
                    try {
                        double newPrice = Double.parseDouble(scanner.nextLine());
                        productList.updateProductPrice(modName, newPrice);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price.");
                    }
                    break;
                case "9":
                    if (productList.hasChanges()) {
                        System.out.print("Unsaved changes detected. Save now? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            productList.saveToCSV(FILE_NAME);
                        }
                    }
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
