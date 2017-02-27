package com.newt.ecom.bean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCheckoutDetails {

 private Long customerId;

 private String customerName;

 private String customerEmail;

 private String address1;

 private String address2;

 private String city;

 private String state;

 private String country;

 private String pincode;

 private String creditCardNo;

 private Date cardExpDate;
 
 private List<ProductList> productList = new ArrayList<ProductList>();

 public ProductCheckoutDetails() {
 }

 public Long getCustomerId() {
  return customerId;
 }

 public void setCustomerId(Long customerId) {
  this.customerId = customerId;
 }

 public String getCustomerName() {
  return customerName;
 }

 public void setCustomerName(String customerName) {
  this.customerName = customerName;
 }

 public String getAddress1() {
  return address1;
 }

 public void setAddress1(String address1) {
  this.address1 = address1;
 }

 public String getAddress2() {
  return address2;
 }

 public void setAddress2(String address2) {
  this.address2 = address2;
 }

 public String getPincode() {
  return pincode;
 }

 public void setPincode(String pincode) {
  this.pincode = pincode;
 }

 public String getCustomerEmail() {
  return customerEmail;
 }

 public void setCustomerEmail(String customerEmail) {
  this.customerEmail = customerEmail;
 }

 public String getCity() {
  return city;
 }

 public void setCity(String city) {
  this.city = city;
 }

 public String getState() {
  return state;
 }

 public void setState(String state) {
  this.state = state;
 }

 public String getCountry() {
  return country;
 }

 public void setCountry(String country) {
  this.country = country;
 }

 public String getCreditCardNo() {
  return creditCardNo;
 }

 public void setCreditCardNo(String creditCardNo) {
  this.creditCardNo = creditCardNo;
 }

 public Date getCardExpDate() {
  return cardExpDate;
 }

 public void setCardExpDate(Date cardExpDate) {
  this.cardExpDate = cardExpDate;
 }

 public List<ProductList> getProductList() {
  return productList;
 }

 public void setProductList(List<ProductList> productList) {
  this.productList = productList;
 }

@Override
public String toString() {
	return "ProductCheckoutDetails [customerId=" + customerId + ", customerName=" + customerName + ", customerEmail="
			+ customerEmail + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state="
			+ state + ", country=" + country + ", pincode=" + pincode + ", creditCardNo=" + creditCardNo
			+ ", cardExpDate=" + cardExpDate + ", productList=" + productList + "]";
} 
 
}