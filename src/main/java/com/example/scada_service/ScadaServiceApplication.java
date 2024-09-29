package com.example.scada_service;

import com.example.scada_service.services.OpcUaSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScadaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScadaServiceApplication.class, args);
	}

}
