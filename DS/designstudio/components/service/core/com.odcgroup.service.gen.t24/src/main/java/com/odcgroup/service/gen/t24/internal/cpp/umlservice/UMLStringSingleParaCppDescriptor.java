/*
 * This class represents one UML parameter, 
 * which is single UML String.
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLStringSingleParaCppDescriptor 
	extends UMLSingleParaCppDescriptor {

	//c++ type of this UML parameter
	private static final String CPP_TYPE = "std::string";

  public UMLStringSingleParaCppDescriptor(String umlName,
  		                               Direction direction,  		                                 
  		                               UMLOperationCppDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;  	 	
  	m_parent = parent;
  	
  	parent.getParent().addStdClsRefIntoInterface("string");
  }

	@Override
	public String getCppType() {
		return CPP_TYPE;		
	}
	
	@Override
	public String getParaCppType() {
		switch(getDirection()) {
		case IN:
			return "const " + CPP_TYPE + "&";
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
				+ ";\n");
		
		return sb.toString();
	}	
	
	@Override
	public String cppVarToJBCVarMethodName() {		
		return CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertPrimitiveToVar";
	}


	@Override
	public String jbcVarToCppVarMethodName() {		
		return CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertVarToPrimitive";
	}
}
