package com.puvn.ordersparser.service.parsing;

import com.puvn.ordersparser.model.OrderDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public interface ParsingService {

	String getRegisteredExtension();

	Function<Object, OrderDto> parse();

}
