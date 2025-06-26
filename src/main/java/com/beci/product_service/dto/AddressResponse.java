package com.beci.product_service.dto;

public class AddressResponse {

    private String id;

    private String street;
    private String city;
    private String postalCode;
    private String country;

    // Getteri și setteri
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipCode() { return postalCode; }
    public void setPostalCode(String zipCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
