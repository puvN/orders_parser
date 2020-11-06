package com.puvn.ordersparser.service.parsing;

import com.puvn.ordersparser.model.OrderDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Сервис для парсинга CSV файлов.
 */
@Service
public class CSVParsingService implements ParsingService {

	private static final String REGISTERED_EXTENSION = ".csv";

	//приложение в csv файлах, например excel, проставляет byte order mark,
	//от которого лучше избавиться для корректности парсинга. Иначе, например выглядящая в дебаге строка "53"
	//выкинет NumberFormatException.
	public static final String UTF8_BOM = "\uFEFF";

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
		if (csvString.startsWith(UTF8_BOM)) {
			csvString = csvString.substring(1);
		}
		String[] values = csvString.split(",");
		return new OrderDto(values[0], values[1], values[2], values[3]);
	}

}
