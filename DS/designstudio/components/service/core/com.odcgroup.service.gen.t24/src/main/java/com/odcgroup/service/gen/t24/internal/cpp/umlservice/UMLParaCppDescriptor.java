/*
 * this root abstract class represents one parameter of UML operation. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public abstract class UMLParaCppDescriptor {	
	//JBC VAR struct raw pointer
	private static final String CPP_JBC_VAR_RAW_TYPE = "VAR *";
  
	//uml parameter name specified in UML operation
	protected String m_umlName = null;
	
	//specify this parameter is IN, INOUT, OUT, or RETURN parameter
	protected Direction m_direction = null;
	
	//reference to UML operation represented by UMLOperation object
	protected UMLOperationCppDescriptor m_parent = null;
		
	public String getCppJBCRawType() {
		return CPP_JBC_VAR_RAW_TYPE;
	}	
	
	public String getUMLName() {
		return m_umlName;
	}
	
	public UMLOperationCppDescriptor getParent() {
		return m_parent;
	}
	
	public Direction getDirection() {
		return m_direction;
	}
	
	//the name of JBC VAR raw parameter defined 
	//in C function adaptor parameter list 
	public String getJBCParaName() {
		return StringUtils.lowerInitialCharacter(getUMLName()
				+ "Var");
	}
	
	//the name of C++ parameter variable defined in 
	//C function adaptor body 
	//and parameter list of C++ service proxy member function
	public String getCppParaName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}	
	
	//c++ type of parameter in the parameter list of 
	//c++ service proxy member function
	public abstract String getParaCppType();	
	
	//c++ type of this parameter
	public abstract String getCppType();
	
	//define JBC VAR struct varaible for each input c++ parameter
	public String defineJBCVar() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = Formatter.getInstance();
		
		sb.append(formatter.currentIndent() 
				+ "temenos::soa::common::VARObject "
				+ getJBCParaName()
				+ "(session);\n");
		
		return sb.toString();
	}
	//define C++ variable in C function adaptor 
	//for each JBC VAR struct in the parameter list of 
	//C function adaptor
	public abstract String defineCppVar();
	
	/*
	 * as SOA framework introduces one C++ class to wrap
	 * JBC VAR struct to avoid memory leak and exception safe,
	 * so one flag should be used to indicate whether JBC VAR is
	 * JBC var struct or new JBC VAR class.
	 * Here this flag is defined as "varIsRawPointer"
	 */
	//convert C++ variable to JBC VAR
	public abstract String convertCppVarToJBCVar(boolean varIsRawPointer);
	//convert JBC VAR to C++ variable
	public abstract String convertJBCVarToCppVar(boolean varIsRawPointer);
}
