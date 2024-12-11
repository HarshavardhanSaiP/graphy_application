package com.example.Graphy_Application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotNull
    private String name;
    @NotNull
    private String category;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
}
