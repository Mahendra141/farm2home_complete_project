package com.farm2home.entity;

import com.farm2home.enums.DemandLevel;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔥 ORIGINAL PRICE (ADMIN ENTERS THIS)
    @Column(nullable = false)
    private Double basePrice;

    // 🔥 FINAL PRICE (CALCULATED – NOT STORED)
    @Transient
    private Double finalPrice;

    private String imageUrl;
    private String category;
    private String unit;

    private Boolean available = true;

    private Integer discountPercent = 0;
    private Boolean discountActive = false;
    private Integer stock;

    @Enumerated(EnumType.STRING)
    private DemandLevel demandLevel = DemandLevel.NORMAL;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

    public Double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }

    public Boolean getDiscountActive() { return discountActive; }
    public void setDiscountActive(Boolean discountActive) { this.discountActive = discountActive; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public DemandLevel getDemandLevel() { return demandLevel; }
    public void setDemandLevel(DemandLevel demandLevel) { this.demandLevel = demandLevel; }
}
