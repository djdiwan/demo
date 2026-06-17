package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.File;
import java.util.List;

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
        productList.addProduct(new PhysicalProduct("Mi11X", 28000.0, 0.2));
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

    /**
     * Tests filtering products by price range.
     */
    @Test
    public void testFindProductsInRange() {
        ProductList productList = new ProductList();
        productList.addProduct(new PhysicalProduct("A", 100, 1.0));
        productList.addProduct(new DigitalProduct("B", 200, "http://download"));
        productList.addProduct(new PhysicalProduct("C", 300, 2.0));
        List<Product> filtered = productList.findProductsInRange(150, 250);
        assertEquals(1, filtered.size());
        assertEquals("B", filtered.get(0).getName());
        assertEquals("Digital", filtered.get(0).getType());
    }

    @Test
    public void testGetTotalValue() {
        ProductList productList = new ProductList();
        productList.addProduct(new PhysicalProduct("A", 10.5, 1.0));
        productList.addProduct(new DigitalProduct("B", 20.5, "url"));
        assertEquals(31.0, productList.getTotalValue(), 0.001);
    }

    /**
     * Tests that a product can be successfully deleted from the list.
     */
    @Test
    public void testDeleteProduct() {
        ProductList productList = new ProductList();
        productList.addProduct(new PhysicalProduct("Mi11X", 28000, 0.15));
        productList.deleteProduct("Mi11X");
        assertEquals(-1.0, productList.getPrice("Mi11X"), 0.001, "Product should not be found after deletion.");
    }

    /**
     * Tests that a product's price can be modified.
     */
    @Test
    public void testUpdateProductPrice() {
        ProductList productList = new ProductList();
        productList.addProduct(new PhysicalProduct("Motorola G30", 30000, 0.3));
        productList.updateProductPrice("Motorola G30", 25000);
        assertEquals(25000.0, productList.getPrice("Motorola G30"), 0.001, "Price should reflect the updated value.");
    }

    /**
     * Tests the state tracking of the hasChanges flag.
     */
    @Test
    public void testHasChangesFlag() {
        ProductList productList = new ProductList();
        assertFalse(productList.hasChanges(), "Initially, hasChanges should be false.");
        
        productList.addProduct(new PhysicalProduct("Sample", 100, 1.0));
        assertTrue(productList.hasChanges(), "hasChanges should be true after adding a product.");
        
        productList.updateProductPrice("Sample", 150);
        assertTrue(productList.hasChanges(), "hasChanges should remain true after price update.");
    }
}