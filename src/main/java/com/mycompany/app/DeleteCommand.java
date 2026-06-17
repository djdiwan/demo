package com.mycompany.app;

import java.util.Scanner;

public class DeleteCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        System.out.print("Enter product name to delete: ");
        productList.deleteProduct(scanner.nextLine());
    }

    @Override
    public String getDescription() {
        return "Delete A Product";
    }
}
