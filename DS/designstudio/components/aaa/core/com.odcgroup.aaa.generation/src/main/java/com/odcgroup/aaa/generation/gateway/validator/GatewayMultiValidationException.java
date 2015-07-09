package com.odcgroup.aaa.generation.gateway.validator;

import java.util.Collections;
import java.util.List;

public class GatewayMultiValidationException extends GatewayValidationException {
	
	private static final long serialVersionUID = -5552510746043495470L;
	
	private List<GatewayValidationException> validationExceptions;

	public GatewayMultiValidationException(String message, List<GatewayValidationException> validationExceptions) {
		super(message + "\n" + getIncludedMessage(validationExceptions));
		this.validationExceptions = validationExceptions;
	}
	
	private static String getIncludedMessage(
			List<GatewayValidationException> exceptions) {
		StringBuilder sb = new StringBuilder();
		for (GatewayValidationException e: exceptions) {
			sb.append(e.getMessage());
			sb.append("\n");
		}
		if (sb.charAt(sb.length()-1) == '\n') {
			sb.delete(sb.length()-1, sb.length());
		}
		return sb.toString();
	}

	public List<GatewayValidationException> getValidationExceptions() {
		return Collections.unmodifiableList(validationExceptions);
	}

}
