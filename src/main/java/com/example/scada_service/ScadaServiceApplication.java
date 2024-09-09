package com.example.scada_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScadaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScadaServiceApplication.class, args);
	}

}
