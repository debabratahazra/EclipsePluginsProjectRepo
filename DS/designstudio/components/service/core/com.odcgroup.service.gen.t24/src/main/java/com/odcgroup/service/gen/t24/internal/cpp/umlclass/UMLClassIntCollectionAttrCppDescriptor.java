/*
 * this class represents one member of UML class, which
 * is collection of UML Integer. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLClassIntCollectionAttrCppDescriptor extends UMLClassCollectionAttrCppDescriptor {
	//c++ type for this collection member
	private static final String CPP_TYPE = "std::vector<int>";
	
	//c++ type for the item of this collection member
	private static final String CPP_BASE_TYPE = "int";
	
	//c++ default value to initialize this member 
	//in c++ default constructor
	private static final String DEFAULT_VALUE = "";

	public UMLClassIntCollectionAttrCppDescriptor(String umlName,
			                               int index,
			                               UMLClassCppDescriptor parent) {
		m_umlName = umlName;
		m_index = index;
		m_parent = parent;
		
		parent.addStdClsRefIntoBOHeader("vector");
		parent.addStdClsRefIntoBOHeader("cstdlib");
		
		parent.addTemenosClsIntoBOCpp("SOAException");		
	}
	
	@Override
	protected String getCppParaBaseType() {
		// TODO Auto-generated method stub
		return CPP_BASE_TYPE;
	}

	@Override
	protected String getCppReturnBaseType() {
		// TODO Auto-generated method stub
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
		
		return "const " + CPP_TYPE + "&";
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
				+ getItemParaName() + ", "
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
