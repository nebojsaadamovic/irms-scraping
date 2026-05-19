package com.example.irms_scraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IrmsScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrmsScraperApplication.class, args);
	}

}
