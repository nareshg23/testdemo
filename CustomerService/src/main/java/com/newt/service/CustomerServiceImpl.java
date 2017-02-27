package com.newt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newt.model.Customer;
import com.newt.repository.CustomerRepository;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	 @Autowired
	 private CustomerRepository customerRepository;


	@Override
	public Map<String, Customer> findCustomerBycustomerId(int customerId) {
		Map<String, Customer> customerDetails = new HashMap<String, Customer>();
		Customer custDetails = customerRepository.findCustomerBycustomerId(customerId);
		if(custDetails != null){
			customerDetails.put("customerDetails", custDetails);
			return customerDetails;
		}
		return customerDetails;
	}


	@Override
	public Map<String, Customer> findCustomerBycustomerName(String customerName) {
		Map<String, Customer> customerDetails = new HashMap<String, Customer>();
		Customer custDetails = customerRepository.findCustomerBycustomerName(customerName);
		if(custDetails != null){
			customerDetails.put("customerDetails", custDetails);
			return customerDetails;
		}
		return customerDetails;
	}


	@Override
	public Iterable<Customer> findAll() {
		return  customerRepository.findAll();
	}


	@Override
	public Map<String, Customer> save(Customer customer) {
		Map<String, Customer> customerDetails = new HashMap<String, Customer>();
		Customer custDetails = customerRepository.save(customer);
		if(custDetails != null){
			customerDetails.put("customerDetails", custDetails);
			return customerDetails;
		}
		return customerDetails;
	}


	@Override
	public Customer findOne(int id) {
		return customerRepository.findOne(id);
	}


	@Override
	public Customer findCustomerBycustomerEmail(String customerEmail) {
		return customerRepository.findCustomerBycustomerEmail(customerEmail);
	}


	@Override
	public Map<String, Customer> findCustomerByusername(String username) {
		Map<String, Customer> customerDetails = new HashMap<String, Customer>();
		Customer custDetails = customerRepository.findCustomerByusername(username);
		if(custDetails != null){
			customerDetails.put("customerDetails", custDetails);
			return customerDetails;
		}
		return customerDetails;
	}
	

}
