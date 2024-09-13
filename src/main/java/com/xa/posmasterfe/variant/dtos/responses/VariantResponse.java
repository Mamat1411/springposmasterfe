package com.xa.posmasterfe.variant.dtos.responses;

import java.time.LocalDateTime;

import com.xa.posmasterfe.product.dtos.responses.ProductResponse;

import lombok.Data;

@Data
public class VariantResponse {
    private Long id;
    private String name;
    private String description;
    private ProductResponse product;
    private Long productId;
    private Long price;
    private Long stock;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Boolean isDeleted;
}
