package com.puvn.ordersparser.service.conversion;

import com.puvn.ordersparser.exception.ApplicationErrorEnum;
import com.puvn.ordersparser.exception.ApplicationException;
import com.puvn.ordersparser.model.BusinessTask;
import com.puvn.ordersparser.model.ConversionTask;
import com.puvn.ordersparser.model.OrderDto;
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

/**
 * Реализация сервиса конвертации.
 */
@Service
public class ConversionServiceImpl implements ConversionService {

	//TODO вынести в application.properties
	private static final byte PARSING_THREADS_NUMBER = 10;

	private static final byte CONVERSION_THREADS_NUMBER = 20;

	private static final byte BATCH_SIZE = 100;

	private final ExecutorService parsingExecutorService = Executors.newFixedThreadPool(PARSING_THREADS_NUMBER);

	private final ExecutorService conversionExecutorService = Executors.newFixedThreadPool(CONVERSION_THREADS_NUMBER);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void convertTasks(List<BusinessTask> businessTasks) {
		List<Callable<Void>> callables = new ArrayList<>();
		for (BusinessTask businessTask : businessTasks) {
			callables.add(() -> {
				parseFileWithBatching(businessTask.getService(), businessTask.getFilename());
				return null;
			});
		}
		try {
			parsingExecutorService.invokeAll(callables);
		} catch (InterruptedException e) {
			throw new ApplicationException(ApplicationErrorEnum.UNEXPECTED_EXCEPTION);
		} finally {
			parsingExecutorService.shutdown();
			conversionExecutorService.shutdown();
		}
	}

	/**
	 * Метод читает файл построчно, с формированием пачек по {@value BATCH_SIZE}, при этом используя функцию
	 * преобразования из сервиса парсинга, чтобы сформировать ДТО для задачи конвертации.
	 *
	 * @param parsingService Сервис парсинга.
	 * @param filename       Имя файла.
	 */
	private void parseFileWithBatching(ParsingService parsingService, String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = reader.readLine();
			List<OrderDto> batch = new ArrayList<>(BATCH_SIZE);
			long lineCount = 0;
			while (line != null) {
				lineCount++;
				OrderDto orderDto = parsingService.parse().apply(line);
				if (orderDto != null) {
					orderDto.setFilename(filename);
					orderDto.setLineNumber(lineCount);
					batch.add(orderDto);
				}
				line = reader.readLine();
				if (batch.size() == BATCH_SIZE || line == null) {
					conversionExecutorService.submit(new ConversionTask(new ArrayList<>(batch)));
					batch.clear();
				}
			}
		} catch (IOException e) {
			throw new ApplicationException(ApplicationErrorEnum.CAN_NOT_READ_FILE);
		}
	}

}
