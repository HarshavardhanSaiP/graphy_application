package com.example.Graphy_Application.service;

import com.example.Graphy_Application.entity.Product;
import com.example.Graphy_Application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        logger.info("Service:  getAllProducts");
        return productRepository.findByIsDeletedFalse();
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProductById(Integer id) {
        logger.info("Service: getProductById");
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Cacheable(value = "products", key = "'filter-' + #category + '-' + #priceMin + '-' + #priceMax")
    public List<Product> getProductsByCategoryAndPriceRange(String category, Integer priceMin, Integer priceMax) {
        logger.info("Service: getProductsByCategoryAndPriceRange");
        return productRepository.findByCategoryAndPriceBetween(category, priceMin, priceMax);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product addProduct(Product product) {
        logger.info("Service: added new entry.");
        return productRepository.save(product);
    }


    @CachePut(value = "product", key="#id")
    public Product updateProduct(Integer id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    @Caching(evict = {@CacheEvict(value = "product", key="#id"),@CacheEvict(value = "products", allEntries = true)})
    public boolean deleteProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product.get().setIsDeleted(true);
            productRepository.save(product.get());
            return true;
        }
        return false;
    }
}
