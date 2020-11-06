package com.puvn.ordersparser.service.conversion;

import com.puvn.ordersparser.exception.ApplicationErrorEnum;
import com.puvn.ordersparser.exception.ApplicationException;
import com.puvn.ordersparser.model.BusinessTask;
import com.puvn.ordersparser.model.ConversionTask;
import com.puvn.ordersparser.model.OrderDto;
import com.puvn.ordersparser.service.parsing.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	private final Integer batchSize;

	private final ExecutorService parsingExecutorService;

	private final ExecutorService conversionExecutorService;

	/**
	 * @param parsingThreadsNumber    количество потоков для парсинга
	 * @param conversionThreadsNumber количество потоков для конвертации
	 * @param batchSize               размер пачки ордеров для конвертации
	 */
	@Autowired
	public ConversionServiceImpl(@Value("${application.parsing.threads.number}") Integer parsingThreadsNumber,
								 @Value("${application.conversion.threads.number}") Integer conversionThreadsNumber,
								 @Value("${application.conversion.batch.size}") Integer batchSize) {
		parsingExecutorService = Executors.newFixedThreadPool(parsingThreadsNumber);
		conversionExecutorService = Executors.newFixedThreadPool(conversionThreadsNumber);
		this.batchSize = batchSize;
	}

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
	 * Метод читает файл построчно, с формированием пачек по batchSize, при этом используя функцию
	 * преобразования из сервиса парсинга, чтобы сформировать ДТО для задачи конвертации.
	 *
	 * @param parsingService Сервис парсинга.
	 * @param filename       Имя файла.
	 */
	private void parseFileWithBatching(ParsingService parsingService, String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = reader.readLine();
			List<OrderDto> batch = new ArrayList<>(this.batchSize);
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
				if (batch.size() == this.batchSize || line == null) {
					conversionExecutorService.submit(new ConversionTask(new ArrayList<>(batch)));
					batch.clear();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
