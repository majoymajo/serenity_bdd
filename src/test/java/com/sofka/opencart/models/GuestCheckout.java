package com.sofka.opencart.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de datos para Guest Checkout
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestCheckout {
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    /**
     * Datos de prueba para checkout invitado
     */
    public static GuestCheckout testData() {
        return GuestCheckout.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan.perez@example.com")
                .telephone("+34 612345678")
                .address("Calle Principal 123, Apartamento 4B")
                .city("Madrid")
                .state("Madrid")
                .postalCode("28001")
                .country("España")
                .build();
    }
}
