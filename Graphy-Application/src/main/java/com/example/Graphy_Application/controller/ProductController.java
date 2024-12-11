package com.example.Graphy_Application.controller;

import com.example.Graphy_Application.dto.ProductRequest;
import com.example.Graphy_Application.dto.ProductResponse;
import com.example.Graphy_Application.entity.Product;
import com.example.Graphy_Application.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    /**
     * with cache - response time is avg of 10ms
     * without - is varying operation based on http methods,
     */

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductsList() {
        logger.info("Get all products request");
        long startTime  = System.currentTimeMillis();

        try {
            List<Product> products = productService.getAllProducts();
            timeTaken(startTime);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error fetching products list: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@NotNull @PathVariable Integer id) {
        logger.info("Get product by id request");

        long startTime  = System.currentTimeMillis();

        try {
            Product product = productService.getProductById(id);
            timeTaken(startTime);
            return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error fetching product with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filterProducts(
            @RequestParam(required = true) String category,
            @RequestParam(required = true) Integer priceMin,
            @RequestParam(required = true) Integer priceMax) {

        /**
         *  without - 232ms
         *  with cache - 10ms
         */
        logger.info("In Filter request.");

        long startTime  = System.currentTimeMillis();

        try {
            List<ProductResponse> products = productService.getProductsByCategoryAndPriceRange(category, priceMin, priceMax);
            timeTaken(startTime);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error filtering products by category and price range: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest product) {
        try {

            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@NotNull @PathVariable Integer id,@Valid @RequestBody ProductRequest product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating product with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@NotNull @PathVariable Integer id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);
            return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting product with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    void timeTaken(long startTime){
        long endTime = System.currentTimeMillis();
        logger.info("TimeTaken : {} ms", endTime - startTime);
    }
}

