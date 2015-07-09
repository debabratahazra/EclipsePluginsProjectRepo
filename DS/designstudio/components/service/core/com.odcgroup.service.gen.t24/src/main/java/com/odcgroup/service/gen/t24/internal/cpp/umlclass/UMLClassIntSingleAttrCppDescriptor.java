/*
 * this class represents one member of UML class, which
 * is UML Integer member. 
 */

package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;


public class UMLClassIntSingleAttrCppDescriptor extends
		UMLClassSingleAttrCppDescriptor {

	//c++ type for this member
	private static final String CPP_INT_TYPE = "int";
	
	//default value to initialize this member 
	//in c++ default constructor
	private static final String CPP_DEFAULT_VALUE = "0";
		
	public UMLClassIntSingleAttrCppDescriptor(String umlName,
																		int index,
																		UMLClassCppDescriptor parent) {
		m_umlName = umlName;
		m_index = index;
		m_parent = parent;
	}
	
	@Override
	public String getCppDefaultValue() {
		return CPP_DEFAULT_VALUE;
	}
	@Override
	public String getCppParaType() {		
		return CPP_INT_TYPE;
	}
	@Override
	public String getCppReturnType() {		
		return CPP_INT_TYPE;
	}

	@Override
	public String getCppTypeName() {
		return CPP_INT_TYPE;
	}
	
	@Override
	public String JBCVarToCppVarMethodName() {		
		return  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertVarToPrimitive";
	}

	@Override
	public String cppVarToJBCVarMethodName() {		
		return  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertPrimitiveToVar";
	}	
}
