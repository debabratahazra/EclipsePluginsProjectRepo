/*
 * this class represents one parameter of UML operation, which
 * is collection of UML Boolean. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLBooleanCollectionParaCppDescriptor 
	extends UMLCollectionParaCppDescriptor {
	
	//c++ type of this UML parameter
  private static final String CPP_TYPE = "std::vector<bool>";
  
  //c++ type of the tiem of this UML parameter
  private static final String CPP_BASE_TYPE = "bool";
  
  public UMLBooleanCollectionParaCppDescriptor(String umlName,
  		                                 Direction direction,
  		                                 UMLOperationCppDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;
  	m_parent = parent;
  	
  	parent.getParent().addStdClsRefIntoInterface("vector");
  	parent.getParent().addStdClsRefIntoAdaptorHeader("vector");  	
  	
  }

	@Override
	public String getCppBaseType() {		
		return CPP_BASE_TYPE;
	}	

	@Override
	public String getCppType() {		
		return CPP_TYPE;
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
	public String convertJBCItemVarIntoCppItemVar() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();	
		
		sb.append(formatter.currentIndent()				
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ConvertVarToPrimitive(session, " 
				+ getJBCItemVarName() + ".Get(), " 
				+ getCppItemVarName() + ");\n");
		
		return sb.toString();
	}

	@Override
	public String convertCppItemVarToJBCVar() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ConvertPrimitiveToVar(session, " 
				+ getCppItemVarName() + ", " 
				+ getJBCItemVarName() + ".Get());\n");
		
		return sb.toString();
	}
	
}
