package com.ndr.micro.replicantsregistryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReplicantsRegistryServiceApplication {

	public static void main(String[] args)
	{
		System.setProperty("spring.config.name", "replicants-service-properties");
		SpringApplication.run(ReplicantsRegistryServiceApplication.class, args);
	}

}
