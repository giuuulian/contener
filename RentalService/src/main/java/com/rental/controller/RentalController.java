package com.rental.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/customer")
public class RentalController {
	@Value("${customer.service.url}")
	private String customerServiceUrl;

	private final RestTemplate rest = new RestTemplate();

	// health check to verify controller is registered
	@GetMapping("/")
	public ResponseEntity<String> health() {
		return ResponseEntity.ok("customer controller OK");
	}

	@GetMapping("/{name}")
	public ResponseEntity<String> bonjour(@PathVariable String name) {
		String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8);
		String url = customerServiceUrl + encoded;
		// forward response from PHP service as-is
		ResponseEntity<String> resp = rest.getForEntity(url, String.class);
		return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
	}
}