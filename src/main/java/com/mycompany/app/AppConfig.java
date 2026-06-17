package com.mycompany.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProductRepository productRepository() {
        // Defaulting to CSV for now; you can change this to H2 or JSON
        return new CsvProductRepository("products.csv");
    }
}