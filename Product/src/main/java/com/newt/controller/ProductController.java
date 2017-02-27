package com.newt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.newt.model.Product;
import com.newt.service.ProductService;
import com.wordnik.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@RequestMapping(value = "search/id/{productId}", method = RequestMethod.GET,  produces = "application/json")
	@ApiOperation(value = "Find By ProductID")
	public Product findbyproductId(@PathVariable int productId) {
		
		return productService.findByproductId(productId);
	}

	@RequestMapping(value = "search/name/{productName}", method = RequestMethod.GET)
	@ApiOperation(value = "Find By ProductName")
	public Product findbyproductName(@PathVariable String productName) {
		return productService.findByProductName(productName);
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Product> findAll() {
		return productService.findAll();
	}

	@ApiOperation(value = "post a product")
	@RequestMapping(method = RequestMethod.POST)
	public Product create(@RequestBody Product car) {
		return productService.save(car);
	}
}
