package com.puvn.ordersparser.model;

import java.util.Arrays;

public class OrderDto {

	private String[] values;

	public OrderDto(String... values) {
		this.values = values;
	}

	public String[] getValues() {
		return values;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderDto orderDto = (OrderDto) o;
		return Arrays.equals(values, orderDto.values);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}

}
