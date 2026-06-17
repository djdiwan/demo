package com.mycompany.app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvProductRepository implements ProductRepository {
    private final String filepath;

    public CsvProductRepository(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Product> load() throws Exception {
        List<Product> products = new ArrayList<>();
        File file = new File(filepath);
        if (!file.exists()) {
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    // Legacy format: Name,Price
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    products.add(new PhysicalProduct(name, price, 0.0));
                } else if (parts.length >= 4) {
                    // Polymorphic format: Type,Name,Price,Extra
                    String type = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String extra = parts[3];
                    if (type.equalsIgnoreCase("Physical")) {
                        double weight = Double.parseDouble(extra);
                        products.add(new PhysicalProduct(name, price, weight));
                    } else if (type.equalsIgnoreCase("Digital")) {
                        products.add(new DigitalProduct(name, price, extra));
                    }
                }
            }
        }
        return products;
    }

    @Override
    public void save(List<Product> products) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            for (Product product : products) {
                if (product instanceof PhysicalProduct) {
                    PhysicalProduct pp = (PhysicalProduct) product;
                    writer.println("Physical," + pp.getName() + "," + pp.getPrice() + "," + pp.getWeight());
                } else if (product instanceof DigitalProduct) {
                    DigitalProduct dp = (DigitalProduct) product;
                    writer.println("Digital," + dp.getName() + "," + dp.getPrice() + "," + dp.getDownloadUrl());
                } else {
                    writer.println("Physical," + product.getName() + "," + product.getPrice() + ",0.0");
                }
            }
        }
    }
}
