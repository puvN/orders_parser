package com.puvn.ordersparser.service;

import org.springframework.stereotype.Service;

@Service
public interface BusinessService {

	void processFiles(String... fileNames);

}
