package com.test.groovy

import com.newt.model.Customer
import groovyx.net.http.RESTClient
import spock.lang.Specification
import grails.util.Holders

class CustomerserviceTestCase extends Specification{
	
	def "should test customer exist based on Id"() {
		given:
			 def primerEndpoint = new RESTClient( 'http://localhost:8767/' )
        when:
        def resp = primerEndpoint.get([ path: 'customer/1'])
        then:
        with(resp) {
            status == 200
            contentType == "application/json"
		}
		
		println(resp.data)
		
		with(resp.data) {
		      		println (resp.data)
			}
	}
	
	def "should test customer can able to register"() {
		given:
			 def customer = new Customer();

			 def primerEndpoint = new RESTClient( 'http://localhost:8767/' )
			 
		when:
			def resp = primerEndpoint.post([ path: 'customer/add/', body:[
			  "address1": "string",
			  "address2": "string",
			  "age": 0,
			  "city": "string",
			  "country": "string",
			  "customerEmail": "lllll@h.com",
			  "customerId": 0,
			  "customerName": "string",
			  "dob": "2016-11-07T10:27:08.867Z",
			  "firstName": "string",
			  "gender": "string",
			  "lastName": "string",
			  "password": "string",
			  "phone": "767676767",
			  "pincode": "string",
			  "state": "string",
			  "status": "string",
			  "username": "lllll@h.com"],requestContentType: "application/json"])
		then:
		with(resp) {
			status == 200
			contentType == "application/json"
		}
		
		with(resp.data) {
				println (resp.data)
 			}
	}
}
