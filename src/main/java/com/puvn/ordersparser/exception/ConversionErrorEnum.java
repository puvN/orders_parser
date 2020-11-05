package com.puvn.ordersparser.exception;

/**
 * Перечисление ошибок конвертации строки файла.
 */
public enum ConversionErrorEnum {

	WRONG_NUMBER_FORMAT("У числового поля записи неверный формат, подставлен %s вместо значения");
	private final String exceptionMessage;

	/**
	 * @param exceptionMessage Сообщение об ошибке.
	 */
	ConversionErrorEnum(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * @return Сообщение об ошибке.
	 */
	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

}
