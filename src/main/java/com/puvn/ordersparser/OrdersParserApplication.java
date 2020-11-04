package com.puvn.ordersparser;

import com.puvn.ordersparser.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения.
 */
@SpringBootApplication
public class OrdersParserApplication implements CommandLineRunner {

	private final BusinessService businessService;

	/**
	 * @param businessService Бизнес-сервис приложения
	 */
	@Autowired
	public OrdersParserApplication(BusinessService businessService) {
		this.businessService = businessService;
	}

	/**
	 * @param args Аргументы консольного приложения.
	 */
	public static void main(String[] args) {
		SpringApplication.run(OrdersParserApplication.class, args);
	}

	/**
	 * @param args Аргументы консольного приложения.
	 */
	@Override
	public void run(String... args) {
		businessService.processFiles(args);
	}

}
