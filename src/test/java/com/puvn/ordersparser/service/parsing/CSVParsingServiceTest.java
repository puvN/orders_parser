package com.puvn.ordersparser.service.parsing;

import com.puvn.ordersparser.model.OrderDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVParsingServiceTest {

	private final ParsingService parsingService = new CSVParsingService();

	@Test
	void getRegisteredExtensionTest() {
		assertEquals(".csv", parsingService.getRegisteredExtension());
	}

	@Test
	void parseTest() {
		OrderDto result = parsingService.parse().apply("\uFEFF53,100,USD,первый заказ");
		assertEquals("53", result.getOrderId());
		assertEquals("100", result.getAmount());
		assertEquals("USD", result.getCurrency());
		assertEquals("первый заказ", result.getComment());
	}

}