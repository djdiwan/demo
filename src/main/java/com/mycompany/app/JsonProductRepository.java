package com.mycompany.app;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonProductRepository implements ProductRepository {
    private final String filepath;
    private final Gson gson;

    public JsonProductRepository(String filepath) {
        this.filepath = filepath;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .setPrettyPrinting()
                .create();
    }

    private static class ProductAdapter implements JsonSerializer<Product>, JsonDeserializer<Product> {
        @Override
        public JsonElement serialize(Product src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", src.getType());
            jsonObject.addProperty("name", src.getName());
            jsonObject.addProperty("price", src.getPrice());
            if (src instanceof PhysicalProduct) {
                jsonObject.addProperty("weight", ((PhysicalProduct) src).getWeight());
            } else if (src instanceof DigitalProduct) {
                jsonObject.addProperty("downloadUrl", ((DigitalProduct) src).getDownloadUrl());
            }
            return jsonObject;
        }

        @Override
        public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.has("type") ? jsonObject.get("type").getAsString() : "Physical";
            String name = jsonObject.get("name").getAsString();
            double price = jsonObject.get("price").getAsDouble();
            if ("Physical".equalsIgnoreCase(type)) {
                double weight = jsonObject.has("weight") ? jsonObject.get("weight").getAsDouble() : 0.0;
                return new PhysicalProduct(name, price, weight);
            } else if ("Digital".equalsIgnoreCase(type)) {
                String downloadUrl = jsonObject.has("downloadUrl") ? jsonObject.get("downloadUrl").getAsString() : "";
                return new DigitalProduct(name, price, downloadUrl);
            }
            throw new JsonParseException("Unknown product type: " + type);
        }
    }

    @Override
    public List<Product> load() throws Exception {
        List<Product> products = new ArrayList<>();
        File file = new File(filepath);
        if (!file.exists()) {
            return products;
        }

        try (Reader reader = new FileReader(file)) {
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            if (array != null) {
                for (JsonElement element : array) {
                    products.add(gson.fromJson(element, Product.class));
                }
            }
        }
        return products;
    }

    @Override
    public void save(List<Product> products) throws Exception {
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(products, writer);
        }
    }
}
