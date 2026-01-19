package com.farm2home.service;

import com.farm2home.enums.DemandLevel;
import com.farm2home.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public Double calculateFinalPrice(Product product) {

        if (Boolean.FALSE.equals(product.getDiscountActive())) {
            return product.getBasePrice();
        }

        if (product.getDemandLevel() == DemandLevel.HIGH) {
            return product.getBasePrice();
        }

        if (product.getStock() != null && product.getStock() < 10) {
            return product.getBasePrice();
        }

        return product.getBasePrice()
                - (product.getBasePrice() * product.getDiscountPercent() / 100);
    }
}
