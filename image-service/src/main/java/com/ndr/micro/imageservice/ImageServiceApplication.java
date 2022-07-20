package com.ndr.micro.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageServiceApplication {

	public static void main(String[] args)
	{
		System.setProperty("spring.config.name", "image-service");
		SpringApplication.run(ImageServiceApplication.class, args);
	}


}
