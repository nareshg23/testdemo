package com.newt.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan
public class CustomerConstants {

	public static String ERROR_MSG;

	public static String CUSTOMER_LIST;

	public static String DATA_NOT_FOUND;

	public static String INTERNAL_SERVER_EXCEPTION;

	@Autowired
	public CustomerConstants(@Value("${customer.errorMsg}") String errorMsg,
			@Value("${customer.customerList}") String customerList,
			@Value("${customer.dataNotFound}") String dataNotFound,
			@Value("${customer.internalServerException}") String internalServerException) {

		CustomerConstants.ERROR_MSG = errorMsg;
		CustomerConstants.CUSTOMER_LIST = customerList;
		CustomerConstants.DATA_NOT_FOUND = dataNotFound;
		CustomerConstants.INTERNAL_SERVER_EXCEPTION = internalServerException;
	}

}
