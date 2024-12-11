package com.example.Graphy_Application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductResponse implements Serializable {
    private Integer id;
    private String name;
    private String category;
    private Integer price;
    private Integer stock;

}
