/*
 * this class represents one member of UML class, which
 * is collection of UML String. 
 */

package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLClassStringCollectionAttrCppDescriptor extends UMLClassCollectionAttrCppDescriptor {

	//c++ type for this member
	private static final String CPP_TYPE = 
		"std::vector<std::string>";
	
	//c++ type for the item of this member
	private static final String CPP_BASE_TYPE = "std::string";
	
	//c++ default value to initialize this member 
	//in business object default constructor 
	private static final String DEFAULT_VALUE = "";	
	
	public UMLClassStringCollectionAttrCppDescriptor(String umlName,
			                                  int index,
			                                  UMLClassCppDescriptor parent) {
		m_umlName = umlName;
		m_index = index;
		m_parent = parent;
		
		parent.addStdClsRefIntoBOHeader("vector");
		parent.addStdClsRefIntoBOHeader("string");
		parent.addStdClsRefIntoBOHeader("cstdlib");
		
		parent.addTemenosClsIntoBOCpp("SOAException");		
	}
	
	@Override
	protected String getCppParaBaseType() {		
		return "const "+ CPP_BASE_TYPE + "&";
	}

	@Override
	protected String getCppReturnBaseType() {		
		return CPP_BASE_TYPE;
	}	

	@Override
	protected String getCppBaseTypeName(){
  	return CPP_BASE_TYPE;
	}
	
	@Override
	public String getCppDefaultValue() {		
		return DEFAULT_VALUE;
	}

	@Override
	public String getCppParaType() {		
		return "const " + CPP_TYPE + "&";
	}

	@Override
	public String getCppReturnType() {
		return CPP_TYPE;
		
	}
	@Override
	public String getCppTypeName() {
		return CPP_TYPE;
	}
	
	@Override
	public String extractCppItemFromJBCVar() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
				
		sb.append(formatter.currentIndent()				
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ConvertVarToPrimitive(session, " 
				+ getItemJBCVarObjectName() + ".Get(), " 
				+ getItemParaName() + ");\n");
		sb.append(formatter.currentIndent() 
				+ getParent().getParaName() + "." 
				+ getAppendItemMethodName() + "("
				+ getItemParaName() + ");\n");
		
		return sb.toString();
	}	
	
	@Override
	public String storeCppItemIntoJBCVar() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();		
		
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ConvertPrimitiveToVar(session, " 
				+ getItemParaName() + ","
				+ getItemJBCVarObjectName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::InsertVarIntoVar(session, "
				+ getJBCObjectVarName() + ".Get(), "
				+ "index + 1, " 
				+ getItemJBCVarObjectName() + ".Get());\n");
		
		return sb.toString();
	}
}
