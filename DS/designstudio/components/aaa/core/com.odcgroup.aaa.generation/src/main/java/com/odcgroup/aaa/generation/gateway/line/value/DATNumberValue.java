package com.odcgroup.aaa.generation.gateway.line.value;

import com.odcgroup.aaa.generation.gateway.writer.Formatter;

public class DATNumberValue extends DATValue {
	
	private Number number;
	
	public DATNumberValue(Number number) {
		this.number = number;
	}

	@Override
	public String format(Formatter formatter) {
		return formatter.format(number);
	}

}
