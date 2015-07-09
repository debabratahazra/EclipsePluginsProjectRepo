/*
 * this abstract class represents one UML parameter, 
 * which is singleton parameter 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;


public abstract class UMLSingleParaCppDescriptor 
	extends UMLParaCppDescriptor {

	@Override
	public String convertJBCVarToCppVar(boolean varIsRawPointer) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "//convert JBC VAR " + getJBCParaName() 
				+ " To C++ variable " + getCppParaName() + "\n");
		
		sb.append(formatter.currentIndent()
					+ jbcVarToCppVarMethodName()  + "(session, ");
		if(varIsRawPointer)
			sb.append(getJBCParaName() + ", ");
		else
			sb.append(getJBCParaName() + ".Get(), ");
		sb.append(getCppParaName() +");\n");
		
		return sb.toString();
	}

	

	@Override
	public String convertCppVarToJBCVar(boolean varIsRawPointer) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "//convert C++ variable " + getCppParaName() 
				+ " To JBC VAR " + getJBCParaName() + "\n");
		
		sb.append(formatter.currentIndent()
				+ cppVarToJBCVarMethodName() + "(session, "
				+ getCppParaName() + ", ");
		if(varIsRawPointer)
			sb.append(getJBCParaName());
		else
			sb.append(getJBCParaName() + ".Get()");
		sb.append(");\n");
		
		return sb.toString();
	}
	
  // C++ method name to covert c++ variable to JBC VAR struct		
	public abstract String cppVarToJBCVarMethodName();
	// C++ method name to convert JBC VAR struct to variable
	public abstract String jbcVarToCppVarMethodName();
}
