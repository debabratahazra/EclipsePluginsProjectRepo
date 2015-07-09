package com.odcgroup.aaa.connector.internal;

public class InvalidMetaDictException extends  TANGIJException {

	private static final long serialVersionUID = 1L;
	private String detailedMessage;

	public InvalidMetaDictException(String message) {
		super(message);
	}
 
	public InvalidMetaDictException(String message, String detailedMessage) {
		super(message);
		this.detailedMessage = detailedMessage;
	}

	public void setDetailedMessage(String message) {
		this.detailedMessage = message;
	}
	
	public String getDetailedMessage() {
		return detailedMessage;
	}
}
