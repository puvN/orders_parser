package com.puvn.ordersparser.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puvn.ordersparser.exception.ConversionErrorEnum;

import java.util.List;
import java.util.Objects;

/**
 * Задача на конвертацию пачки {@link OrderDto}, которая была сформирована в
 * {@link com.puvn.ordersparser.service.conversion.ConversionService} в процессе чтения файла, который необходимо
 * преобразовать.
 */
public class ConversionTask implements Runnable {

	private final List<OrderDto> batch;

	private final ObjectMapper objectMapper;

	/**
	 * @param batch Список ДТО для преобразования.
	 */
	public ConversionTask(List<OrderDto> batch) {
		this.batch = batch;
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Бизнес-смысл задачи преобразования пачки.
	 */
	@Override
	public void run() {
		//можно в отдельном сервисе определять runnable и передавать в таску, чтоб сам таск не знал о
		//реализации, но так нагляднее наверно, и не хочется плодить сервисы
		batch.stream()
				.map(this::convertToOut)
				.map(this::toJson)
				.filter(Objects::nonNull)
				.forEach(System.out::println);
	}

	/**
	 * Метод преобразует ДТО в выходной формат.
	 *
	 * @param orderDto ДТО ордера
	 * @return ДТО выходного формата
	 */
	private OutOrderDto convertToOut(OrderDto orderDto) {
		ConversionResult conversionResult = new ConversionResult();
		long id = conversionResult.convert(orderDto.getOrderId()).getValue();
		long amount = conversionResult.convert(orderDto.getAmount()).getValue();
		String result = conversionResult.getConversionResult();
		return new OutOrderDto(id, amount, orderDto.getComment(), orderDto.getFilename(), orderDto.getLineNumber(),
				result);
	}

	/**
	 * Метод формирует json строку выходного формата.
	 *
	 * @param outOrderDto ДТО выходного формата
	 * @return json строка
	 */
	private String toJson(OutOrderDto outOrderDto) {
		try {
			return this.objectMapper.writeValueAsString(outOrderDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null; //допущение, что ничего не вернется, если произойдет ошибка преобразования json.
	}

	/**
	 * Приватный класс для хранения результата конвертирования.
	 */
	private static class ConversionResult {

		//мок числового параметра для подстановки в выходной ответ в случае проблем с преобразованием
		private static final byte NUMBER_MOCK = -1;

		private String conversionResult = "OK"; //результат успешного конвертирования

		private long value;

		/**
		 * @param field поле которое нужно конвертировать
		 * @return результат
		 */
		public ConversionResult convert(String field) {
			try {
				value = Long.parseLong(field);
			} catch (NumberFormatException e) {
				conversionResult =
						String.format(ConversionErrorEnum.WRONG_NUMBER_FORMAT.getExceptionMessage(), NUMBER_MOCK);
				value = NUMBER_MOCK;
			}
			return this;
		}

		public String getConversionResult() {
			return conversionResult;
		}

		public long getValue() {
			return value;
		}

	}

}
