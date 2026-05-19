package com.example.irms_scraping.dto;

import lombok.Data;

@Data
public class BusinessEntityDetailsDTO {

    private Long taxpayerId;

    private String fullName;

    private String shortName;

    private String identificationNumber;

    private String email;

    private String registrationDate;

    private String mainActivity;
}