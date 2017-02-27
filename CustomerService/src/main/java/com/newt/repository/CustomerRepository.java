package com.newt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
import com.newt.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer>{

 
    public Customer findCustomerBycustomerId(int customerId);
    public Customer findCustomerBycustomerName(String customerName);
	public Customer findCustomerBycustomerEmail(String customerEmail);
	public Customer findCustomerByusername(String username);
    
	 

}
