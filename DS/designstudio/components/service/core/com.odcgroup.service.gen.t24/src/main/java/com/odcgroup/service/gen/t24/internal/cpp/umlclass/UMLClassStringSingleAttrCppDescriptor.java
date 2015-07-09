/*
 * this class represents one member of UML class, which
 * is one UML String member. 
 */

package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;


public class UMLClassStringSingleAttrCppDescriptor extends UMLClassSingleAttrCppDescriptor {
	//c++ type for this member
  private static final String CPP_STRING_TYPE = "std::string";
  
  //c++ default value to initialize this member 
  //in business object default constructor
  private static final String CPP_DEFAULT_VALUE = "\"\"";  
  
  public UMLClassStringSingleAttrCppDescriptor(String umlName, 
  																     int index,
  																     UMLClassCppDescriptor parent) {
  	m_umlName = umlName;
  	m_index = index;
  	m_parent = parent;
  	
  	parent.addStdClsRefIntoBOHeader("string");  	
  }

	@Override
	public String getCppDefaultValue() {
		// TODO Auto-generated method stub
		return CPP_DEFAULT_VALUE;
	}

	@Override
	public String getCppParaType() {
		
		return "const " + CPP_STRING_TYPE + "&";
	}

	@Override
	public String getCppReturnType() {		
		return CPP_STRING_TYPE;
	}

	@Override
	public String getCppTypeName() {
		return CPP_STRING_TYPE;
	}

	@Override
	public String JBCVarToCppVarMethodName() {
		// TODO Auto-generated method stub
		return  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertVarToPrimitive";
	}

	@Override
	public String cppVarToJBCVarMethodName() {
		// TODO Auto-generated method stub
		return  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertPrimitiveToVar";
	}  
}
