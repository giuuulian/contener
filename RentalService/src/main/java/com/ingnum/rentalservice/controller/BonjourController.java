package com.ingnum.rentalservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonjourController {

    @GetMapping("/bonjour")
    public String bonjour() {
        return "bonjour";
    }

    @GetMapping("/bonjour-php")
    public String bonjourPhp() {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Requesting PHP service at: " + phpServiceUrl);
        String response = restTemplate.getForObject(phpServiceUrl, String.class);
        return response;
    }

    @GetMapping("/customer/{name}")
    public String getCustomerAddress(@PathVariable String name) {
        return "l'adresse de " + name + " est 13 rue dareau";
    }
}
