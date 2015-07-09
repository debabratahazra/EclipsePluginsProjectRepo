package com.odcgroup.aaa.generation.gateway.line.value;

import java.util.Date;

import com.odcgroup.aaa.generation.gateway.writer.Formatter;

public class DATDateValue extends DATValue {

	private Date value;

	public DATDateValue(Date value) {
		this.value = value;
	}
	
	public String format(Formatter formatter) {
		return formatter.format(value);
	}

}
