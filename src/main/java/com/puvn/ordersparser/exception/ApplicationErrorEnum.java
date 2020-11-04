package com.puvn.ordersparser.exception;

/**
 * Перечисление ошибок приложения. Используется при указании ошибки в {@link ApplicationException}.
 */
public enum ApplicationErrorEnum {

	ZERO_ARGUMENTS_ERROR("Количество аргументов должно быть больше 0"),
	NOT_REGISTERED_EXTENSION("Список файлов для преобразования содержит файл с неизвестным расширением"),
	NO_EXTENSION("Список файлов для преобразования содержит файл без расширения"),
	CAN_NOT_READ_FILE("Файл не найден или не может быть прочитан"),
	UNEXPECTED_EXCEPTION("В процессе работы произошла непредвиденная ошибка");

	private final String exceptionMessage;

	/**
	 * @param exceptionMessage Сообщение об ошибке.
	 */
	ApplicationErrorEnum(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * @return Сообщение об ошибке.
	 */
	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

}
