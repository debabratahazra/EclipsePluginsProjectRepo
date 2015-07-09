package com.odcgroup.aaa.connector.internal.util;


/**
 * Simple VO to collect only the required information
 * @author yan
 */
public class FormatVO {
	
	private String code;
	private String denom;
	private FunctionVO function;
	
	public FormatVO(String code, String denom, String procNameFunction) {
		this.code = code;
		this.denom = denom;
		this.function = new FunctionVO(procNameFunction);
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the denom
	 */
	public String getDenom() {
		return denom;
	}
	/**
	 * @param denom the denom to set
	 */
	public void setDenom(String denom) {
		this.denom = denom;
	}

	/**
	 * @return the function
	 */
	public FunctionVO getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(FunctionVO function) {
		this.function = function;
	}

}
