package com.mycompany.app;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- Welcome to the Product Management System ---");
        System.out.println("Select Initial Persistence Mechanism:");
        System.out.println("1. H2 Database (jdbc:h2:./productsdb)");
        System.out.println("2. CSV File (products.csv)");
        System.out.println("3. JSON File (products.json)");
        System.out.print("Choice [Default: CSV]: ");
        String initialChoice = scanner.nextLine();
        
        ProductRepository repository;
        if (initialChoice.equals("1")) {
            repository = new H2ProductRepository("jdbc:h2:./productsdb");
            System.out.println("Initialized with H2 Database.");
        } else if (initialChoice.equals("3")) {
            repository = new JsonProductRepository("products.json");
            System.out.println("Initialized with JSON File.");
        } else {
            repository = new CsvProductRepository("products.csv");
            System.out.println("Initialized with CSV File.");
        }

        ProductList productList = new ProductList(repository);
        // Automatically load initial data
        productList.load();
        System.out.println("Initial data loaded.");

        // Define dynamic Command Registry
        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("1", new LoadCommand());
        commands.put("2", new SaveCommand());
        commands.put("3", new ShowCommand());
        commands.put("4", new AddCommand());
        commands.put("5", new FindRangeCommand());
        commands.put("6", new FindPriceCommand());
        commands.put("7", new DeleteCommand());
        commands.put("8", new UpdatePriceCommand());
        commands.put("9", new SwitchRepoCommand());

        boolean running = true;
        while (running) {
            System.out.println("\n--- Product Management Menu ---");
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getDescription());
            }
            System.out.println("10. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("10")) {
                if (productList.hasChanges()) {
                    System.out.print("Unsaved changes detected. Save now? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        productList.save();
                    }
                }
                running = false;
            } else if (commands.containsKey(choice)) {
                try {
                    commands.get(choice).execute(productList, scanner);
                } catch (Exception e) {
                    System.err.println("Error executing command: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
        System.out.println("Goodbye!");
    }
}
