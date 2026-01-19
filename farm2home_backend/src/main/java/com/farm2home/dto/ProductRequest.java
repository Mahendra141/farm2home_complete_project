package com.farm2home.dto;

import com.farm2home.enums.DemandLevel;

public class ProductRequest {

    private String name;
    private Double price;        // base price
    private String category;
    private String unit;
    private Boolean available;

    // 🔥 NEW OPTIONAL FIELDS (ADMIN CONTROLLED)
    private Integer discountPercent;
    private Boolean discountActive;
    private Integer stock;
    private DemandLevel demandLevel;

    // ===== GETTERS & SETTERS =====

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Boolean getDiscountActive() {
        return discountActive;
    }

    public void setDiscountActive(Boolean discountActive) {
        this.discountActive = discountActive;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public DemandLevel getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(DemandLevel demandLevel) {
        this.demandLevel = demandLevel;
    }
}
