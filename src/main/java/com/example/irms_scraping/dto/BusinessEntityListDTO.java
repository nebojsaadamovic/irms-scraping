package com.example.irms_scraping.dto;

import lombok.Data;

@Data
public class BusinessEntityListDTO {

    private Long taxpayerId;

    private String registrationNumber;

    private Integer version;

    private String identificationNumber;

    private String fullName;

    private String legalStatus;

    private String mainActivity;
}