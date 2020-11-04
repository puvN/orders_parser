package com.puvn.ordersparser.service;

import com.puvn.ordersparser.exception.OrdersParserErrorEnum;
import com.puvn.ordersparser.exception.OrdersParserException;
import com.puvn.ordersparser.model.Task;
import com.puvn.ordersparser.service.conversion.ConversionService;
import com.puvn.ordersparser.service.parsing.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BusinessServiceImpl implements BusinessService {

	private final List<ParsingService> parsingServices;

	private final ConversionService conversionService;

	@Autowired
	public BusinessServiceImpl(List<ParsingService> parsingServices,
							   ConversionService conversionService) {
		this.parsingServices = parsingServices;
		this.conversionService = conversionService;
	}

	@Override
	public void processFiles(String... args) {
		if (args.length == 0) {
			throw new OrdersParserException(OrdersParserErrorEnum.ZERO_ARGUMENTS_ERROR);
		}
		List<Task> tasks = Stream.of(args)
				.distinct()
				.map(filename -> new Task(getParsingServiceByFilename(filename), filename))
				.collect(Collectors.toList());
		this.conversionService.convertTasks(tasks);
	}

	private ParsingService getParsingServiceByFilename(String filename) {
		Optional<String> extension = getExtensionFromFileName(filename);
		if (extension.isPresent()) {
			return this.parsingServices.stream()
					.filter(service -> service.getRegisteredExtension().equals(extension.get()))
					.findFirst()
					.orElseThrow(() -> new OrdersParserException(OrdersParserErrorEnum.NOT_REGISTERED_EXTENSION));
		} else {
			throw new OrdersParserException(OrdersParserErrorEnum.NO_EXTENSION);
		}
	}

	public Optional<String> getExtensionFromFileName(String filename) {
		return Optional.ofNullable(filename)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

}
