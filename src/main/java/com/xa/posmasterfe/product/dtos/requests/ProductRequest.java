package com.xa.posmasterfe.product.dtos.requests;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private String createdBy;
    private String updatedBy;
    private String isDeleted;
}
