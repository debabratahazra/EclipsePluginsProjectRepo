/*
 * this class represents one member of UML class, which
 * is UML Boolean member. 
 */

package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;


public class UMLClassBooleanSingleAttrCppDescriptor extends
		UMLClassSingleAttrCppDescriptor {
	//c++ type for UML Boolean primitive type
	private static final String CPP_BOOL_TYPE = "bool";
	//c++ default value to initialize this member in
	//c++ default constructor
	private static final String CPP_DEFAULT_VALUE="true";
		
	public UMLClassBooleanSingleAttrCppDescriptor(String umlName, int index, 
																UMLClassCppDescriptor parent) {
		m_umlName = umlName;
		m_index = index;
		m_parent = parent;		
	}	

	@Override
	public String getCppDefaultValue() {
		// TODO Auto-generated method stub
		return CPP_DEFAULT_VALUE;
	}
	
	@Override
	public String getCppTypeName() {
		return CPP_BOOL_TYPE;
	}
	
	@Override
	public String getCppParaType() {
		// TODO Auto-generated method stub
		return CPP_BOOL_TYPE;
	}

	@Override
	public String getCppReturnType() {
		// TODO Auto-generated method stub
		return CPP_BOOL_TYPE;
	}

	@Override
	public String JBCVarToCppVarMethodName() {
		// TODO Auto-generated method stub
		return CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertVarToPrimitive";
	}

	@Override
	public String cppVarToJBCVarMethodName() {
		// TODO Auto-generated method stub
		return CppConstants.SOA_JBC_VAR_HANDLER_CLASS
			+ "::ConvertPrimitiveToVar";
	}	
}
