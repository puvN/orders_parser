package com.puvn.ordersparser.model;

import com.puvn.ordersparser.service.parsing.ParsingService;

import java.util.Objects;

public class Task {

	public Task(ParsingService service, String filename) {
		this.service = service;
		this.filename = filename;
	}

	private final ParsingService service;

	private final String filename;

	public ParsingService getService() {
		return service;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Task task = (Task) o;
		return service.equals(task.service) &&
				filename.equals(task.filename);
	}

	@Override
	public int hashCode() {
		return Objects.hash(service, filename);
	}

}