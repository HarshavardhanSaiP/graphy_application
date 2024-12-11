package com.example.Graphy_Application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String category;
    private Integer price;
    private Integer stock;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
