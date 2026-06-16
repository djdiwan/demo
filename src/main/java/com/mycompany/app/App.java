package com.mycompany.app;

import java.util.ArrayList;

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
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Mi11X", 28000));
        products.add(new Product("Redme Note10 Pro", 22000));
        products.add(new Product("Motorola G30", 30000));
        System.out.println("Product List:");
        for (Product product : products) {
            System.out.println(product.getName() + ", Price:" + product.getPrice());
        }
    }
}
