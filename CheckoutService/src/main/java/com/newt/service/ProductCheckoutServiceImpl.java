package com.newt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newt.model.ProductCheckout;
import com.newt.repository.ProductCheckoutRepository;

@Service
public class ProductCheckoutServiceImpl implements ProductCheckoutService{
	 @Autowired
	 private ProductCheckoutRepository prodrepo;

	@Override
	public ProductCheckout save(ProductCheckout prodchkdtls) {
		
		return prodrepo.save(prodchkdtls);
	}
	 
}
