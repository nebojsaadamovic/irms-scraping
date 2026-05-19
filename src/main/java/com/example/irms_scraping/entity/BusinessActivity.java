package com.example.irms_scraping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityCode;

    private String mainActivity;
}