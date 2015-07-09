/*
 * this abstract class represents one parameter of UML operation, which
 * is UML Integer. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLIntSingleParaCppDescriptor extends UMLSingleParaCppDescriptor {

	//c++ type of this UML Operation parameter
	private static final String CPP_TYPE = "int";

  public UMLIntSingleParaCppDescriptor(String umlName,
  		                            Direction direction,
  		                            UMLOperationCppDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;  		
  	m_parent = parent;
  }	

	
	@Override
	public String getCppType() {
		return CPP_TYPE;		
	}

	@Override
	public String getParaCppType() {
		switch(getDirection()) {
		case IN:
			return CPP_TYPE;
		case OUT:
		case INOUT:
			return CPP_TYPE + "&";
		case RETURN:
			return CPP_TYPE;
		default:
			return CPP_TYPE;
		}		
	}	
	
	@Override
	public String defineCppVar() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppType() + " " + getCppParaName() 
				+ " = 0;\n");
		
		return sb.toString();
	}
	
	@Override
	public String cppVarToJBCVarMethodName() {		
		return  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertPrimitiveToVar";
	}

	@Override
	public String jbcVarToCppVarMethodName() {		
		return  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertVarToPrimitive";
	}
}
