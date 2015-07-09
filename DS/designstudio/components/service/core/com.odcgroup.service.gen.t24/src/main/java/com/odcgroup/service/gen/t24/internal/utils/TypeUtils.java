package com.odcgroup.service.gen.t24.internal.utils;

// General Type utilities
public class TypeUtils {

	/**
	 * Gets a empty string representation of a primitive type - Java style.
	 * 
	 * @param type The type name
	 * @return String representation of an empty value of this type
	 */
	public String getEmptyTypeValue(String type)
	{
		if (type.equals("String")) {
			return "\"\"";
		}
		else if (type.equals("Integer")) {
			return "0";
		}
		else if (type.equals("Boolean")) {
			return "false";
		}
		if (type.equals("void")) {
			return "";
		}
		// This means we are dealing with Custom type, so return new object
		else {
			return "new " + type + "()";
		}	
	}
	
	/**
	 * Converts a result string to the correct type - Java style.
	 * 
	 * @param type The type name
	 * @param resultVariableName The name of the variable containing the result string 
	 * @return String representation of an empty value of this type
	 */
	public String getResultFromString(String type, String resultVariableName)
	{
		if (type.equals("String")) {
			return resultVariableName;
		}
		else if (type.equals("Integer")) {
			return "Integer.parseInt( " + resultVariableName + " )";
		}
		else if (type.equals("Boolean")) {
			return "Boolean.parseBoolean( " + resultVariableName + " )";
		}
		if (type.equals("void")) {
			return "";
		}
		else {
			return resultVariableName;
		}	
	}
}
