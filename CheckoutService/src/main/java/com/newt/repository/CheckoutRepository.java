package com.newt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
import com.newt.model.Checkout;

@Repository
public interface CheckoutRepository extends CrudRepository<Checkout,Integer>{

 
    public Checkout findCheckoutByorderId(int customerId);
}
