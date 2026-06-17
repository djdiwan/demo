package com.mycompany.app;

import java.util.List;

public interface ProductRepository {
    List<Product> load() throws Exception;
    void save(List<Product> products) throws Exception;
}
