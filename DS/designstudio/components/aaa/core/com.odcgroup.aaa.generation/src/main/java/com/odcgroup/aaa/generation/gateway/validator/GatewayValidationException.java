package com.odcgroup.aaa.generation.gateway.validator;


public class GatewayValidationException extends Exception {

	private static final long serialVersionUID = -9195133798172641342L;

	public GatewayValidationException(String message) {
		super(message);
	}

	public GatewayValidationException(String message, Exception e) {
		super(message, e);
	}
}
