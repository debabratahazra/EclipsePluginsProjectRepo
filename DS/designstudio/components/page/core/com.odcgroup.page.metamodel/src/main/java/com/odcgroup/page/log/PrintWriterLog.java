package com.odcgroup.page.log;

import java.io.PrintWriter;
import java.io.Writer;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;

/**
 * Simple Logger which wraps the Writer in a PrintWriter.
 * 
 * @author Gary Hayes
 */
public class PrintWriterLog implements ILog {
	
	/** The PrintWriter. */
	private PrintWriter printWriter;
	
	/**
	 * Creates a new PrintWriterLog.
	 * 
	 * @param writer The Writer to write to
	 */
	public PrintWriterLog(Writer writer) {
		this.printWriter = new PrintWriter(writer);
	}

	/**
	 * Null implementation.
	 * 
	 * @param listener
	 */
	public void addLogListener(ILogListener listener) {
		
	}


	/**
	 * Null implementation.
	 * 
	 * @return Bundle
	 */
	public Bundle getBundle() {
		return null;
	}


	/**
	 * Logs the message
	 * 
	 * @param status
	 */
	public void log(IStatus status) {
		try {
			printWriter.write(status.getMessage().toCharArray());
		status.getException().printStackTrace(printWriter);
		} catch(Exception ignored) {
		}
	}

	/**
	 * Null implementation.
	 * 
	 * @param listener
	 */	
	public void removeLogListener(ILogListener listener) {	
	}
}