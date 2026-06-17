package com.mycompany.app;

import java.util.Scanner;

public class UpdatePriceCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        System.out.print("Enter product name: ");
        String modName = scanner.nextLine();
        System.out.print("Enter new price: ");
        try {
            double newPrice = Double.parseDouble(scanner.nextLine());
            productList.updateProductPrice(modName, newPrice);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
        }
    }

    @Override
    public String getDescription() {
        return "Modify Product Price";
    }
}
