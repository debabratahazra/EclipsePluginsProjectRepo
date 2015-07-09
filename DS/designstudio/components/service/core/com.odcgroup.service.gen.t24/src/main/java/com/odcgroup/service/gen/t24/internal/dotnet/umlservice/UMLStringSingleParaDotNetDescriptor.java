/*
 * This class represents one UML parameter, 
 * which is single UML String.
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLStringSingleParaDotNetDescriptor 
	extends UMLParaDotNetDescriptor {
	// UML Properties
	private static final String CARDINALTIY = "SINGLE";
	private static final String COMPLEXITY = "PRIMITIVE";
	//C++ TYPE
	private static final String CPP_TYPE = "std::string";
	//.NET type
	private static final String DOTNET_TYPE = "String^";
	//.NET WCF type
	private static final String DOTNET_WCF_TYPE = "String";
	
  public UMLStringSingleParaDotNetDescriptor(String umlName,
  		                               Direction direction, 
  		                               boolean isMandatory, 		                                 
  		                               UMLOperationDotNetDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;  	 	
  	m_isMandatory = isMandatory;
  	m_parent = parent; 	
  	parent.getParent().addDotNetStdClsRef("System::String");
  	parent.getParent().addCppStdClsRef("string");
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
			return "";
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
		return getDotNetWCFType();
	}
}
