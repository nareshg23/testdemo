package com.newt.ecom;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newt.ecom.bean.ProductCheckoutDetails;
import com.newt.ecom.bean.ProductList;
import com.newt.ecom.bean.ShoppingBean;
import com.newt.ecom.commonutils.EcomConstants;
import com.newt.ecom.commonutils.Productstatus;
import com.newt.ecom.model.AuthTokenInfo;
import com.newt.ecom.model.Customer;
import com.newt.ecom.model.Product;
import com.newt.ecom.model.ShoppingCartItems;

/**
 * Handles requests for the application home page.
 */
@SessionAttributes("loggedInProfile")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView getHome(@ModelAttribute("shoppingBean") ShoppingBean shoppingBean) {
        ModelAndView modelAndView= new ModelAndView("loginPage");
			shoppingBean.setProductList(getProductList());
			modelAndView.addObject("productList",shoppingBean.getProductList());
			modelAndView.addObject("customer", new Customer());
		    modelAndView.addObject("loggedInProfile", new Customer());
		return modelAndView;
	}
	
	@RequestMapping(value = "/customerRegisterPage", method = RequestMethod.GET)
	public ModelAndView customerHome() {
        ModelAndView modelAndView= new ModelAndView("customerRegisterPage");
		 modelAndView.addObject("customer", new Customer());
	     modelAndView.addObject("loggedInProfile", new Customer());
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerCustomer(@ModelAttribute("customer") Customer customer,@ModelAttribute("shoppingBean") ShoppingBean shoppingBean, 
			BindingResult result) throws JsonParseException, JsonMappingException, IOException {
		
		customer.setCustomerName(customer.getFirstName()+""+customer.getLastName());
		customer.setUsername(customer.getCustomerEmail());
		customer.setStatus("ACTIVE");
		
		logger.info("Customer Info ====>"+customer.toString());
		
		ModelAndView modelAndView=new ModelAndView("loginPage");
		//final String restURI = "http://52.207.17.79:8767/customer/add";		
		final String restURI = "http://localhost:8763/customer/add";		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ObjectMapper obj = new ObjectMapper();
		Map<?, ?> results = null;
		try{
			AuthTokenInfo tokenInfo = sendTokenRequest();
			 Assert.notNull(tokenInfo, "Authenticate first please......");
			 HttpEntity<Object> request = new HttpEntity<Object>(customer,getHeaders());
			 ResponseEntity<Map> responseEntity = restTemplate.exchange(restURI+EcomConstants.QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
	        		HttpMethod.POST, request, Map.class);
			//ResponseEntity responseEntity = restTemplate.postForEntity(restURI, customer, Map.class);
			Customer customerDetails = null;
			if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
				 results = (Map<?, ?>) responseEntity.getBody();
				 if(results.get("ErrorMsg") != null){
		    	     modelAndView.addObject("errorMsg", results.get("ErrorMsg"));
		    	     return modelAndView;	 
				 }else if(results.get("customerDetails") != null){
					 customerDetails = obj.convertValue(results.get("customerDetails"), Customer.class);
					 if(customerDetails != null){
							modelAndView.addObject("successMsg", "Customer registred successfuly");
							shoppingBean.setProductList(getProductList());
							modelAndView.addObject("productList",shoppingBean.getProductList());
							return modelAndView;
					 }	
				 }
			}
		}catch(HttpClientErrorException ex){
			if(ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
				results = obj.readValue(ex.getResponseBodyAsString(), Map.class); 
				if(results.get("ErrorMsg") != null){
		    	     modelAndView.addObject("errorMsg", results.get("ErrorMsg"));
		    	     return modelAndView;
				 }else{
		    	     modelAndView.addObject("errorMsg", "Authentication Failed");
		    	     return modelAndView;
				 }
			}
			if(ex.getStatusCode().equals(HttpStatus.EXPECTATION_FAILED)){
				results = obj.readValue(ex.getResponseBodyAsString(), Map.class); 
				if(results.get("ErrorMsg") != null){
		    	     modelAndView.addObject("errorMsg", results.get("ErrorMsg"));
		    	     return modelAndView;
				 }
			}if(ex.getStatusCode().equals(HttpStatus.FORBIDDEN)){
	    	     modelAndView.addObject("errorMsg", "Deny access for the requested resources!");
	    	     return modelAndView;
			}
			if(ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)){
				 modelAndView=new ModelAndView("unauthorized");
	    	     return modelAndView;
			}
		}catch(Exception ex){
			 modelAndView=new ModelAndView("invalidLogin");
   	     modelAndView.addObject("errorMsg", "Internal server exception");
   	     return modelAndView;
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginCustomer(@ModelAttribute("customer") Customer customer, @ModelAttribute("shoppingBean") ShoppingBean shoppingBean) throws JsonParseException, JsonMappingException, IOException {
        ModelAndView modelAndView;
      //Validation
        logger.info("Customer Info====>"+customer.getUsername());
    	if(customer.getUsername()=="" || customer.getPassword()==""){
    		if(customer.getUsername()=="" && customer.getPassword()==""){
    	        modelAndView=new ModelAndView("invalidLogin");
    	        modelAndView.addObject("errorMsg", "please enter the username and password");

    	        return modelAndView;
    		}
    		if(customer.getUsername()==""){
    	        modelAndView=new ModelAndView("invalidLogin");
    	    	modelAndView.addObject("userNameMsg", "please enter the username");
    	        return modelAndView;

    		}
    		if(customer.getPassword()==""){
    	        modelAndView=new ModelAndView("invalidLogin");
    	        modelAndView.addObject("passwordMsg", "please enter the password");
    	        return modelAndView;
    		}
    		
    	}else{
    		List<Customer> customerList = null;
    		Customer customerDetails= null;
    		 Map<?, ?> result = null;
			 ObjectMapper obj = new ObjectMapper();
		     RestTemplate restTemplate = new RestTemplate();
    		try{
		        //final String restURI = "http://52.207.17.79:8767/customer/login/"+customer.getUsername();
		        final String restURI = "http://localhost:8763/customer/login/"+customer.getUsername();
		       AuthTokenInfo tokenInfo = sendTokenRequest();
		    	Assert.notNull(tokenInfo, "Authenticate first please......");
		        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		        ResponseEntity<Map> responseEntity = restTemplate.exchange(restURI+EcomConstants.QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
		        		HttpMethod.GET, request, Map.class);
		        //ResponseEntity<Map> responseEntity = restTemplate.getForEntity(restURI, Map.class);
				if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
					 result = (Map<?, ?>) responseEntity.getBody();
					 if(result.get("ErrorMsg") != null){
						 modelAndView=new ModelAndView("invalidLogin");
			    	     modelAndView.addObject("errorMsg", result.get("ErrorMsg"));
			    	     return modelAndView;	 
					 }else if(result.get("customerDetails") != null){
						 customerDetails = obj.convertValue(result.get("customerDetails"), Customer.class);
					 }
		        if(customerDetails != null){
		        	if(!customerDetails.getPassword().equals(customer.getPassword())){
		        		 modelAndView=new ModelAndView("invalidLogin");
			    	     modelAndView.addObject("errorMsg", "Invalid password try again!!");
			    	     return modelAndView;	
		        	}
		        	modelAndView=new ModelAndView("productCatalog");
		        	shoppingBean.setProductList(getProductList());
		 			modelAndView.addObject("productList",shoppingBean.getProductList());
				    modelAndView.addObject("loggedInProfile", customerDetails);
		        	shoppingBean.setCustomerId(customerDetails.getCustomerId());
		    		shoppingBean.setCustomerName(customerDetails.getCustomerName());
		    		shoppingBean.setCustomerEmail(customerDetails.getCustomerEmail());
		    		modelAndView.addObject("shoppingBean", shoppingBean);
		    		return modelAndView;	
		        	
		        	
		        }else{
		        	 modelAndView=new ModelAndView("invalidLogin");
		    	     modelAndView.addObject("errorMsg", "Invalid Credantials");
		    	     return modelAndView;	
		        }
			 }
    	}catch(HttpClientErrorException ex){
			if(ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
				result = obj.readValue(ex.getResponseBodyAsString(), Map.class); 
				if(result.get("ErrorMsg") != null){
					 modelAndView=new ModelAndView("invalidLogin");
		    	     modelAndView.addObject("errorMsg", result.get("ErrorMsg"));
		    	     return modelAndView;
				 }else{
					 modelAndView=new ModelAndView("invalidLogin");
		    	     modelAndView.addObject("errorMsg", "Authentication Failed");
		    	     return modelAndView;
				 }
			}
			if(ex.getStatusCode().equals(HttpStatus.EXPECTATION_FAILED)){
				result = obj.readValue(ex.getResponseBodyAsString(), Map.class); 
				if(result.get("ErrorMsg") != null){
					 modelAndView=new ModelAndView("invalidLogin");
		    	     modelAndView.addObject("errorMsg", result.get("ErrorMsg"));
		    	     return modelAndView;
				 }
			}
			if(ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)){
				 modelAndView=new ModelAndView("unauthorized");
	    	     return modelAndView;
			}
			if(ex.getStatusCode().equals(HttpStatus.FORBIDDEN)){
				 modelAndView=new ModelAndView("invalidLogin");
	    	     modelAndView.addObject("errorMsg", "Deny access for the requested resources!");
	    	     return modelAndView;
			}
		}catch(Exception ex){
			 modelAndView=new ModelAndView("invalidLogin");
    	     modelAndView.addObject("errorMsg", "Internal server exception"+ex);
    	     return modelAndView;
		}
    		
    	}
    	
   	 modelAndView=new ModelAndView("invalidLogin");
     modelAndView.addObject("errorMsg", "Username and Password does not Match. please enter the correct username and password");
	 modelAndView.addObject("loggedInProfile", new Customer());
     return modelAndView;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView loadLogoutPage(SessionStatus status, @ModelAttribute("shoppingBean") ShoppingBean shoppingBean) {
		status.setComplete();
		ModelAndView modelAndView= new ModelAndView("loginPage");
       	shoppingBean.setProductList(getProductList());
		modelAndView.addObject("productList",shoppingBean.getProductList());
		modelAndView.addObject("customer", new Customer());
	    modelAndView.addObject("loggedInProfile", new Customer());
	    return modelAndView;
	}
	
		
	@RequestMapping(value = "/addProductCart", method = RequestMethod.POST)
	public ModelAndView addProductToCart(@ModelAttribute("loggedInProfile") Customer loggedInProfile,@ModelAttribute("shoppingBean") ShoppingBean shoppingBean, 
			BindingResult result) {
		ModelAndView modelAndView=new ModelAndView("productCatalog");
		
		logger.debug("=====>"+shoppingBean.getProductId()+shoppingBean.getProductName()+shoppingBean.getProductPrice()+shoppingBean.getProductDescription());
		
		//final String restURI = "http://52.207.22.180:8766/shoppingcart/OrderService/create/"+
		final String restURI = "http://localhost:8766/shoppingcart/OrderService/create/"+
		shoppingBean.getCustomerId()+"/"+shoppingBean.getCustomerName()+"/"+shoppingBean.getProductId()+"/"+shoppingBean.getProductName()
		+"/"+shoppingBean.getProductPrice()+"/"+shoppingBean.getProductDescription();	
		
		RestTemplate restTemplate = new RestTemplate();		
		HashMap<String,String> results = restTemplate.postForObject(restURI, null, HashMap.class);
		logger.debug(results.get("Message"));
		if(results != null){
			modelAndView.addObject("message", "Product added to the cart successfully!!!");
		}
    	shoppingBean.setProductList(getProductList());
		shoppingBean.setTotalPrice(Float.parseFloat(results.get("MESSAGE")));
		shoppingBean.setCustomerId(shoppingBean.getCustomerId());
		shoppingBean.setCustomerName(shoppingBean.getCustomerName());
		modelAndView.addObject("productList",shoppingBean.getProductList());
	    modelAndView.addObject("loggedInProfile", loggedInProfile);
		modelAndView.addObject("shoppingBean", shoppingBean);
		return modelAndView;
	}
	
	@RequestMapping(value = "/proceedToPay", method = RequestMethod.GET)
	public ModelAndView proceedToPay(@ModelAttribute("loggedInProfile") Customer loggedInProfile,@ModelAttribute("shoppingBean") ShoppingBean shoppingBean) {
		ModelAndView modelAndView=new ModelAndView("ProceedToPay");
		logger.debug("=====>"+shoppingBean.getProductId()+shoppingBean.getProductName()+shoppingBean.getProductPrice()+shoppingBean.getProductDescription());
		
		//final String restURI = "http://52.207.22.180:8766/shoppingcart/OrderService/get/productList/"+shoppingBean.getCustomerId();		
		final String restURI = "http://localhost:8766/shoppingcart/OrderService/get/productList/"+shoppingBean.getCustomerId();
		RestTemplate restTemplate = new RestTemplate();
		List<ShoppingCartItems> results = restTemplate.getForObject(restURI, List.class);
		logger.debug("=====>"+results);
		
		shoppingBean.setShoppingCartItems(results);
		shoppingBean.setCustomerId(shoppingBean.getCustomerId());
		shoppingBean.setCustomerName(shoppingBean.getCustomerName());
		modelAndView.addObject("loggedInProfile", loggedInProfile);
		modelAndView.addObject("shoppingBean", shoppingBean);
		return modelAndView;
	}
	
	@RequestMapping(value = "/makepayment", method = RequestMethod.POST)
	public ModelAndView makePayment(@ModelAttribute("shoppingBean") ShoppingBean shoppingBean, 
			BindingResult result) {
		ModelAndView modelAndView=new ModelAndView("orderCreated");
		logger.info("=====>***"+shoppingBean.toString());
		ProductCheckoutDetails productCheckout = new ProductCheckoutDetails();
		productCheckout.setCustomerId(shoppingBean.getCustomerId());
		productCheckout.setCustomerName(shoppingBean.getCustomerName());
		productCheckout.setAddress1(shoppingBean.getAddress1());
		productCheckout.setAddress2(shoppingBean.getAddress2());
		productCheckout.setCity(shoppingBean.getCity());
		productCheckout.setState(shoppingBean.getState());
		productCheckout.setCountry(shoppingBean.getCountry());
		productCheckout.setCardExpDate(new Date());
		productCheckout.setCreditCardNo(shoppingBean.getCreditCardNo());
		productCheckout.setPincode(shoppingBean.getPincode());
		List<ProductList> lists = new ArrayList<ProductList>();
		for(ShoppingCartItems shoppingCartList : shoppingBean.getShoppingCartItems()){
			ProductList productList = new ProductList();
			productList.setCustomerId(shoppingBean.getCustomerId());
			productList.setProductId(shoppingCartList.getProductId());
			productList.setProductName(shoppingCartList.getProductName());
			productList.setProductStatus(Productstatus.SHIPPED);
			productList.setShoppingcartId(shoppingCartList.getShoppingcartId());
			lists.add(productList);
		}
		productCheckout.setProductList(lists);
		logger.info("productCheckout==>"+productCheckout.toString());
		//final String restURI = "http://52.207.177.148:8764/checkout/addCheckoutDetails/";		
		final String restURI = "http://localhost:8764/checkout/addCheckoutDetails/";		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ProductCheckoutDetails results = restTemplate.postForObject(restURI, productCheckout, ProductCheckoutDetails.class);
		if(results != null){
			modelAndView.addObject("message", "Order Created Successfully!!!!");
		}		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getTags", method = RequestMethod.GET)
	public @ResponseBody List<Product> getTags(@RequestParam String productName) {
		logger.debug("product entering"+ productName);
		List<Product> result=new ArrayList<Product>();
		List<Product> dataList= getProductList();
		for (Product productBean : dataList) {
			if(null!= productBean.getProductName()){
				if(productBean.getProductName().toUpperCase().contains(productName.toUpperCase())){
					result.add(productBean);
				}
			}	
		}
		return result;

	}
	
	private List<Product> getProductList(){
		 List<Product> results = null;
		 //final String restURI = "http://52.207.22.180:8765/products/";		
		 final String restURI = "http://localhost:8765/products/";		
		 RestTemplate restTemplate = new RestTemplate();
		 try{
			/* AuthTokenInfo tokenInfo = sendTokenRequest();
			 Assert.notNull(tokenInfo, "Authenticate first please......");
		     HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		     ResponseEntity<Map> responseEntity = restTemplate.exchange(restURI+EcomConstants.QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
		        		HttpMethod.GET, request, Map.class);*/
		     ResponseEntity<Map> responseEntity = restTemplate.getForEntity(restURI, Map.class);
			 if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
				 Map productDetails = (Map) responseEntity.getBody();
				 if(productDetails.get("ErrorMsg") != null){
					 return null; 
				 }else{
					 results = (List<Product>) productDetails.get("productDetails");
					 if(results != null)
						 return results;
				 }
			 }
		 }catch(HttpClientErrorException ex){
			logger.error("ProductList==>"+ex.getMessage());
			if(ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)){
				unauthorizedClient();
			}
		 }
		 return null;
	}
	
	/** Prepare HTTP Headers.**/
     
    private static HttpHeaders getHeaders(){
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }
    
    
     /** Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.**/
     
    @SuppressWarnings({ "unchecked"})
	private static AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(EcomConstants.AUTH_SERVER_URI+EcomConstants.QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
        
        if(map!=null){
        	tokenInfo = new AuthTokenInfo();
        	tokenInfo.setAccess_token((String)map.get("access_token"));
        	tokenInfo.setToken_type((String)map.get("token_type"));
        	tokenInfo.setRefresh_token((String)map.get("refresh_token"));
        	tokenInfo.setExpires_in((Integer)map.get("expires_in"));
        	tokenInfo.setScope((String)map.get("scope"));
        }else{
            System.out.println("No user exist----------");
            
        }
        return tokenInfo;
    }
    
    
     /** Add HTTP Authorization header, using Basic-Authentication to send client-credentials.**/
     
    private static HttpHeaders getHeadersWithClientCredentials(){
    	String plainClientCredentials="my-trusted-client:secret";
    	String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
    	
    	HttpHeaders headers = getHeaders();
    	headers.add("Authorization", "Basic " + base64ClientCredentials);
    	return headers;
    } 
    
	private ModelAndView unauthorizedClient() {
		 ModelAndView  modelAndView=new ModelAndView("unauthorized");
	     return modelAndView;	
	}
    
}
