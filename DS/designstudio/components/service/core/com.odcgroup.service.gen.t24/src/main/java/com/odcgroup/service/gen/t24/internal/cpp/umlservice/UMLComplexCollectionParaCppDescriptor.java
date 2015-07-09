/*
 * this class represents one parameter of UML operation, which
 * is collection of complex type parameter. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
public class UMLComplexCollectionParaCppDescriptor 
	extends UMLCollectionParaCppDescriptor {
	
	private String m_umlComplexTypeName = null;

  public UMLComplexCollectionParaCppDescriptor(String umlName,
  		                                 Direction direction,
  		                                 String umlComplexTypeName,
  		                                 UMLOperationCppDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;
  	m_umlComplexTypeName = 
  		StringUtils.upperInitialCharacter(umlComplexTypeName);  	
  	m_parent = parent;
  	
  	parent.getParent().addStdClsRefIntoInterface("vector");
  	parent.getParent().addUserClsRefIntoInterface(
  			m_umlComplexTypeName);
  	parent.getParent().addStdClsRefIntoAdaptorHeader("vector");
  	parent.getParent().addUserClsRefIntoAdaptorHeader(
  			m_umlComplexTypeName);
  	parent.getParent().addUserClsRefIntoAdaptorCpp(
  			m_umlComplexTypeName + "Handler");
  }
  
	@Override
	public String getCppBaseType() {		
		return m_umlComplexTypeName;
	}

	@Override
	public String getCppType() {
		return "std::vector<" + getCppBaseType() + " >";		
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
				+ "::RaiseVar(session, "
				+ getJBCItemVarName() + ".Get());\n");
		sb.append(formatter.currentIndent()				
				+ m_umlComplexTypeName + "Handler::FromVAR(session, " 
				+ getJBCItemVarName() + ".Get(), " 
				+ getCppItemVarName() + ");\n");
		
		return sb.toString();
	}

	@Override
	public String convertCppItemVarToJBCVar() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ m_umlComplexTypeName + "Handler::ToVAR(session, " 
				+ getCppItemVarName() + ", " 
				+ getJBCItemVarName() + ".Get());\n");
    sb.append(formatter.currentIndent()
    		+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
    		+ "::LowerVar(session, "
    		+ getJBCItemVarName() + ".Get());\n");		
		return sb.toString();
	}

}
