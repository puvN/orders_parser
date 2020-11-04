package com.puvn.ordersparser.service.parsing;

import com.puvn.ordersparser.model.OrderDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Сервис для парсинга CSV файлов.
 */
@Service
public class CSVParsingService implements ParsingService {

	/**
	 * Расширение файла, с которым работает данная реализация сервиса.
	 */
	private static final String REGISTERED_EXTENSION = ".csv";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRegisteredExtension() {
		return REGISTERED_EXTENSION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function<Object, OrderDto> parse() {
		return object -> getOrderDto((String) object);
	}

	/**
	 * Метод возвращает ДТО, сформированный из CSV cтроки.
	 *
	 * @param csvString CSV строка
	 * @return ДТО
	 */
	private OrderDto getOrderDto(String csvString) {
		String[] values = csvString.split(",");
		return new OrderDto(values);
	}

}
