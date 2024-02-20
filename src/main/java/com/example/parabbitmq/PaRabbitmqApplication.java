package com.example.parabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.parabbitmq.prodaja.repositories", "com.example.parabbitmq.roba.repositories"})

public class PaRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaRabbitmqApplication.class, args);
	}

}
