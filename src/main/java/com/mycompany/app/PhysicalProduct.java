package com.mycompany.app;

public class PhysicalProduct extends AbstractProduct {
    private final double weight;

    public PhysicalProduct(String name, double price, double weight) {
        super(name, price);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String getType() {
        return "Physical";
    }

    @Override
    public String getDetails() {
        return "Weight: " + weight + " kg";
    }
}
