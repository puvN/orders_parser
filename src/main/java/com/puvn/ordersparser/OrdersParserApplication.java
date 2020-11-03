package com.puvn.ordersparser;

import com.puvn.ordersparser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrdersParserApplication implements CommandLineRunner {

	private final BusinessService businessService;

	@Autowired
	public OrdersParserApplication(BusinessService businessService) {
		this.businessService = businessService;
	}

	public static void main(String[] args) {
		SpringApplication.run(OrdersParserApplication.class, args);
	}

	@Override
	public void run(String... args) {
		businessService.processFiles(args);
	}

}
