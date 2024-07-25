package com.xa.posmasterfe.product.dtos.responses;

import com.xa.posmasterfe.category.dtos.responses.CategoryResponse;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private CategoryResponse category;
    private Long categoryId;
    private Double price;
    private Integer stock;
}
