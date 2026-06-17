package com.mycompany.app;

import java.util.Scanner;

public class AddCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        System.out.println("Select Product Type:");
        System.out.println("1. Physical Product");
        System.out.println("2. Digital Product");
        System.out.print("Choice: ");
        String typeChoice = scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        try {
            double price = Double.parseDouble(scanner.nextLine());
            if (typeChoice.equals("2")) {
                System.out.print("Enter download URL: ");
                String downloadUrl = scanner.nextLine();
                productList.addProduct(new DigitalProduct(name, price, downloadUrl));
                System.out.println("Digital Product added.");
            } else {
                System.out.print("Enter product weight (kg): ");
                double weight = Double.parseDouble(scanner.nextLine());
                productList.addProduct(new PhysicalProduct(name, price, weight));
                System.out.println("Physical Product added.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric format.");
        }
    }

    @Override
    public String getDescription() {
        return "Add a Product";
    }
}
