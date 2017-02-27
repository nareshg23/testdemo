package com.newt.service;

import java.util.Map;

import com.newt.model.Customer;

public interface CustomerService {
	
	public Map<String, Customer> findCustomerBycustomerId(int customerId);
	
    public Map<String,Customer> findCustomerBycustomerName(String customerName);

	public Iterable<Customer>  findAll();

	public Map<String,Customer> save(Customer customer);

	public Customer findOne(int id);

	public Customer findCustomerBycustomerEmail(String customerEmail);

	public Map<String,Customer> findCustomerByusername(String username);

}
