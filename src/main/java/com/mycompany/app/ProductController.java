package com.mycompany.app;

import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<Product> findInRange(@RequestParam double min, @RequestParam double max) {
        return productList.findProductsInRange(min, max);
    }

    @DeleteMapping("/{name}")
    public String deleteProduct(@PathVariable String name) {
        productList.deleteProduct(name);
        productList.save();
        return "Product deleted: " + name;
    }

    @PatchMapping("/{name}/price")
    public String updatePrice(@PathVariable String name, @RequestBody Double newPrice) {
        productList.updateProductPrice(name, newPrice);
        productList.save();
        return "Price updated for " + name;
    }
}