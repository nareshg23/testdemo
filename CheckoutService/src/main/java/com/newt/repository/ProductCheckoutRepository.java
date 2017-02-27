package com.newt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
import com.newt.model.Checkout;
import com.newt.model.ProductCheckout;

@Repository
public interface ProductCheckoutRepository extends CrudRepository<ProductCheckout,Integer>{

}
