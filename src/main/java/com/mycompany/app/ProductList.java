package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The ProductList class manages a collection of products.
 * It provides methods to add products and list their details.
 */
@Service
public class ProductList {
    private ArrayList<Product> products;
    private boolean hasChanges = false;
    private ProductRepository repository;

    /**
     * Constructs a new ProductList with an empty list of products.
     */
    public ProductList() {
        this.products = new ArrayList<>();
    }

    /**
     * Constructs a new ProductList with a custom repository.
     */
    @Autowired
    public ProductList(ProductRepository repository) {
        this.products = new ArrayList<>();
        this.repository = repository;
    }

    public ProductRepository getRepository() {
        return repository;
    }

    public void setRepository(ProductRepository repository) {
        this.repository = repository;
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
            System.out.println(product.getName() + " (" + product.getType() + "), Price:" + product.getPrice() + " [" + product.getDetails() + "]");
        }
    }

    /**
     * Saves the current list of products to the repository.
     */
    public void save() {
        if (repository == null) {
            System.err.println("No repository set for saving.");
            return;
        }
        try {
            repository.save(products);
            System.out.println("Data successfully saved.");
            this.hasChanges = false;
        } catch (Exception e) {
            System.err.println("Error saving: " + e.getMessage());
        }
    }

    /**
     * Loads products from the repository.
     */
    public void load() {
        if (repository == null) {
            System.err.println("No repository set for loading.");
            return;
        }
        try {
            this.products.clear();
            this.products.addAll(repository.load());
            this.hasChanges = false;
        } catch (Exception e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of products to a CSV file (legacy support).
     * @param filename The name of the file to save to.
     */
    public void saveToCSV(String filename) {
        ProductRepository backup = this.repository;
        this.repository = new CsvProductRepository(filename);
        save();
        this.repository = backup;
    }

    /**
     * Loads products from a CSV file into the list (legacy support).
     * @param filename The name of the file to load from.
     */
    public void loadFromCSV(String filename) {
        ProductRepository backup = this.repository;
        this.repository = new CsvProductRepository(filename);
        load();
        this.repository = backup;
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
                if (p instanceof AbstractProduct) {
                    ((AbstractProduct) p).setPrice(newPrice);
                    this.hasChanges = true;
                    System.out.println("Price updated.");
                    return;
                }
            }
        }
        System.out.println("Product not found.");
    }

    public double getPrice(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product.getPrice();
            }
        }
        return -1;
    }
}