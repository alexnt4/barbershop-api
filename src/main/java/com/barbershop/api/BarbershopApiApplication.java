package com.barbershop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BarbershopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbershopApiApplication.class, args);
	}

}
