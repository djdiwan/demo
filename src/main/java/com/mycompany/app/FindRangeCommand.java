package com.mycompany.app;

import java.util.Scanner;

public class FindRangeCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        try {
            System.out.print("Enter min price: ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter max price: ");
            double max = Double.parseDouble(scanner.nextLine());
            System.out.println("Results:");
            productList.findProductsInRange(min, max).forEach(p -> 
                System.out.println(p.getName() + " (" + p.getType() + ") - " + p.getPrice() + " [" + p.getDetails() + "]"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }

    @Override
    public String getDescription() {
        return "Find Products in a Price Range";
    }
}
