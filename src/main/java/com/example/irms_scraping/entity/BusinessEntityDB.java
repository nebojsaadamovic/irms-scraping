package com.example.irms_scraping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "business_entities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityDB {

    @Id
    private Long id;

    private String name;

    private String shortName;

    private String pib;

    private String email;

    private String registrationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private BusinessActivity activity;


//    private Boolean emailSent = false;
//
//    private LocalDateTime emailSentAt;


    @Override
    public String toString() {
        return "BusinessEntityDB{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", pib='" + pib + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", activity='" + activity.getMainActivity() + '\'' +
                ", activityCode='" + activity.getActivityCode() + '\'' +
                '}';
    }
}