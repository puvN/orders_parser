package com.puvn.ordersparser.service.conversion;

import com.puvn.ordersparser.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConversionService {

	void convertTasks(List<Task> tasks);

}
