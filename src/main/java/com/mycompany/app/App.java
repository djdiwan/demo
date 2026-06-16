package com.mycompany.app;

import java.util.ArrayList;

/**
 * Hello world!
 */
public class App {
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
