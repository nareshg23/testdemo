package com.newt.constants;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An example of an application-specific exception. Defined here for convenience
 * as we don't have a real domain model or its associated business logic.
 */
public class GlobalExceptionHandler extends RuntimeException  {
	

 /**
  * Unique ID for Serialized object
  */
 private static final long serialVersionUID = -8790211652911971729L;

 public GlobalExceptionHandler(String msg) {
  super(msg);
 }
 
 public GlobalExceptionHandler(String msg, Throwable t) {
  super(msg, t);
 }


}