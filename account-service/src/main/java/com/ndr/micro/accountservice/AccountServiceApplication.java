package com.ndr.micro.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "account-service");
		SpringApplication.run(AccountServiceApplication.class, args);
	}
}
