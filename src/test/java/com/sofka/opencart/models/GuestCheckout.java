package com.sofka.opencart.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static GuestCheckout testData() {
        String uniqueEmail = "test.user." + System.currentTimeMillis() + "@example.com";
        return GuestCheckout.builder()
                .firstName("Test")
                .lastName("User")
                .email(uniqueEmail)
                .telephone("3001234567")
                .address("123 Main Street")
                .city("Medellin")
                .state("Antioquia")
                .postalCode("050001")
                .country("Colombia")
                .build();
    }
}
