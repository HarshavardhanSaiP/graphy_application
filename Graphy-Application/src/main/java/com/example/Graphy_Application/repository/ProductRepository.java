package com.example.Graphy_Application.repository;

import com.example.Graphy_Application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategoryAndPriceBetween(String category, Integer priceMin, Integer priceMax);

    List<Product> findByIsDeletedFalse();
}
