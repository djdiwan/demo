package com.mycompany.app;

import java.util.Scanner;

public class LoadCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        productList.load();
        System.out.println("Product list loaded.");
    }

    @Override
    public String getDescription() {
        return "Load Product List";
    }
}
