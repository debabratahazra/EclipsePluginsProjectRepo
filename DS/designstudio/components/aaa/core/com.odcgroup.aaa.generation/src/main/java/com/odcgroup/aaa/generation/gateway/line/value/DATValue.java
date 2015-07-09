package com.odcgroup.aaa.generation.gateway.line.value;

import com.odcgroup.aaa.generation.gateway.writer.Formatter;


public abstract class DATValue {
	
	public DATValue() {
		
	}

	public abstract String format(Formatter formatter);
	
	public String toString() {
		return format(Formatter.DEFAULT_FORMATTER);
	}

}
