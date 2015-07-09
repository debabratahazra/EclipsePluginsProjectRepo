/*
 * this class represents one parameter of UML operation, which
 * is collection of UML Boolean. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLBooleanCollectionParaDotNetDescriptor 
	extends UMLParaDotNetDescriptor {
	
	// UML Properties
	private static final String CARDINALTIY = "MULTIPLE";
	private static final String COMPLEXITY = "PRIMITIVE";
	//C++ TYPE
	private static final String CPP_TYPE = "std::vector<bool>";
	//.NET type
	private static final String DOTNET_TYPE = "List<bool>^";
	//.NET csharp values
	private static final String DOTNET_WCF_TYPE = "List<bool>";
	private static final String DOTNET_CSHARP_BASE_TYPE = "bool";
	
	public UMLBooleanCollectionParaDotNetDescriptor(String umlName,
	                                 Direction direction, boolean isMandatory,
	                                 UMLOperationDotNetDescriptor parent) {
		m_umlName = umlName;
	  	m_direction = direction;
	  	m_parent = parent; 	  	
	  	m_isMandatory = isMandatory;
	  	  	
	  	parent.getParent().addDotNetStdClsRef("System::Collections::Generic::List");
		parent.getParent().addCppStdClsRef("vector");
		parent.getParent().addCppUserClsRef("PrimitiveMarshal");
	}
  

	@Override
	public String getCppType() {		
		return CPP_TYPE;
	}

	@Override
	public String getDotNetType() {
		return DOTNET_TYPE;
	}	
	
	@Override
	public String getDotNetWCFType() {
		return DOTNET_WCF_TYPE;
	}
	
	@Override
	public String getDefaultValue(String codeLang) {
		// For C#
		if (codeLang.equals("CS")) {
			return "new " + DOTNET_WCF_TYPE + "()";
		} 
		return "";
	}	
	
	@Override
	public String marshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::PrimitiveMarshal::Marshal(" 
				+ getParaDotNetName() + ", "
				+ getParaCppName() + ");\n");
		
		return sb.toString();
	}

	@Override
	public String unmarshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getParaDotNetName() + " = "
				+ "temenos::soa::common::PrimitiveMarshal::Unmarshal(" 
				+  getParaCppName() + ");\n");
		
		return sb.toString();
	}

	@Override
	public String getCardinality() {
		return CARDINALTIY;
	}

	@Override
	public String getComplexity() {
		return COMPLEXITY;
	}

	@Override
	public String getDotNetBaseType() {
		return DOTNET_CSHARP_BASE_TYPE;
	}
}
	
