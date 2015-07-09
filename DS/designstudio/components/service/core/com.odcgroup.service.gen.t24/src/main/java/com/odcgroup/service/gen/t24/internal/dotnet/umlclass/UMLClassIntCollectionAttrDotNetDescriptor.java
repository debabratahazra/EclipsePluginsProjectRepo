/*
 * this class represents one member of UML class, which
 * is collection of UML Integer. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLClassIntCollectionAttrDotNetDescriptor 
	extends UMLClassCollectionAttrDotNetDescriptor {
	// UML Properties
	private static final String CARDINALITY = "MULTIPLE";
	private static final String COMPLEXITY = "PRIMITIVE";
	//c++ base type
	private static final String CPP_BASE_TYPE = "int";
	//c++ type
	private static final String CPP_TYPE = "std::vector<int>";
	//.net type for this member
	private static final String DOT_NET_TYPE = "List<int>^";
  	// .NET C# type
	private static final String DOT_NET_CS_TYPE = "List<int>";
	//.net base type for this memeber
	private static final String DOT_NET_BASE_TYPE = "int";
	//default value to initialize this memeber
	//in managed default constructor
	private static final String DOT_NET_DEFAULT_VALUE = "gcnew List<int>()";
	// C# initialised value
	private static final String DOT_NET_CS_DEFAULT_VALUE = "List<int>()";
	public UMLClassIntCollectionAttrDotNetDescriptor(String umlName,
			                               int index,
			                               UMLClassDotNetDescriptor parent) {
		m_umlName = umlName;
		m_index = index;
		m_parent = parent;
		
		parent.addDotNetStdClsRefIntoBO("System::Collections::Generic::List");
								
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
	protected String getCppBaseType() {
		return CPP_BASE_TYPE;
	}

	@Override
	public String getCppType() {
		return CPP_TYPE;
	}
	
	@Override
	public String getDotNetBaseType() {		
		return DOT_NET_BASE_TYPE;
	}
	
	@Override
	public String getDotNetCSBaseType() {
		return getDotNetBaseType();
	}
	
	@Override
	public String getDotNetType() {
		// TODO Auto-generated method stub
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
		return DOT_NET_CS_DEFAULT_VALUE;
	}
	
	@Override
	protected String marshalItem() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppBaseType() + " " + getCppItemVarName()
				+ " = "
				+ getDotNetItemVarName() + ";\n");
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
				+ getParent().getDotNetParaName() + "->" 
				+ getDotNetPropertyName() + "->Add("
				+ getCppItemVarName() + ");\n");
		
		return sb.toString();
	}	
}
