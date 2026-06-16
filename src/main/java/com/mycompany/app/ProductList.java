package com.mycompany.app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ProductList class manages a collection of products.
 * It provides methods to add products and list their details.
 */
public class ProductList {
    private ArrayList<Product> products;
    private boolean hasChanges = false;

    /**
     * Constructs a new ProductList with an empty list of products.
     */
    public ProductList() {
        this.products = new ArrayList<>();
    }

    /**
     * Returns whether the list has been modified since the last save/load.
     */
    public boolean hasChanges() {
        return hasChanges;
    }

    /**
     * Adds a product to the list.
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        this.products.add(product);
        this.hasChanges = true;
    }

    /**
     * Prints the details of all products in the list to the console.
     */
    public void listProducts() {
        System.out.println("Product List:");
        for (Product product : products) {
            System.out.println(product.getName() + ", Price:" + product.getPrice());
        }
    }

    /**
     * Saves the current list of products to a CSV file.
     * @param filename The name of the file to save to.
     */
    public void saveToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Product product : products) {
                writer.println(product.getName() + "," + product.getPrice());
            }
            System.out.println("Data saved to " + filename);
            this.hasChanges = false;
        } catch (IOException e) {
            System.err.println("Error saving to CSV: " + e.getMessage());
        }
    }

    /**
     * Loads products from a CSV file into the list.
     * @param filename The name of the file to load from.
     */
    public void loadFromCSV(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }

        this.products.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    this.addProduct(new Product(name, price));
                }
            }
            this.hasChanges = false;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading from CSV: " + e.getMessage());
        }
    }

    /**
     * Finds all products within a specific price range.
     * @param min The minimum price.
     * @param max The maximum price.
     * @return A list of products within the range.
     */
    public List<Product> findProductsInRange(double min, double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total value of all products in the list.
     * @return The sum of all product prices.
     */
    public double getTotalValue() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    /**
     * Deletes a product by name.
     * @param name Name of the product to delete.
     */
    public void deleteProduct(String name) {
        if (products.removeIf(p -> p.getName().equalsIgnoreCase(name))) {
            this.hasChanges = true;
            System.out.println("Product removed.");
        } else {
            System.out.println("Product not found.");
        }
    }

    /**
     * Modifies the price of an existing product.
     * @param name Name of the product.
     * @param newPrice New price to set.
     */
    public void updateProductPrice(String name, double newPrice) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.price = newPrice;
                this.hasChanges = true;
                System.out.println("Price updated.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public double getPrice(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product.getPrice();
            }
        }
        return -1;
    
    }
}