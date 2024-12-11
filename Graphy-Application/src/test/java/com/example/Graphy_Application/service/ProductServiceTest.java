package com.example.Graphy_Application.service;

import com.example.Graphy_Application.entity.Product;
import com.example.Graphy_Application.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    Product product1;
    Product product2;

    @BeforeEach
    void me(){
        product1 = new Product();
        product1.setId(1);
        product1.setName("Product1");
        product1.setCategory("Category1");
        product1.setPrice(100);
        product1.setStock(50);

        product2 = new Product();
        product2.setId(2);
        product2.setName("Product2");
        product2.setCategory("Category2");
        product2.setPrice(150);
        product2.setStock(30);

    }

    @Test
    void testGetAllProducts() {
        Mockito.when(productRepository.findByIsDeletedFalse()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);

    }

    @Test
    void testGetProductById_ProductExists() {

        Product product = new Product();
        product.setId(1);
        product.setName("Product1");
        product.setCategory("Category1");
        product.setPrice(100);
        product.setStock(50);

        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1);

        assertNotNull(result, "Product should not be null");

    }

    @Test
    void testGetProductById_ProductDoesNotExist() {

        Mockito.when(productRepository.findById(99)).thenReturn(Optional.empty());

        Product result = productService.getProductById(99);

        assertNull(result, "Product should be null when not found");

    }

    @Test
    void testGetProductsByCategoryAndPriceRange_FoundProducts() {

        Mockito.when(productRepository.findByCategoryAndPriceBetween("Electronics", 50, 150))
                .thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getProductsByCategoryAndPriceRange("Electronics", 50, 150);

        assertNotNull(products, "Product list should not be null");
        assertEquals(2, products.size(), "There should be 2 products in the list");
        assertTrue(products.contains(product1), "Product list should contain Product1");
        assertTrue(products.contains(product2), "Product list should contain Product2");

    }


}
