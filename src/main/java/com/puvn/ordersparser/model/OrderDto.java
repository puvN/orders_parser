package com.puvn.ordersparser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * ДТО заказа из файла. Данный ДТО должен быть сформирован как результат {@link java.util.function.Function}
 * после переданной в нее строки из файла.
 */
public class OrderDto {

	private String orderId;

	private String amount;

	private String currency;

	private String comment;

	@JsonIgnore
	private Long lineNumber;

	@JsonIgnore
	private String filename;

	public OrderDto() {
	}

	/**
	 * @param orderId ИД ордера
	 * @param amount сумма
	 * @param currency валюта
	 * @param comment комментарий
	 */
	public OrderDto(@NonNull String orderId,
					@NonNull String amount,
					@NonNull String currency,
					@NonNull String comment) {
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.comment = comment;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getComment() {
		return comment;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

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
		OrderDto orderDto = (OrderDto) o;
		return orderId.equals(orderDto.orderId)
				&& amount.equals(orderDto.amount)
				&& currency.equals(orderDto.currency)
				&& comment.equals(orderDto.comment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, amount, currency, comment);
	}
}
