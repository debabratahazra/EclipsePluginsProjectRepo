package com.odcgroup.aaa.connector.internal.util;

/**
 * @author yan
 */
public class FunctionVO {

	private String procNameFunction;
	
	public FunctionVO(String procNameFunction) {
		this.procNameFunction = procNameFunction;
	}

	/**
	 * @return the function
	 */
	public String getProcNameFunction() {
		return procNameFunction;
	}

	/**
	 * @param function the function to set
	 */
	public void setProcNameFunction(String function) {
		this.procNameFunction = function;
	}
	
	// Utility functions
	public String getDashedProcNameFunction() {
		return ProcNameFunctionFormatter.getDashedProcNameFunction(procNameFunction);
	}
	
	public String getCamelCaseProcNameFunction() {
		return ProcNameFunctionFormatter.getCamelCaseProcNameFunction(procNameFunction);
	}
	
	public String getLowerCaseProcNameFunction() {
		return ProcNameFunctionFormatter.getLowerCaseProcNameFunction(procNameFunction);
	}

}
