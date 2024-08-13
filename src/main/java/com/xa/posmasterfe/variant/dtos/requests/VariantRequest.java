package com.xa.posmasterfe.variant.dtos.requests;

import lombok.Data;

@Data
public class VariantRequest {
    private String name;
    private String description;
    private Long productId;
    private Double price;
    private Long stock;
    private String createdBy;
    private String updatedBy;
    private String isDeleted;
}
