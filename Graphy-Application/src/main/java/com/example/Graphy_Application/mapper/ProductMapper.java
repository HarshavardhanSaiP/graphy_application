package com.example.Graphy_Application.mapper;

import com.example.Graphy_Application.dto.ProductResponse;
import com.example.Graphy_Application.entity.Product;

public class ProductMapper {

    public static ProductResponse toResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setCategory(product.getCategory());
        productResponse.setName(product.getName());
        productResponse.setStock(product.getStock());
        productResponse.setPrice(product.getPrice());
        return  productResponse;
    }

    public static Product toProduct(ProductResponse productResponse){
        Product product = new Product();
        product.setCategory(product.getCategory());
        product.setName(product.getName());
        product.setStock(product.getStock());
        product.setPrice(product.getPrice());

        return product;
    }
}
