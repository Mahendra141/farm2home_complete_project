package com.farm2home.repository;

import com.farm2home.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIgnoreCaseAndAvailableTrue(String category);

    List<Product> findByNameContainingIgnoreCaseAndAvailableTrue(String name);

    // ✅ FIXED: explicit query returning ONLY names
    @Query("""
        SELECT p.name 
        FROM Product p 
        WHERE p.available = true 
          AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY p.name
        """)
    List<String> findTop5ProductNames(String keyword);
}
