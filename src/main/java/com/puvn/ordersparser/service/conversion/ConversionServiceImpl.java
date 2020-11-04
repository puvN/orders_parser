package com.puvn.ordersparser.service.conversion;

import com.puvn.ordersparser.exception.OrdersParserErrorEnum;
import com.puvn.ordersparser.exception.OrdersParserException;
import com.puvn.ordersparser.model.OrderDto;
import com.puvn.ordersparser.model.Task;
import com.puvn.ordersparser.service.parsing.ParsingService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConversionServiceImpl implements ConversionService {

	//TODO вынести в application.properties
	private static final byte PARSING_THREADS_NUMBER = 10;

	private static final byte CONVERSION_THREADS_NUMBER = 20;

	private static final byte BATCH_SIZE = 100;

	private final ExecutorService parsingExecutorService = Executors.newFixedThreadPool(PARSING_THREADS_NUMBER);

	private final ExecutorService conversionExecutorService = Executors.newFixedThreadPool(CONVERSION_THREADS_NUMBER);

	@Override
	public void convertTasks(List<Task> tasks) {
		List<Callable<Void>> callables = new ArrayList<>();
		for (Task task : tasks) {
			callables.add(() -> {
				parseFileWithBatching(task.getService(), task.getFilename());
				return null;
			});
		}
		try {
			parsingExecutorService.invokeAll(callables);
		} catch (InterruptedException e) {
			throw new OrdersParserException(OrdersParserErrorEnum.UNEXPECTED_EXCEPTION);
		}
	}

	private void parseFileWithBatching(ParsingService parsingService, String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
			String line = reader.readLine();
			List<OrderDto> batch = new ArrayList<>(BATCH_SIZE);
			while (line != null) {
				batch.add(parsingService.parse().apply(line));
				if (batch.size() == BATCH_SIZE) {
					conversionExecutorService.submit(() -> convertToOutput(batch));
					batch.clear();
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			throw new OrdersParserException(OrdersParserErrorEnum.CAN_NOT_READ_FILE);
		}
	}

	//TODO сделать конвертацию и вывод в stdout
	private void convertToOutput(List<OrderDto> batch) {
		for (OrderDto orderDto : batch) {
			System.out.println(orderDto);
		}
	}

}
