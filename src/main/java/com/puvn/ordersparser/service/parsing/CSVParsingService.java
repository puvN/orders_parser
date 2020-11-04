package com.puvn.ordersparser.service.parsing;

import com.puvn.ordersparser.model.OrderDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CSVParsingService implements ParsingService {

	private static final String REGISTERED_EXTENSION = ".csv";

	@Override
	public String getRegisteredExtension() {
		return REGISTERED_EXTENSION;
	}

	@Override
	public Function<Object, OrderDto> parse() {
		return object -> getOrderDto((String) object);
	}

	private OrderDto getOrderDto(String csvString) {
		String[] values = csvString.split(",");
		return new OrderDto(values);
	}

}
