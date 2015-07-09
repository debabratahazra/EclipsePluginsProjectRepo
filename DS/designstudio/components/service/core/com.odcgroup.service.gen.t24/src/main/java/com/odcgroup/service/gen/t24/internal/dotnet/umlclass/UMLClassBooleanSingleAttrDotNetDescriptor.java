/*
 * this class represents one member of UML class, which
 * is UML Boolean member. 
 */

package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;



public class UMLClassBooleanSingleAttrDotNetDescriptor extends
		UMLClassSingleAttrDotNetDescriptor {
	// UML Properties
	private static final String CARDINALITY = "SINGLE";
	private static final String COMPLEXITY = "PRIMITIVE";
	//c++ type
	private static final String CPP_TYPE = "bool";
	
	//.net type for UML Boolean primitive type
	private static final String DOT_NET_TYPE= "bool";
	
	//.net default value to initialize this member in
	//managed business object default constructor
	private static final String DOT_NET_DEFAULT_VALUE = "true";
	
	public UMLClassBooleanSingleAttrDotNetDescriptor(String umlName, int index, 
																UMLClassDotNetDescriptor parent) {
		m_umlName = umlName;
		m_index = index;
		m_parent = parent;		
	}
	
	// UML Properties
	@Override
	public String getCardinality() {
		return CARDINALITY;
	}
	
	@Override
	public String getComplexity() {
		return COMPLEXITY;
	}
	
	@Override
	public String getDotNetBaseType() {		
		return getDotNetType();
	}
	
	@Override 
	public String getDotNetCSBaseType() {
		return getDotNetBaseType();
	}
	
	@Override
	public String getDotNetType() {		
		return DOT_NET_TYPE;
	}

	@Override
	public String getDotNetCSType(){
		return getDotNetType();
	}
	
	@Override
	public String getDotNetDefaultValue() {		
		return DOT_NET_DEFAULT_VALUE;
	}	

	@Override
	public String getDotNetCSDefaultValue() {
		return getDotNetDefaultValue();
	}
	
	@Override
	public String getCppType() {		
		return CPP_TYPE;
	}
	
	@Override
	public String marshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppType() + " " + getCppVarName() + " = " 
				+ getParent().getDotNetParaName() + "->" + getDotNetPropertyName() + ";\n");
		sb.append(formatter.currentIndent()
				+ getParent().getCppParaName() + "." + getCppSetMethodName()
				+ "(" + getCppVarName() + ");\n");
		
		return sb.toString();		
	}

	@Override
	public String unmarshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()				 
				+ getParent().getDotNetParaName() + "->" + getDotNetPropertyName() 
				+ " = "
				+ getParent().getCppParaName() + "." + getCppGetMethodName() + "();\n");
				
		return sb.toString();
	}	
}
