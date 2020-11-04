package com.puvn.ordersparser.exception;


public enum OrdersParserErrorEnum {

	ZERO_ARGUMENTS_ERROR("Количество аргументов должно быть больше 0"),
	NOT_REGISTERED_EXTENSION("Список файлов для преобразования содержит файл с неизвестным расширением"),
	NO_EXTENSION("Список файлов для преобразования содержит файл без расширения"),
	CAN_NOT_READ_FILE("Файл не найден или не может быть прочитан"),
	UNEXPECTED_EXCEPTION("В процессе работы произошла непредвиденная ошибка");

	private final String exceptionMessage;

	OrdersParserErrorEnum(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

}
