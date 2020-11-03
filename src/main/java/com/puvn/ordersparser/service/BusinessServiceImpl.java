package com.puvn.ordersparser.service;

import com.puvn.ordersparser.service.convert.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

	private final List<ConversionService> conversionServiceList;

	@Autowired
	public BusinessServiceImpl(List<ConversionService> conversionServiceList) {
		this.conversionServiceList = conversionServiceList;
	}

	@Override
	public void processFiles(String... fileNames) {

	}

	private ConversionService getConversionServiceByFileName(String fileName) {
		return null;
	}

}
