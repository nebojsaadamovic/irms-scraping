package com.example.irms_scraping.services;

import com.example.irms_scraping.dto.BusinessEntityDetailsDTO;
import com.example.irms_scraping.dto.BusinessEntityListDTO;
import com.example.irms_scraping.dto.BusinessEntityListResponse;
import com.example.irms_scraping.entity.BusinessActivity;
import com.example.irms_scraping.entity.BusinessEntityDB;
import com.example.irms_scraping.enums.ExcludedActivityCode;
import com.example.irms_scraping.repository.BusinessEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IrmsService {

    private final BusinessEntityRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final JavaMailSender mailSender;

    public void fetchTodayRegisteredCompanies(String today) {

        int page = 1;
        List<BusinessEntityDB> allEntities = new ArrayList<>();

        while (true) {

            String url =
                    "https://irms.tax.gov.me/public/api/business-entities?page="
                            + page
                            + "&perPage=500&taxpayerStatusId=11&registrationDate="
                            + today;

            BusinessEntityListResponse response =
                    restTemplate.getForObject(url, BusinessEntityListResponse.class);

            if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
                break;
            }

            List<BusinessEntityListDTO> companies = response.getResults();

            for (BusinessEntityListDTO company : companies) {

                try {
                    String detailsUrl =
                            "https://irms.tax.gov.me/public/api/business-entity/"
                                    + company.getTaxpayerId();

                    BusinessEntityDetailsDTO details =
                            restTemplate.getForObject(detailsUrl, BusinessEntityDetailsDTO.class);


                    String[] activityParts = details.getMainActivity().split(",");
                    String code = activityParts[0].trim();
                    String activityName = activityParts[1].trim();


                    BusinessActivity activity = BusinessActivity.builder()
                            .activityCode(code)
                            .mainActivity(activityName)
                            .build();

                    if (details == null || ExcludedActivityCode.contains(code)) continue;

                    BusinessEntityDB entity = BusinessEntityDB.builder()
                            .id(details.getTaxpayerId())
                            .name(details.getFullName())
                            .shortName(details.getShortName())
                            .pib(details.getIdentificationNumber())
                            .email(details.getEmail())
                            .registrationDate(details.getRegistrationDate())
                            .activity(activity)
                            .build();

                    repository.save(entity);


                    allEntities.add(entity);

                } catch (Exception e) {
                    System.out.println("Error company id: " + company.getTaxpayerId());
                }
            }

            // pagination stop condition
            if (!response.isHasNext()) {
                for (BusinessEntityDB entity : allEntities) {
                    System.out.println(entity);
                }
                break;
            }

            page++;
        }
    }


    @Scheduled(cron = "0 0 2 * * *", zone = "Europe/Podgorica")
    public void sendDailyEmails() {

        System.out.println("Starting email job...");

        processAndSend();

        System.out.println("Email job finished");
    }


    public void processAndSend() {

        boolean testMode = true;

        List<BusinessEntityDB> filtered;

        if (testMode) {
            filtered = List.of(
                    BusinessEntityDB.builder().name("Test 1").email("adamovic.nebojsa@gmail.com").build(),
                    BusinessEntityDB.builder().name("Test 2").email("nebojsa.adamovic@omnisoft.me").build()
            );
        } else {
            filtered = repository.findAll().stream()
                    .filter(e -> e.getEmail() != null)
                    .filter(e -> !e.getEmail().isBlank())
                    .toList();
        }

        for (BusinessEntityDB lead : filtered) {
            try {
                sendEmail(lead);
            } catch (Exception e) {
                System.out.println("FAILED EMAIL: " + lead.getEmail());
                e.printStackTrace();
            }
        }
    }


    public void sendEmail(BusinessEntityDB lead) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(lead.getEmail());

        message.setSubject("Ponuda za vaš biznis");

        String name = lead.getShortName() != null ? lead.getShortName() : lead.getName();
        message.setText(
                "Poštovani " + name + ",\n\n" +
                        "Imamo ponudu za vaše poslovanje...\n\n" +
                        "Pozdrav!"
        );

        mailSender.send(message);
    }
}