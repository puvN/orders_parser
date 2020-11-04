package com.puvn.ordersparser.model;

import com.puvn.ordersparser.service.parsing.ParsingService;

import java.util.Objects;

/**
 * Описание бизнес-задачи приложения.
 * <p>
 * При формировании бизнес-задачи бизнес-сервисом {@link com.puvn.ordersparser.service.BusinessService} определяется
 * конкретная реализация сервиса парсинга по расширению файла, который необходимо преобразовать.
 *
 * @see ParsingService
 */
public class BusinessTask {

	/**
	 * @param service  сервис парсинга
	 * @param filename имя файла, который необходимо преобразовать
	 */
	public BusinessTask(ParsingService service, String filename) {
		this.service = service;
		this.filename = filename;
	}

	/**
	 * сервис парсинга.
	 */
	private final ParsingService service;
	/**
	 * имя файла, который необходимо преобразовать.
	 */
	private final String filename;

	/**
	 * @return сервис парсинга
	 */
	public ParsingService getService() {
		return service;
	}

	/**
	 * @return имя файла.
	 */
	public String getFilename() {
		return filename;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BusinessTask businessTask = (BusinessTask) o;
		return service.equals(businessTask.service)
				&& filename.equals(businessTask.filename);
	}

	@Override
	public int hashCode() {
		return Objects.hash(service, filename);
	}

}