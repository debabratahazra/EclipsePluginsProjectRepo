/*
 * this class represents one member of UML class, which
 * is complex type member 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;


public class UMLClassComplexSingleAttrCppDescriptor extends
		UMLClassSingleAttrCppDescriptor {

	//c++ default value to initialize this complex type member
	//in c++ default constructor
	private static final String DEFAULT_VALUE = "";		
	private String m_umlComplexTypeName = null;	
	
	public UMLClassComplexSingleAttrCppDescriptor(String umlName,			                                 
			                              String UMLComplexTypeName,
			                              int index,
			                              UMLClassCppDescriptor parent) {
		m_umlName = umlName;
		m_umlComplexTypeName = UMLComplexTypeName;		
		m_index = index;
		m_parent = parent;	
		
		parent.addUserClsRefIntoBOHeader(
				StringUtils.upperInitialCharacter(m_umlComplexTypeName));		
		parent.addUserClsIntoBOHandlerCpp(
				StringUtils.upperInitialCharacter(m_umlComplexTypeName)
						+ "Handler");		
	}	

	@Override
	public String getCppDefaultValue() {	
		return DEFAULT_VALUE;
	}

	@Override
	public String getCppParaType() {
		
		return "const " + m_umlComplexTypeName + "&";
	}

	@Override
	public String getCppTypeName() {
		return m_umlComplexTypeName;
	}
	
	@Override
	public String getCppReturnType() {		
		return m_umlComplexTypeName;
	}

	@Override
	public String JBCVarToCppVarMethodName() {		
		return m_umlComplexTypeName 
					 + "Handler::FromVAR";					 
	}

	@Override
	public String cppVarToJBCVarMethodName() {
		
		return m_umlComplexTypeName 
		 			+ "Handler::ToVAR";
	}
	
	//override the definition in UMLClassSingletonMember
	//as Getxxxx().ToString() should be invoked, not
	//Getxxxxx() be invoked
	@Override
	public String convertToString() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		String stringVar = "outputStringFor" + getVarName();
		sb.append(formatter.currentIndent()
				+ "std::string " + stringVar  
				+ " = "
				+ getCppGetMethodName() +"().ToString();\n"); 
				
		sb.append(formatter.currentIndent() 
				+ "oss << \"" + getVarName() 
				+ " : \" << " + stringVar + " << \" | \";\n");
		
		return sb.toString();
	}	
}
