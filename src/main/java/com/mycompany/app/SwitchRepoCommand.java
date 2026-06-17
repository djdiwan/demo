package com.mycompany.app;

import java.util.Scanner;

public class SwitchRepoCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        System.out.println("Select New Persistence Mechanism:");
        System.out.println("1. CSV File (products.csv)");
        System.out.println("2. JSON File (products.json)");
        System.out.println("3. H2 Database (jdbc:h2:./productsdb)");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();
        
        ProductRepository newRepo;
        switch (choice) {
            case "2":
                newRepo = new JsonProductRepository("products.json");
                productList.setRepository(newRepo);
                System.out.println("Switched to JSON File repository.");
                break;
            case "3":
                newRepo = new H2ProductRepository("jdbc:h2:./productsdb");
                productList.setRepository(newRepo);
                System.out.println("Switched to H2 Database repository.");
                break;
            case "1":
            default:
                newRepo = new CsvProductRepository("products.csv");
                productList.setRepository(newRepo);
                System.out.println("Switched to CSV File repository.");
                break;
        }
        
        System.out.print("Do you want to load data from the new repository now? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            productList.load();
            System.out.println("Data loaded.");
        }
    }

    @Override
    public String getDescription() {
        return "Switch Persistence Mechanism (Polymorphic Storage)";
    }
}
