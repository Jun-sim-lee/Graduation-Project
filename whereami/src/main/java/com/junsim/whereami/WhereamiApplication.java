package com.junsim.whereami;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WhereamiApplication {
	public static void main(String[] args) {
		SpringApplication.run(WhereamiApplication.class, args);

	}

}
