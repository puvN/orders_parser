package com.puvn.ordersparser.exception;

public class OrdersParserException extends RuntimeException {

	public OrdersParserException() {
		super();
	}

	public OrdersParserException(String exceptionString) {
		super(exceptionString);
	}

	public OrdersParserException(OrdersParserErrorEnum errorEnum) {
		super(errorEnum.getExceptionMessage());
	}

}
