package com.mycompany.app;

import java.util.Scanner;

public class ShowCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        productList.listProducts();
        System.out.println("Total Inventory Value: " + productList.getTotalValue());
    }

    @Override
    public String getDescription() {
        return "Show Product List";
    }
}
