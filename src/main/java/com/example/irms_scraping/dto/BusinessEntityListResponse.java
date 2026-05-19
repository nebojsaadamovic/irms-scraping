package com.example.irms_scraping.dto;

import lombok.Data;

import java.util.List;

@Data
public class BusinessEntityListResponse {

    private int total;
    private int page;
    private int totalPage;
    private int perPage;
    private boolean hasNext;
    private boolean hasPrevious;

    private List<BusinessEntityListDTO> results;
}