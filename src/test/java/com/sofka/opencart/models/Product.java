package com.sofka.opencart.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de datos para un Producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private String price;
    private String quantity;
    private String description;

    /**
     * Ejemplo de productos disponibles en la tienda
     */
    public static Product LAPTOP() {
        return Product.builder()
                .name("MacBook")
                .price("122.00")
                .quantity("1")
                .description("Laptop de alta gama")
                .build();
    }

    public static Product IPHONE() {
        return Product.builder()
                .name("iPhone")
                .price("101.00")
                .quantity("1")
                .description("Smartphone de última generación")
                .build();
    }

    public static Product ACA_PALM() {
        return Product.builder()
                .name("Aca palm")
                .price("35.00")
                .quantity("1")
                .description("Planta ornamental")
                .build();
    }
}
