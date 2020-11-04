package com.puvn.ordersparser.model;

import java.util.Arrays;
import java.util.List;

/**
 * Задача на конвертацию пачки {@link OrderDto}, которая была сформирована в
 * {@link com.puvn.ordersparser.service.conversion.ConversionService} в процессе чтения файла, который необходимо
 * преобразовать.
 */
public class ConversionTask implements Runnable {

	private final List<OrderDto> batch;

	/**
	 * @param batch Список ДТО для преобразования.
	 */
	public ConversionTask(List<OrderDto> batch) {
		this.batch = batch;
	}

	/**
	 * Бизнес-смысл задачи преобразования пачки.
	 */
	@Override
	public void run() {
		for (OrderDto orderDto : batch) {
			System.out.println(Arrays.asList(orderDto.getValues()));
		}
	}

}
