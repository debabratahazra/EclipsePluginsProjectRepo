package com.odcgroup.page.transformmodel.util;

public class SignificantFigure {

	private static final String BUSINESS_TYPE = "(BT)";
	private String scale = "";
	private String precision = "";
	
	public SignificantFigure(String scale, String precision) {
		this.scale = scale;
		this.precision = precision;
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return getBTFreeValue(scale);
	}

	/**
	 * @param value
	 * @return
	 */
	private String getBTFreeValue(String value) {
		if (value.contains(BUSINESS_TYPE)) {
			return value.substring(0, value.indexOf(BUSINESS_TYPE)).trim();
		}
		else {
			return value;
		}
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * @return the precision
	 */
	public String getPrecision() {
		return getBTFreeValue(precision);
	}
	
	

	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	
	
}
