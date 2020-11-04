package com.puvn.ordersparser.service;

import org.springframework.stereotype.Service;

/**
 * Основноый бизнес-сервис приложения, который запускается из {@link com.puvn.ordersparser.OrdersParserApplication}.
 */
@Service
public interface BusinessService {

	/**
	 * Метод подбирает сервисы парсинга для каждого файла - аргумента приложения.
	 *
	 * @param args массив из строк - аргументов приложения
	 */
	void processFiles(String... args);

}
