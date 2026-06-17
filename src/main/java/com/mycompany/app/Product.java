package com.mycompany.app;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PhysicalProduct.class, name = "Physical"),
    @JsonSubTypes.Type(value = DigitalProduct.class, name = "Digital")
})
public interface Product {
    String getName();
    double getPrice();
    String getType();
    String getDetails();
}
