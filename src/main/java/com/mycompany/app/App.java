package com.mycompany.app;

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
        ProductList productList = new ProductList();
        productList.addProduct(new Product("Mi11X", 28000));
        productList.addProduct(new Product("Redme Note10 Pro", 22000));
        productList.addProduct(new Product("Motorola G30", 30000));
        productList.listProducts();
    }
}
