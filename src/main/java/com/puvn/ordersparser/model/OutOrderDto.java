package com.puvn.ordersparser.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * ДТО для вывода заказа в указанной форме.
 */
public class OutOrderDto implements Serializable {

	private Long id;

	//допущение в рамках тестового задания, что сумма ордера всегда целое число
	private Long amount;

	private String comment;

	private String filename;

	private Long line;

	private String result;

	public OutOrderDto() {

	}

	/**
	 * @param id       ИД ордера
	 * @param amount   Сумма ордера
	 * @param comment  Комментарий
	 * @param filename Имя файла
	 * @param line     Номер строки в файле
	 * @param result   Результат конвертирования
	 */
	public OutOrderDto(Long id, Long amount, String comment, String filename, Long line, String result) {
		this.id = id;
		this.amount = amount;
		this.comment = comment;
		this.filename = filename;
		this.line = line;
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public Long getAmount() {
		return amount;
	}

	public String getComment() {
		return comment;
	}

	public String getFilename() {
		return filename;
	}

	public Long getLine() {
		return line;
	}

	public String getResult() {
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OutOrderDto that = (OutOrderDto) o;
		return id.equals(that.id)
				&& amount.equals(that.amount)
				&& comment.equals(that.comment)
				&& filename.equals(that.filename)
				&& line.equals(that.line)
				&& result.equals(that.result);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, amount, comment, filename, line, result);
	}

}
