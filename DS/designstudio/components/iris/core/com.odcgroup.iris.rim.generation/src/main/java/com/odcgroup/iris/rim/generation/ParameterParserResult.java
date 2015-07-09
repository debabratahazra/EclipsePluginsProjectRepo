package com.odcgroup.iris.rim.generation;

/**
 * TODO: Document me!
 * 
 * @author taubert
 * 
 */
public class ParameterParserResult {
	
	private String resourceName = "";
	private String parameters = "";

	
	public ParameterParserResult(String sResourceName){
		this.resourceName = sResourceName;
	}
	
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the parameters
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public String toString(){
		return this.resourceName;
	}
}
