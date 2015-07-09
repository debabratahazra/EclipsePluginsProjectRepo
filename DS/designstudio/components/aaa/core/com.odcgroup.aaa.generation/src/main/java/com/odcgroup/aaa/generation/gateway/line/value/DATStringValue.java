package com.odcgroup.aaa.generation.gateway.line.value;

import com.odcgroup.aaa.generation.gateway.writer.Formatter;

public class DATStringValue extends DATValue {
	
	private String value;
	
	public DATStringValue(String value) {
		this.value = value;
	}

	@Override
	public String format(Formatter formatter) {
		return formatter.format(value);
	}

}
