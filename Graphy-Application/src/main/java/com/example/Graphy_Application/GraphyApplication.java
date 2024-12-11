package com.example.Graphy_Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GraphyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphyApplication.class, args);
	}

}
