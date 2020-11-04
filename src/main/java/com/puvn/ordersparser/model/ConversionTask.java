package com.puvn.ordersparser.model;

import java.util.Arrays;
import java.util.List;

public class ConversionTask implements Runnable {

	private final List<OrderDto> batch;

	public ConversionTask(List<OrderDto> batch) {
		this.batch = batch;
	}

	@Override
	public void run() {
		for (OrderDto orderDto : batch) {
			System.out.println(Arrays.asList(orderDto.getValues()));
		}
	}

}
