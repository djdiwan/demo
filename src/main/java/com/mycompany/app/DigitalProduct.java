package com.mycompany.app;

public class DigitalProduct extends AbstractProduct {
    private final String downloadUrl;

    public DigitalProduct(String name, double price, String downloadUrl) {
        super(name, price);
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Override
    public String getType() {
        return "Digital";
    }

    @Override
    public String getDetails() {
        return "Download URL: " + downloadUrl;
    }
}
