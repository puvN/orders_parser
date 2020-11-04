package com.puvn.ordersparser.exception;

/**
 * Класс ошибки приложения.
 */
public class ApplicationException extends RuntimeException {

	/**
	 * @param errorEnum Перечисление ошибок приложения.
	 * @see ApplicationErrorEnum
	 */
	public ApplicationException(ApplicationErrorEnum errorEnum) {
		super(errorEnum.getExceptionMessage());
	}

}
