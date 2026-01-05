package com.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rental"})
public class RentalServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RentalServiceApplication.class, args);
	}
}