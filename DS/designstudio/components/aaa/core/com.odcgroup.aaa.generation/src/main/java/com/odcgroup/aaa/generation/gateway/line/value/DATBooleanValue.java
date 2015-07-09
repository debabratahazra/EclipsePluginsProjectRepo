package com.odcgroup.aaa.generation.gateway.line.value;

import com.odcgroup.aaa.generation.gateway.writer.Formatter;

public class DATBooleanValue extends DATValue {
	
	private Boolean value;
	
	public DATBooleanValue(Boolean value) {
		this.value = value;
	}

	public String format(Formatter formatter) {
		return formatter.format(value);
	}
}
