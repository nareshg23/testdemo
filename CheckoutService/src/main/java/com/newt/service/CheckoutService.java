package com.newt.service;

import com.newt.model.Checkout;

public interface CheckoutService {
	
	public Checkout findCheckoutByorderId(int customerId);
	
	public Iterable<Checkout>  findAll();

	public Checkout save(Checkout customer);

	public Checkout findOne(int id);
}
