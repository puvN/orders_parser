package com.puvn.ordersparser.service.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puvn.ordersparser.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Сервис для парсинга json файлов.
 */
@Service
public class JSONParsingService implements ParsingService {

	private static final String REGISTERED_EXTENSION = ".json";

	private final ObjectMapper jsonMapper;

	@Autowired
	public JSONParsingService() {
		this.jsonMapper = new ObjectMapper()
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}

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
		return o -> {
			try {
				return this.jsonMapper.readValue((String) o, OrderDto.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		};
	}

}
