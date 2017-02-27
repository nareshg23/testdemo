package com.newt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newt.model.Checkout;
import com.newt.repository.CheckoutRepository;

@Service("customerService")
public class CheckoutServiceImpl implements CheckoutService{
	 @Autowired
	 private CheckoutRepository customerRepository;


	@Override
	public Checkout findCheckoutByorderId(int orderId) {
		return customerRepository.findCheckoutByorderId(orderId);
	}


	@Override
	public Iterable<Checkout> findAll() {
		return  customerRepository.findAll();
	}


	@Override
	public Checkout save(Checkout customer) {
		return customerRepository.save(customer);
	}


	@Override
	public Checkout findOne(int id) {
		return customerRepository.findOne(id);
	}

}
