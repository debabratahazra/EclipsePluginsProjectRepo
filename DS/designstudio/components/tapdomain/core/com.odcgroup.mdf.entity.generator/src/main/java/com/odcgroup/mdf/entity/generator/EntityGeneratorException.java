package com.odcgroup.mdf.entity.generator;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * Exception thrown by the mdf entity generation
 * @author yan
 */
public class EntityGeneratorException extends RuntimeException {

	private static final long serialVersionUID = -4457939631747416266L;

	private MdfModelElement context;
	
	public EntityGeneratorException(MdfModelElement context, Throwable cause) {	
		super(cause);
		this.context = context;
	}
	
	public String getMessage() {
		String causeMessage = "";
		Throwable throwable = getCause();
		for (int i=0; i<5; i++) {
			if (throwable == null)
				break;
			causeMessage = causeMessage + "\n" + throwable.getClass().getName() + ": " + throwable.getMessage();
			throwable = throwable.getCause();
		}
		
		return "An unexpected problem occurs while generating code for " +
				getContext() + ".\n" + 
				causeMessage;
	}
	
	public String toString() {
		String stackTrace = "";
		if (getCause() != null) {
			StringWriter sw = new StringWriter();
			getCause().printStackTrace(new PrintWriter(sw));
			stackTrace = sw.toString();
		}
		return "The Entity Generator encountered an unexpected error. " +
				"Context: " + getContext() + 
				"Cause: " + (getCause()!=null?getCause().toString() + "\n" + stackTrace:"<unknown>");
	}
	
	private String getContext() {
		if (context == null) {
			return "null";
		}
		
		if (context.getQualifiedName() == null) {
			return context.toString() + "(qualified name is null)";
		}
		
		return context.getQualifiedName().getQualifiedName();
	}

}
