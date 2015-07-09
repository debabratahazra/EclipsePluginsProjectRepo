/*
 * this class represents one member of UML class, which
 * is one UML String member. 
 */

package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLClassStringSingleAttrDotNetDescriptor 
		extends UMLClassSingleAttrDotNetDescriptor {
	// UML Properties
	private static final String CARDINALITY = "SINGLE";
	private static final String COMPLEXITY = "PRIMITIVE";
	//c++ type
	private static final String CPP_TYPE = "std::string";
	//.net type for this member
	private static final String DOT_NET_TYPE = "String^";
	//.NET default value initialize this member 
	private static final String DOT_NET_CS_TYPE = "String";
	//.NET default value initialize this member 
	//in managed business object default constructor 
	private static final String DOT_NET_DEFAULT_VALUE = "\"\"";
  
	public UMLClassStringSingleAttrDotNetDescriptor(String umlName, 
  																     int index,
  																     UMLClassDotNetDescriptor parent) {
  	m_umlName = umlName;
  	m_index = index;
  	m_parent = parent;	
  	
  	parent.addDotNetStdClsRefIntoBO("System::String");
										
		parent.addCppTemenosUserClsRefIntoHelperCpp("PrimitiveMarshal");
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
		return getDotNetCSType();
	}
	@Override
	public String getDotNetType() {		
		return DOT_NET_TYPE;
	}
	
	@Override 
	public String getDotNetCSType() {
		return DOT_NET_CS_TYPE;
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
				+ getCppType() + " " + getCppVarName() + ";\n");		
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::PrimitiveMarshal::Marshal(" 
				+ getParent().getDotNetParaName() + "->" + getDotNetPropertyName() + ", "
				+ getCppVarName() + ");\n");
				
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
				+ getCppType() + " " + getCppVarName() + " = " 
				+ getParent().getCppParaName() + "." + getCppGetMethodName() + "();\n");
		
		sb.append(formatter.currentIndent()				 
				+ getParent().getDotNetParaName() + "->" + getDotNetPropertyName() 
				+ " = \n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::PrimitiveMarshal::Unmarshal(" 
				+ getCppVarName() + ");\n");
		formatter.outdent();
				 
				
		return sb.toString();
	}	
}
