package com.farm2home.dto;

import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {

    @NotNull
    private Double totalAmount;

    private Double latitude;
    private Double longitude;

    // getters & setters

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
