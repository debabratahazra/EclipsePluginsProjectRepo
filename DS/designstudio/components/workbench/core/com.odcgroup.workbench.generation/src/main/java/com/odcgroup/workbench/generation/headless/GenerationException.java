package com.odcgroup.workbench.generation.headless;


public class GenerationException extends RuntimeException {
	private static final long serialVersionUID = -946088520789147933L;

//	public GenerationException(String string) {
//		super(string);
//	}

	public GenerationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
