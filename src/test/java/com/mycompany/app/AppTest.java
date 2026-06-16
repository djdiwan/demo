package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Tests that a product added to the list can be retrieved by its name to get its price.
     */
    @Test
    public void testAddProductAndGetPrice() {
        ProductList productList = new ProductList();
        productList.addProduct(new Product("Mi11X", 28000.0));
        assertEquals(28000.0, productList.getPrice("Mi11X"), 0.001, "The price should match the added product.");
    }

    /**
     * Tests that searching for a product that doesn't exist returns -1.
     */
    @Test
    public void testGetPriceForNonExistentProduct() {
        ProductList productList = new ProductList();
        assertEquals(-1.0, productList.getPrice("NonExistent"), 0.001, "Should return -1 for missing products.");
    }
}
