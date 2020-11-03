package com.puvn.ordersparser.exception;

public enum OrdersParserErrorEnum {

	ZERO_ARGUMENTS_ERROR("Количество аргументов должно быть больше 0");

	private final String exceptionMessage;

	OrdersParserErrorEnum(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

}
