package com.puvn.ordersparser.service.parsing;

import com.puvn.ordersparser.model.OrderDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONParsingServiceTest {

	private final ParsingService parsingService = new JSONParsingService();

	@Test
	void getRegisteredExtensionTest() {
		assertEquals(".json", parsingService.getRegisteredExtension());
	}

	@Test
	void parseTest() {
		OrderDto result = parsingService.parse()
				.apply("{\"orderId\":2,\"amount\":200,\"currency\":\"EUR\",\"comment\":\"oplata zakaza\"}");
		assertEquals("2", result.getOrderId());
		assertEquals("200", result.getAmount());
		assertEquals("EUR", result.getCurrency());
		assertEquals("oplata zakaza", result.getComment());
	}

}