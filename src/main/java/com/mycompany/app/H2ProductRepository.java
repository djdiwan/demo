package com.mycompany.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2ProductRepository implements ProductRepository {
    private final String dbUrl;
    private final String user;
    private final String password;

    public H2ProductRepository(String dbUrl) {
        this.dbUrl = dbUrl;
        this.user = "sa";
        this.password = "";
        try {
            Class.forName("org.h2.Driver");
            initTable();
        } catch (Exception e) {
            System.err.println("Failed to initialize H2 Driver or Table: " + e.getMessage());
        }
    }

    private void initTable() throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS products (" +
                    "name VARCHAR(255) PRIMARY KEY, " +
                    "type VARCHAR(50), " +
                    "price DOUBLE, " +
                    "weight DOUBLE, " +
                    "download_url VARCHAR(255)" +
                    ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, password);
    }

    @Override
    public List<Product> load() throws Exception {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT name, type, price, weight, download_url FROM products";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                if ("Physical".equalsIgnoreCase(type)) {
                    double weight = rs.getDouble("weight");
                    products.add(new PhysicalProduct(name, price, weight));
                } else if ("Digital".equalsIgnoreCase(type)) {
                    String downloadUrl = rs.getString("download_url");
                    products.add(new DigitalProduct(name, price, downloadUrl));
                }
            }
        }
        return products;
    }

    @Override
    public void save(List<Product> products) throws Exception {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Clear existing records
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("DELETE FROM products");
                }
                // Insert current records
                String sql = "INSERT INTO products (name, type, price, weight, download_url) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (Product p : products) {
                        pstmt.setString(1, p.getName());
                        pstmt.setString(2, p.getType());
                        pstmt.setDouble(3, p.getPrice());
                        if (p instanceof PhysicalProduct) {
                            pstmt.setDouble(4, ((PhysicalProduct) p).getWeight());
                            pstmt.setNull(5, Types.VARCHAR);
                        } else if (p instanceof DigitalProduct) {
                            pstmt.setNull(4, Types.DOUBLE);
                            pstmt.setString(5, ((DigitalProduct) p).getDownloadUrl());
                        } else {
                            pstmt.setDouble(4, 0.0);
                            pstmt.setNull(5, Types.VARCHAR);
                        }
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
