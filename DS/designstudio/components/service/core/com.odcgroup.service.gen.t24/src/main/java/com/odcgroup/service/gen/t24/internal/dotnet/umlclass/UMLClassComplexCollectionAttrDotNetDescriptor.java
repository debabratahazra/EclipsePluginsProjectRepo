/*
 * this class represents one member of UML class, which
 * is collection of UML complex type. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;



public class UMLClassComplexCollectionAttrDotNetDescriptor extends
		UMLClassCollectionAttrDotNetDescriptor {
	// UML Properties
	private static final String CARDINALITY = "MULTIPLE";
	private static final String COMPLEXITY = "COMPLEX";
	//c++ base type name
	private String m_cppBaseName;
	//.NET type name
	private String m_dotNetBaseType = null;
	// .NET C# Types and Values
	private String m_dotNetCSBaseType = null;
	private String m_dotNetCSType = null;
	private String DOT_NET_CS_DEFAULT_VALUE = null;
	
	public UMLClassComplexCollectionAttrDotNetDescriptor(String umlName,
			                                   String umlComplexTypeName,
			                                   int index,
			                                   UMLClassDotNetDescriptor parent) {
		m_umlName = umlName;
		m_cppBaseName = StringUtils.upperInitialCharacter(umlComplexTypeName);
		m_dotNetBaseType = "Managed" 
			+ StringUtils.upperInitialCharacter(umlComplexTypeName)
			+ "^";
		m_dotNetCSBaseType = "Managed"
			+ StringUtils.upperInitialCharacter(umlComplexTypeName);
		m_index = index;
		m_parent = parent;		
		
		m_dotNetCSType = "List<Managed"
			+ StringUtils.upperInitialCharacter(umlComplexTypeName)
			+ ">";
		
		DOT_NET_CS_DEFAULT_VALUE = "new List<Managed"
			+ StringUtils.upperInitialCharacter(umlComplexTypeName)
			+ ">()";
		
		parent.addDotNetStdClsRefIntoBO("System::Collections::Generic::List");
		parent.addDotNetUserClsRefIntoBO("Managed" 
				+ StringUtils.upperInitialCharacter(umlComplexTypeName));
		
		parent.addDotNetUserClsRefIntoHelperCpp("Managed" 
				+ StringUtils.upperInitialCharacter(umlComplexTypeName));
		parent.addCppUserClsRefIntoHelperCpp(m_cppBaseName + "Marshal");
				
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
	protected String getCppBaseType() {		
		return m_cppBaseName;
	}

	@Override
	public String getCppType() {		
		return "std::vector<" + getCppBaseType() + ">";
	}
	
	@Override
	public String getDotNetBaseType() {		
		return m_dotNetBaseType;
	}

	@Override
	public String getDotNetCSBaseType() {
		return m_dotNetCSBaseType;
	}
	
	@Override
	public String getDotNetType() {		
		return "List<" + m_dotNetBaseType + ">^";
	}

	@Override
	public String getDotNetCSType() {		
		return m_dotNetCSType;
	}
	
	@Override
	public String getDotNetDefaultValue() {		
		return "gcnew List<" + m_dotNetBaseType + ">()";
	}

	@Override
	public String getDotNetCSDefaultValue() {		
		return DOT_NET_CS_DEFAULT_VALUE;
	}
	
	@Override
	protected String marshalItem() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppBaseType() + " " + getCppItemVarName() + ";\n");
		sb.append(formatter.currentIndent()
				+ getCppBaseType() + "Marshal::Marshal("
				+ getDotNetItemVarName() + ","
				+ getCppItemVarName() + ");\n");
		sb.append(formatter.currentIndent()
				+ getParent().getCppParaName() 
				+ "." 
				+ getCppAppendItemMethodName() + "(" + getCppItemVarName() + ");\n");
		formatter.outdent();
		
		return sb.toString();
	}

	@Override
	protected String unmarshalItem() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppBaseType() + " " + getCppItemVarName() 
				+ " = "
				+ getParent().getCppParaName()+ "." + getCppGetItemMethodName() 
				+ "(index);\n");
		sb.append(formatter.currentIndent()
				+ getDotNetBaseType() + " " + getDotNetItemVarName() 
				+ " = " 
				+ getCppBaseType() + "Marshal::Unmarshal(" 
				+ getCppItemVarName() + ");\n");		
		sb.append(formatter.currentIndent()
				+ getParent().getDotNetParaName() + "->" 
				+ getDotNetPropertyName() + "->Add("
				+ getDotNetItemVarName() + ");\n");
		
		return sb.toString();
	}	
}
