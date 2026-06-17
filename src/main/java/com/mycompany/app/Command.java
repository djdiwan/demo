package com.mycompany.app;

import java.util.Scanner;

public interface Command {
    void execute(ProductList productList, Scanner scanner);
    String getDescription();
}
