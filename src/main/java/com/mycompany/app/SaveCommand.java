package com.mycompany.app;

import java.util.Scanner;

public class SaveCommand implements Command {
    @Override
    public void execute(ProductList productList, Scanner scanner) {
        if (productList.hasChanges()) {
            productList.save();
        } else {
            System.out.println("No changes to save.");
        }
    }

    @Override
    public String getDescription() {
        return "Save Product List (only if changed)";
    }
}
