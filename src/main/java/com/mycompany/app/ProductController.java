package com.mycompany.app;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductList productList;

    public ProductController(ProductList productList) {
        this.productList = productList;
        this.productList.load(); // Load initial data
    }

    @GetMapping
    public List<Product> getAllProducts() {
        // We would need to add a getProducts() method to ProductList
        return productList.findProductsInRange(0, Double.MAX_VALUE);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        productList.addProduct(product);
        productList.save();
        return product;
    }

    @GetMapping("/search")
    public List<Product> findInRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return productList.findProductsInRange(min, max);
    }

    @DeleteMapping("/{name}")
    public String deleteProduct(@PathVariable("name") String name) {
        productList.deleteProduct(name);
        productList.save();
        return "Product deleted: " + name;
    }

    @PatchMapping("/{name}/price")
    public String updatePrice(@PathVariable("name") String name, @RequestBody Double newPrice) {
        productList.updateProductPrice(name, newPrice);
        productList.save();
        return "Price updated for " + name;
    }

    @GetMapping("/repository")
    public String getCurrentRepository() {
        ProductRepository repo = productList.getRepository();
        if (repo instanceof H2ProductRepository) {
            return "H2";
        } else if (repo instanceof JsonProductRepository) {
            return "JSON";
        } else {
            return "CSV";
        }
    }

    @PostMapping("/repository")
    public String switchRepository(@RequestParam("type") String type) {
        ProductRepository newRepo;
        if (type.equalsIgnoreCase("JSON")) {
            newRepo = new JsonProductRepository("products.json");
            productList.setRepository(newRepo);
            productList.load();
            return "Switched to JSON File";
        } else if (type.equalsIgnoreCase("H2")) {
            newRepo = new H2ProductRepository("jdbc:h2:./productsdb");
            productList.setRepository(newRepo);
            productList.load();
            return "Switched to H2 Database";
        } else {
            newRepo = new CsvProductRepository("products.csv");
            productList.setRepository(newRepo);
            productList.load();
            return "Switched to CSV File";
        }
    }
}