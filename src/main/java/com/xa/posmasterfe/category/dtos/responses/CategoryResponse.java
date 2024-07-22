package com.xa.posmasterfe.category.dtos.responses;

import java.util.List;

import lombok.Data;

@Data
public class CategoryResponse {
    private String status;
    private String message;
    private List<CategoryResponseData> data;
}
