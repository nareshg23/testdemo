

package com.newt.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newt.CustomerServiceApplication;
import com.newt.constants.CustomerConstants;
import com.newt.constants.GlobalExceptionHandler;
import com.newt.model.Customer;
import com.newt.service.CustomerService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.Authorization;
import com.wordnik.swagger.annotations.AuthorizationScope;

/**
 * @author Manogari G
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private static final Logger logger = Logger.getLogger(CustomerController.class);
    
    @RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
    @ApiOperation(value = "post a customer", response = ResponseEntity.class,authorizations = {@Authorization(value="oauth2",scopes = {@AuthorizationScope(scope = "customer.read",description = "allows adding of customer")})})
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<?, ?>> registerCustomer(@RequestBody @Valid Customer customer,BindingResult result) {
    	Map<?,?> customerDetails = null;
		Map<Object, Object> errorMsgMap = new HashMap<Object, Object>();
    	try{
			if(result.hasErrors()){
	    		errorMsgMap.put(CustomerConstants.ERROR_MSG, "");
				errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
				return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.BAD_REQUEST);
	    	}
	    	Customer customerinfo = customerService.findCustomerBycustomerEmail(customer.getCustomerEmail());
	        if (customerinfo != null) {
	        	throw new GlobalExceptionHandler("Customer Already Exist");
	        } else {
	        	customerDetails = customerService.save(customer);
	        	if (customerDetails == null || customerDetails.isEmpty()) {
	             	errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.DATA_NOT_FOUND);
	 				errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
	 				return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.OK);
	             } ;
	        }
    	}catch(GlobalExceptionHandler ex){
         	errorMsgMap.put(CustomerConstants.ERROR_MSG, ex.getMessage());
 			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.BAD_REQUEST);
         }catch(Exception e){
        	errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.INTERNAL_SERVER_EXCEPTION+"\t"+e.getMessage());
  			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
  			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.EXPECTATION_FAILED);
    	}
    	return new ResponseEntity<Map<?, ?>>(customerDetails, HttpStatus.OK);
    }

    @ApiOperation(value = "get a customer",response = ResponseEntity.class,authorizations = {@Authorization(value=CustomerServiceApplication.securitySchemaOAuth2, scopes = {@AuthorizationScope(scope = CustomerServiceApplication.authorizationScopeGlobal,description = CustomerServiceApplication.authorizationScopeGlobalDesc)})})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    
    public ResponseEntity<Map<?, ?>> findCustomerbyID(@PathVariable int id) {
    	Map<?,?> customerDetails = null;
		Map<Object, Object> errorMsgMap = new HashMap<Object, Object>();
        logger.info("Controller Searching for Customer By Id ===============:" + id);
        try {
        	if(id == 0){
        		throw new GlobalExceptionHandler("Customer Id Not Valid!!"+id);
        	}
        	customerDetails = customerService.findCustomerBycustomerId(id);
        	if (customerDetails == null || customerDetails.isEmpty()) {
             	errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.DATA_NOT_FOUND);
 				errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 				return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.OK);
             } 
         }catch(GlobalExceptionHandler ex){
         	errorMsgMap.put(CustomerConstants.ERROR_MSG, ex.getMessage());
 			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.BAD_REQUEST);
         }
         catch (Exception e) {
            errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.INTERNAL_SERVER_EXCEPTION+"\t"+e.getMessage());
 			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.EXPECTATION_FAILED);
         }
         return new ResponseEntity<Map<?, ?>>(customerDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/allcustomers", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "get all customers")
    public List<Customer> listCustomers() {
    	logger.info("Searching all customer details===============:\n");
        List<Customer> list = (List<Customer>) customerService.findAll();
        //testing.add(nameVal);
        return list;
    }

    @RequestMapping(value = "/byname/{customerName}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "get customer names1")
    public ResponseEntity<Map<?, ?>> findCustomerbyFirstName(@PathVariable("customerName") String customerName) {
    	Map<?,?> customerDetails = null;
		Map<Object, Object> errorMsgMap = new HashMap<Object, Object>();
        logger.info("Controller Searching for Employee Firstname ===============:" + customerName);
        try {
        	if(customerName == null || customerName.isEmpty()){
        		throw new GlobalExceptionHandler("Customer Name Not Valid!!"+customerName);
        	}
        	customerDetails = customerService.findCustomerBycustomerName(customerName);
        	if (customerDetails == null || customerDetails.isEmpty()) {
             	errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.DATA_NOT_FOUND);
 				errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 				return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.OK);
             } 
         }catch(GlobalExceptionHandler ex){
         	errorMsgMap.put(CustomerConstants.ERROR_MSG, ex.getMessage());
 			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.BAD_REQUEST);
         }
         catch (Exception e) {
            errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.INTERNAL_SERVER_EXCEPTION+"\t"+e.getMessage());
 			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
 			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.EXPECTATION_FAILED);
         }
         return new ResponseEntity<Map<?, ?>>(customerDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/login/{username:.+}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "get customer details by username")
    public ResponseEntity<Map<?, ?>> findCustomerbyUsername(@PathVariable("username") String username){
    	Map<?,?> customerDetails = null;
		Map<Object, Object> errorMsgMap = new HashMap<Object, Object>();
        logger.info("Controller Searching for username ===============:" + username);
        try {
        	if(username == null || username.isEmpty()){
        		throw new GlobalExceptionHandler("Username Not Valid!!"+username);
        	}
        	customerDetails = customerService.findCustomerByusername(username);
            if (customerDetails == null || customerDetails.isEmpty()) {
            	errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.DATA_NOT_FOUND);
				errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
				return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.OK);
            } 
        }catch(GlobalExceptionHandler ex){
        	errorMsgMap.put(CustomerConstants.ERROR_MSG, ex.getMessage());
			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            errorMsgMap.put(CustomerConstants.ERROR_MSG, CustomerConstants.INTERNAL_SERVER_EXCEPTION+"\t"+e.getMessage());
			errorMsgMap.put(CustomerConstants.CUSTOMER_LIST, new ArrayList<>());
			return new ResponseEntity<Map<?, ?>>(errorMsgMap, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Map<?, ?>>(customerDetails, HttpStatus.OK);
    }

}
