package com.newt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.newt.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	public long count();

	public Product findByProductName(String productName);

	public Product findByProductId(int productId);
}
