package com.mycompany.app;

import java.util.Scanner;

public class FindPriceCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        System.out.print("Enter product name: ");
        String searchName = scanner.nextLine();
        double price = productList.getPrice(searchName);
        if (price != -1) {
            System.out.println("Price: " + price);
        } else {
            System.out.println("Product not found.");
        }
    }

    @Override
    public String getDescription() {
        return "Find A Product's Price";
    }
}
