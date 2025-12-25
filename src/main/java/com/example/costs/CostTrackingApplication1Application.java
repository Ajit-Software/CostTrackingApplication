package com.example.costs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//@ImportResource("classpath:jdbc-config.xml")
@SpringBootApplication
public class CostTrackingApplication1Application {

	public static void main(String[] args) {
		SpringApplication.run(CostTrackingApplication1Application.class, args);
	}

}
