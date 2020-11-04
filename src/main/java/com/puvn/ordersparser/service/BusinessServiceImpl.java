package com.puvn.ordersparser.service;

import com.puvn.ordersparser.exception.ApplicationErrorEnum;
import com.puvn.ordersparser.exception.ApplicationException;
import com.puvn.ordersparser.model.BusinessTask;
import com.puvn.ordersparser.service.conversion.ConversionService;
import com.puvn.ordersparser.service.parsing.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Реализация бизнес-сервиса.
 */
@Service
public class BusinessServiceImpl implements BusinessService {

	/**
	 * Список реализаций сервисов парсинга.
	 */
	private final List<ParsingService> parsingServices;

	/**
	 * Сервис конвертации.
	 */
	private final ConversionService conversionService;

	/**
	 * @param parsingServices   Список реализаций сервисов парсинга.
	 * @param conversionService Сервис конвертации
	 */
	@Autowired
	public BusinessServiceImpl(List<ParsingService> parsingServices,
							   ConversionService conversionService) {
		this.parsingServices = parsingServices;
		this.conversionService = conversionService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processFiles(String... args) {
		if (args.length == 0) {
			throw new ApplicationException(ApplicationErrorEnum.ZERO_ARGUMENTS_ERROR);
		}
		List<BusinessTask> businessTasks = Stream.of(args)
				.distinct()
				.map(filename -> new BusinessTask(getParsingServiceByFilename(filename), filename))
				.collect(Collectors.toList());
		this.conversionService.convertTasks(businessTasks);
	}

	/**
	 * Подбор сервиса парсинга по имени файла.
	 *
	 * @param filename Имя файла.
	 * @return Сервис парсинга.
	 */
	private ParsingService getParsingServiceByFilename(String filename) {
		Optional<String> extension = getExtensionFromFileName(filename);
		if (extension.isPresent()) {
			return this.parsingServices.stream()
					.filter(service -> service.getRegisteredExtension().equals(extension.get()))
					.findFirst()
					.orElseThrow(() -> new ApplicationException(ApplicationErrorEnum.NOT_REGISTERED_EXTENSION));
		} else {
			throw new ApplicationException(ApplicationErrorEnum.NO_EXTENSION);
		}
	}

	/**
	 * Метод возрвщает расширение файла из его имени. Расширение возвращается с точкой, для наглядности.
	 *
	 * @param filename Имя файла
	 * @return Расширение файла (с точкой)
	 */
	private Optional<String> getExtensionFromFileName(String filename) {
		return Optional.ofNullable(filename)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".")));
	}

}
