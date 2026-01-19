package com.farm2home.dto;

import jakarta.validation.constraints.NotBlank;

public class DeliveryDetailsDTO {

    @NotBlank
    private String customerName;

    @NotBlank
    private String phone;

    @NotBlank
    private String addressLine;

    private String area;

    @NotBlank
    private String city;

    @NotBlank
    private String pincode;

    // getters & setters

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
