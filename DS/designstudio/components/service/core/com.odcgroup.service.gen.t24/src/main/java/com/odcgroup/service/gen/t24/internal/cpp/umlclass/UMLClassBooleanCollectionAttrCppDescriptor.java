/*
 * this class represents one member of UML class, which
 * is collection of UML Boolean. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLClassBooleanCollectionAttrCppDescriptor 
	extends UMLClassCollectionAttrCppDescriptor {

	//c++ type
	private static final String CPP_TYPE = "std::vector<bool>";
	
	//c++ base type of UML collection Booleam item
	private static final String CPP_BASE_TYPE = "bool";
	
	//default c++ value to initialize this member in c++ default constructor
	private static final String CPP_DEFAULT_VALUE = "";
			
	public UMLClassBooleanCollectionAttrCppDescriptor(String umlName,
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
		return CPP_BASE_TYPE;
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
	public String getCppTypeName() {
		return CPP_TYPE;
	}  
  
	@Override
	public String getCppDefaultValue() {		
		return CPP_DEFAULT_VALUE;
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
