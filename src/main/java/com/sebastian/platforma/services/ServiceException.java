package com.sebastian.platforma.services;

public class ServiceException extends Exception {

	private String errorCode;
	private static final long serialVersionUID = 1L;
	
	public ServiceException(String errorCode) {
		super();
		this.errorCode=errorCode;
		
	}
	public ServiceException(String errorCode,String message, Throwable cause) {
		super(message, cause);
		this.errorCode=errorCode;
		
	}
	public ServiceException(String errorCode,String message) {
		super(message);
		this.errorCode=errorCode;
	
	}
	public ServiceException(String errorCode,Throwable cause) {
		super(cause);
		this.errorCode=errorCode;
	
	}
	public String getErrorCode() {
		return errorCode;
	}

	
}
