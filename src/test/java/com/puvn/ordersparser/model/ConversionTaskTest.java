package com.puvn.ordersparser.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConversionTaskTest {

	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
	}


	@Test
	void runTest() {
		ConversionTask task = new ConversionTask(getCorrectTestList(true));

		task.run();

		String expectedResult1 =
				"{\"id\":1,\"amount\":100,\"comment\":\"comment\",\"filename\":\"file1\",\"line\":1," +
						"\"result\":\"OK\"}";
		String expectedResult2 = "{\"id\":2,\"amount\":200,\"comment\":\"comment2\",\"filename\":\"file2\"," +
				"\"line\":2,\"result\":\"OK\"}";

		assertTrue(outputStreamCaptor.toString().trim().contains(expectedResult1));
		assertTrue(outputStreamCaptor.toString().trim().contains(expectedResult2));
	}

	@Test
	void runTestWithErrors() {
		ConversionTask task = new ConversionTask(getCorrectTestList(false));

		task.run();

		String expectedResult1 =
				"{\"id\":1,\"amount\":-1,\"comment\":\"comment\",\"filename\":\"file1\",\"line\":1,\"result\":\"У " +
						"числового поля неверный формат, подставлен -1 вместо значения\"}";
		String expectedResult2 = "{\"id\":-1,\"amount\":200,\"comment\":\"comment2\",\"filename\":\"file2\"," +
				"\"line\":2,\"result\":\"У числового поля неверный формат, подставлен -1 вместо значения\"}";

		assertTrue(outputStreamCaptor.toString().trim().contains(expectedResult1));
		assertTrue(outputStreamCaptor.toString().trim().contains(expectedResult2));
	}

	private List<OrderDto> getCorrectTestList(boolean correct) {
		List<OrderDto> dtoList = new ArrayList<>();
		OrderDto dto1;
		OrderDto dto2;
		if (correct) {
			dto1 = new OrderDto("1", "100", "USD", "comment");
			dto2 = new OrderDto("2", "200", "EUR", "comment2");
		} else {
			dto1 = new OrderDto("1", "wrong", "USD", "comment");
			dto2 = new OrderDto("2*", "200", "EUR", "comment2");
		}
		dto1.setFilename("file1");
		dto1.setLineNumber(1L);
		dto2.setFilename("file2");
		dto2.setLineNumber(2L);
		dtoList.add(dto1);
		dtoList.add(dto2);
		return dtoList;
	}

}