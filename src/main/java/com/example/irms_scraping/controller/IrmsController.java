package com.example.irms_scraping.controller;

import com.example.irms_scraping.services.IrmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/irms")
@RequiredArgsConstructor
public class IrmsController {

    private final IrmsService irmsService;

    @GetMapping("/sync-today")
    public String syncToday() {

        String today = LocalDate.now().toString();

        irmsService.fetchTodayRegisteredCompanies(today);

        return "Sync started for date: " + today;
    }


    @GetMapping("/send-mails")
    public String sendDailyEmails() {

        irmsService.sendDailyEmails();

        return "Send emails ";
    }
}