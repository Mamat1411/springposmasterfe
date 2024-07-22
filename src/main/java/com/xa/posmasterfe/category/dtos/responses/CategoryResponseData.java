package com.xa.posmasterfe.category.dtos.responses;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryResponseData {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Boolean isDeleted;
}
