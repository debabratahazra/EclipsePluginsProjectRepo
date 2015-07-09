/*
 * this class represents one member of UML class, which
 * is complex type member 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLClassComplexSingleAttrDotNetDescriptor extends
		UMLClassSingleAttrDotNetDescriptor {
	// UML Properties
	private static final String CARDINALITY = "SINGLE";
	private static final String COMPLEXITY = "COMPLEX";
	//.net default value to initialize this member
	//in managed default constructor
	private String DOT_NET_DEFAULT_VALUE= null;
	private String DOT_NET_CS_DEFAULT_VALUE= null;
	private String m_cppTypeName = null;
	private String m_dotNetTypeName = null;
	private String m_dotNetCSTypeName = null;
	
	
	public UMLClassComplexSingleAttrDotNetDescriptor(String umlName,			                                 
			                              String UMLComplexTypeName,
			                              int index,
			                              UMLClassDotNetDescriptor parent) {
		m_umlName = umlName;
		m_cppTypeName = 
			StringUtils.upperInitialCharacter(UMLComplexTypeName);
		m_dotNetTypeName = 
			"Managed" 
			+ StringUtils.upperInitialCharacter(UMLComplexTypeName)
			+ "^";
		m_dotNetCSTypeName =
			"Managed"
			+ StringUtils.upperInitialCharacter(UMLComplexTypeName);
		m_index = index;
		m_parent = parent;
		
		DOT_NET_DEFAULT_VALUE = "gcnew Managed" 
			+	StringUtils.upperInitialCharacter(UMLComplexTypeName) + "()";
		DOT_NET_CS_DEFAULT_VALUE = "new Managed"
			+	StringUtils.upperInitialCharacter(UMLComplexTypeName) + "()";
		
		parent.addDotNetUserClsRefIntoBO("Managed" 
				+ StringUtils.upperInitialCharacter(UMLComplexTypeName));
		parent.addDotNetUserClsRefIntoHelperCpp("Managed" 
				+ StringUtils.upperInitialCharacter(UMLComplexTypeName));
		parent.addCppUserClsRefIntoHelperCpp(m_cppTypeName + "Marshal");
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
		return m_dotNetTypeName;
	}

	@Override
	public String getDotNetCSType() {
		return m_dotNetCSTypeName;
	}
	
	@Override
	public String getDotNetDefaultValue() {		
		return DOT_NET_DEFAULT_VALUE;
	}
	
	@Override
	public String getDotNetCSDefaultValue() {
		return DOT_NET_CS_DEFAULT_VALUE;
	}
	
	@Override
	public String getCppType() {		
		return m_cppTypeName;
	}
	
	@Override
	public String marshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppType() + " " + getCppVarName() + ";\n");		
		sb.append(formatter.currentIndent()
				+ getCppType() + "Marshal::Marshal(" 
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
				+ getCppType() + "Marshal::Unmarshal(" 
				+ getCppVarName() + ");\n");
		formatter.outdent();
				 
				
		return sb.toString();
	}	
}
