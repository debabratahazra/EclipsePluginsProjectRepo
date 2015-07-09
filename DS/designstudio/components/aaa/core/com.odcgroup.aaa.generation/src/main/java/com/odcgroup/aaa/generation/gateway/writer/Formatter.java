package com.odcgroup.aaa.generation.gateway.writer;

import java.text.DateFormat;
import java.util.Date;


public class Formatter {
	
	private Settings settings;
	
	public static final Formatter DEFAULT_FORMATTER = new Formatter(new Settings());
	
	public Formatter(Settings settings) {
		this.settings = settings;
	}
	
	public String format(String value) {
		if (value == null) {
			return settings.getNullValue();
		}
		if (settings.getQuote() == 0) {
			return value;
		} else {
			return settings.getQuote() + value + settings.getQuote();
		}
	}
	
	public String format(Boolean value) {
		if (value == null) {
			return settings.getNullValue();
		} else {
			return value?"1":"0";
		}
	}
	
	public String format(Number value) {
		if (value == null) {
			return settings.getNullValue();
		}
		return value.toString();
	}
	
	public String format(Date value) {
		if (value == null) {
			return settings.getNullValue();
		}
		DateFormat df = settings.getDateFormat();
		return df.format(value);
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	public String getMissingMandatoryFiledValue() {
		return "MISSING_MANDATORY_FIELD";
	}

}
