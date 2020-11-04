package com.puvn.ordersparser.model;

import java.util.Arrays;

/**
 * ДТО заказа из файла. Данный ДТО должен быть сформирован как результат {@link java.util.function.Function}
 * после переданной в нее строки из файла.
 */
public class OrderDto {

	private final String[] values;

	/**
	 * @param values Массив значений из строки файла.
	 */
	public OrderDto(String... values) {
		this.values = values;
	}

	/**
	 * @return Массив значений из строки файла.
	 */
	public String[] getValues() {
		return values;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OrderDto orderDto = (OrderDto) o;
		return Arrays.equals(values, orderDto.values);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}

}
