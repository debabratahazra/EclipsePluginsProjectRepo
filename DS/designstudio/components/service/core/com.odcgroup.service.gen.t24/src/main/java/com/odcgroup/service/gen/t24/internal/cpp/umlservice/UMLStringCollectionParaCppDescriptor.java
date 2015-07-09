/*
 * this class represents one UML parameter,
 * which is collection of UML String.
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLStringCollectionParaCppDescriptor 
	extends UMLCollectionParaCppDescriptor {

	//C++ type of item of this collection parameter
	private static final String CPP_BASE_TYPE = "std::string";
	
	//C++ type of this collection parameter
	private static final String CPP_TYPE = "std::vector<std::string>";

  public UMLStringCollectionParaCppDescriptor(String umlName,
  		                                Direction direction,  		                                 
  		                                UMLOperationCppDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;  	  	
  	m_parent = parent;
  	
  	parent.getParent().addStdClsRefIntoInterface("string");
  	parent.getParent().addStdClsRefIntoInterface("vector");
  	
  	parent.getParent().addStdClsRefIntoAdaptorHeader("string");
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
				+ getJBCItemVarName()+ ".Get());\n");
		
		return sb.toString();
	}
}
