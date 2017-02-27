package com.newt.ecom.model;

public class Product {
	
	private int productId;
	private String productName;
	private double productPrice;
	private String productDescription;
	
	public Product(){}
	
	public Product(int productId, String productName, double productPrice, String productDescription)
	{
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDescription = productDescription;		
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", productDescription="
				+ productDescription + "]";
	}
}
