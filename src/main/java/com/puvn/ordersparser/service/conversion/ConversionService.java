package com.puvn.ordersparser.service.conversion;

import com.puvn.ordersparser.model.BusinessTask;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис конвертирования бизнес задач. Выполняет основную задачу конвертирования тасков
 * в многопоточной среде.
 * <p>
 * Для этого в сервисе есть два {@link java.util.concurrent.ExecutorService}, которые реализуются как FixedThreadPool.
 * Первый пол потоков первого экзекьютора выполняет чтение указанных в {@link BusinessTask} файлов. Чтение выполняется
 * построчно с использованием {@link java.io.BufferedReader}, с формированием пачки
 * {@link com.puvn.ordersparser.model.OrderDto}, которая указывается в
 * {@link com.puvn.ordersparser.model.ConversionTask} как задача на выполнение конвертации.
 * <p>
 * Задача преобразования в выходной формат {@link com.puvn.ordersparser.model.ConversionTask} исполняется на втором
 * пуле потоков второго экзекьютора, в то время как поток первого пула продолжает чтение файла и формирует новую
 * пачку дальше.
 */
@Service
public interface ConversionService {

	/**
	 * Метод выполняет основной бизнес-функционал конвертации бизнес-задач (какой сервис
	 * парсинга какой файл парсит).
	 *
	 * @param businessTasks Список бизнес-задач.
	 */
	void convertTasks(List<BusinessTask> businessTasks);

}
