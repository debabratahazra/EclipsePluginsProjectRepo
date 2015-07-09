/*
 * this class represents one parameter of UML operation, which
 * is complex type parameter. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLComplexSingleParaCppDescriptor 
	extends UMLSingleParaCppDescriptor {

	private String m_umlComplexTypeName = null;

  public UMLComplexSingleParaCppDescriptor(String umlName,
  		                                Direction direction,
  		                                String umlComplexTypeName,
  		                                UMLOperationCppDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;
  	m_umlComplexTypeName = 
  		StringUtils.upperInitialCharacter(umlComplexTypeName);  	
  	m_parent = parent;
  	  	
  	parent.getParent().addUserClsRefIntoAdaptorHeader(
  			m_umlComplexTypeName);  	
  	parent.getParent().addUserClsRefIntoAdaptorCpp(
  			m_umlComplexTypeName + "Handler");
  	
  	parent.getParent().addUserClsRefIntoInterface(
  			m_umlComplexTypeName);
  }
	
	@Override
	public String getCppType() {
		return m_umlComplexTypeName;		
	}

	@Override
	public String getParaCppType() {
		switch(getDirection()) {
		case IN:
			return "const " + m_umlComplexTypeName + "&";
		case OUT:
		case INOUT:
			return m_umlComplexTypeName + "&";
		case RETURN:
			return m_umlComplexTypeName;
		default:
			return m_umlComplexTypeName;
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
		return m_umlComplexTypeName 
		 	+ "Handler::ToVAR";
	}

	@Override
	public String jbcVarToCppVarMethodName() {		
		return m_umlComplexTypeName 
		 + "Handler::FromVAR";	
	}
}
