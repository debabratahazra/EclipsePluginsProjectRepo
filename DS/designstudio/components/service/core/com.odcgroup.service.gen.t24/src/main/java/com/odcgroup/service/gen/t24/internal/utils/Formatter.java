/*
 * This class is to control the format of generated source code.
 * Here the singleton pattern is used.
 * 
 * Note: this formatter cannot be used in multiple thread environment,
 * where each thread is to generate source code.
 */
package com.odcgroup.service.gen.t24.internal.utils;

public class Formatter {
	//define the number of spaces of each INDENT unit
	private static final String INDENT_SIZE = "  ";
	
	//single Formatter instance object
	private static Formatter instance = null;
	
	//current indent size
	private int m_indent;
	
	//get the single instance of Formatter object
	public static Formatter getInstance() {
		if(instance == null) {
			synchronized(Formatter.class) {
				if(instance == null) {
					instance = new Formatter();
				}
			}
		}
		
		return instance;
	}
	
	private Formatter() {
		m_indent = 0;
	}
	
	//increase indent size when this method is called
	public void indent() {
		m_indent++;
	}
	
	//decrease indent size when this method is called 
	public void outdent() {
		if(m_indent > 0)
			m_indent--;
	}
	
	//calculate current indent space
	public String currentIndent() {
		StringBuilder sb = new StringBuilder();
		
		for(int size = 0; size < m_indent; size++)
			sb.append(INDENT_SIZE);
		
		return sb.toString();
	}
}
