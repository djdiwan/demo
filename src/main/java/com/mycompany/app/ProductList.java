package com.mycompany.app;

import java.util.ArrayList;

/**
 * The ProductList class manages a collection of products.
 * It provides methods to add products and list their details.
 */
public class ProductList {
    private ArrayList<Product> products;

    /**
     * Constructs a new ProductList with an empty list of products.
     */
    public ProductList() {
        this.products = new ArrayList<>();
    }

    /**
     * Adds a product to the list.
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        this.products.add(product);
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
    public double getPrice(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product.getPrice();
            }
        }
        return -1;
    
    }
}