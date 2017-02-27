package com.newt.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.Resources;
import com.newt.bean.ProductCheckoutDetails;
import com.newt.bean.ProductList;
import com.newt.model.Checkout;
import com.newt.model.ProductCheckout;
import com.newt.service.CheckoutService;
import com.newt.service.ProductCheckoutService;
import com.wordnik.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private ProductCheckoutService prodchkoutService;
    
    private static final Logger logger = Logger.getLogger(CheckoutController.class);
    
    @ApiOperation(value = "post a customer")
    @RequestMapping(value = "/addCheckoutDetails", method = RequestMethod.POST)

    
    
    public String registerCustomer(@RequestBody ProductCheckoutDetails productcheckoutdtls) throws IOException {

        if(productcheckoutdtls != null)
         {   
         Checkout checkout = new Checkout();
         checkout.setAddress1(productcheckoutdtls.getAddress1());
         checkout.setAddress2(productcheckoutdtls.getAddress2());
         checkout.setCardExpDate(productcheckoutdtls.getCardExpDate());
         checkout.setCity(productcheckoutdtls.getCity());
         checkout.setCountry(productcheckoutdtls.getCountry());
         checkout.setCreditCardNo(productcheckoutdtls.getCreditCardNo());
         checkout.setCustomerEmail(productcheckoutdtls.getCustomerEmail());
         checkout.setCustomerId(productcheckoutdtls.getCustomerId());
         checkout.setCustomerName(productcheckoutdtls.getCustomerName());
         checkout.setPincode(productcheckoutdtls.getPincode());
         checkout.setState(productcheckoutdtls.getState());
         checkoutService.save(checkout);
         for (int i=0;i<productcheckoutdtls.getProductList().size();i++) {
        	 
          ProductCheckout saveprod = new ProductCheckout();
          saveprod.setCustomerId(productcheckoutdtls.getCustomerId());
          saveprod.setProductId(productcheckoutdtls.getProductList().get(i).getProductId());
          saveprod.setProductName(productcheckoutdtls.getProductList().get(i).getProductName());
          saveprod.setCheckoutOrderId(checkout.getOrderId());
          saveprod.setShoppingcartId(productcheckoutdtls.getProductList().get(i).getShoppingcartId());
          saveprod.setProductStatus(productcheckoutdtls.getProductList().get(i).getProductStatus());
          prodchkoutService.save(saveprod);          
      }
      //   publishProductStatusChangeEvent(productcheckoutdtls.getProductList());
          return "";
          
         }
           return null;
    }
    
  	/*public void publishProductStatusChangeEvent(List<ProductList> productList) throws IOException {
		KafkaProducer<String, String> producer = createKafkaProConnection();
		ObjectMapper mapper = new ObjectMapper();
		try {
			String event = "\"Product Status Changed\"";
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = mapper.writeValueAsString(productList);
			producer.send(new ProducerRecord<String,  String>("product-details",
					arrayToJson));
		} catch (Throwable throwable) {
			System.out.printf("%s", throwable.getStackTrace());
		} finally {
			producer.close();
		}
		
	}

	private KafkaProducer<String, String> createKafkaProConnection()
			throws IOException {
		KafkaProducer<String, String> producer;
		try (InputStream props = Resources.getResource("producer.props").openStream()) {
			Properties properties = new Properties();
			properties.load(props);
			producer = new KafkaProducer<>(properties);
		}
		return producer;
	}
*/ }
