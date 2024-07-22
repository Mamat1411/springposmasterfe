package com.xa.posmasterfe.category.dtos.requests;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String description;
    private String createdBy;
    private String updatedBy;
    private String isDeleted;
}
