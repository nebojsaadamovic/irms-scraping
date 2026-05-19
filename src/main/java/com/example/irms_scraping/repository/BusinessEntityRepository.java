package com.example.irms_scraping.repository;

import com.example.irms_scraping.entity.BusinessEntityDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityRepository
        extends JpaRepository<BusinessEntityDB, Long> {
}