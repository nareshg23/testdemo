package com.newt.service;

import java.util.List;

import com.newt.model.Product;

public interface ProductService {
	public Product findByproductId(int productId);

	public Iterable<Product> findAll();

	public Product save(Product products);

	public Product findByProductName(String productName);
}
