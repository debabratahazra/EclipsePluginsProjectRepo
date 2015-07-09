package com.odcgroup.integrationfwk.ui;

/**
 * General all-purpose exception class for TWS Consumer plug-in
 */
@SuppressWarnings("serial")
public class TWSConsumerPluginException extends Exception {

	public TWSConsumerPluginException() {
		super();
	}

	public TWSConsumerPluginException(String message) {
		super(message);
	}

	public TWSConsumerPluginException(String message, Throwable cause) {
		super(message, cause);
	}

	public TWSConsumerPluginException(Throwable cause) {
		super(cause);
	}

}