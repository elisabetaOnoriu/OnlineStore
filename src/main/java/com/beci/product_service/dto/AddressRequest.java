package com.beci.product_service.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    private String postalCode;

    @NotBlank
    private String country;

    // Getteri și setteri
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipCode() { return postalCode; }
    public void setZipCode(String zipCode) { this.postalCode = zipCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
