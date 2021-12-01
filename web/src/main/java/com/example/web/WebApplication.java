package com.example.web;

import java.time.LocalDate;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);

        System.out.println("WebApplication: " + LocalDate.now()
            .toString());
	}

}
