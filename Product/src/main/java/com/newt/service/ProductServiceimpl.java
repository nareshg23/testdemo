package com.newt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newt.model.Product;
import com.newt.repository.ProductRepository;

@Service
public class ProductServiceimpl implements ProductService {

	@Autowired
	ProductRepository productRepo;

	public Product findByproductId(int productId) {

		return productRepo.findByProductId(productId);
	}

	public Iterable<Product> findAll() {

		return productRepo.findAll();
	}

	public Product save(Product products) {
		return productRepo.save(products);
	}

	@Override
	public Product findByProductName(String productName) {

		return productRepo.findByProductName(productName);
	}
}
