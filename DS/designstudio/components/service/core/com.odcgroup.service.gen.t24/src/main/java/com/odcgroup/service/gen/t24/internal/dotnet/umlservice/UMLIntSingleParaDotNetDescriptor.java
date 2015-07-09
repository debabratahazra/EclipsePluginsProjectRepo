/*
 * this abstract class represents one parameter of UML operation, which
 * is UML Integer. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public class UMLIntSingleParaDotNetDescriptor 
	extends UMLParaDotNetDescriptor {
	// UML Properties
	private static final String CARDINALTIY = "SINGLE";
	private static final String COMPLEXITY = "PRIMITIVE";
	//C++ TYPE
	private static final String CPP_TYPE = "int";
	//.NET type
	private static final String DOTNET_TYPE = "int";
	
  public UMLIntSingleParaDotNetDescriptor(String umlName,
  		                            Direction direction,
  		                            boolean isMandatory,
  		                            UMLOperationDotNetDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;  	
  	m_isMandatory = isMandatory;
  	m_parent = parent;
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
		return DOTNET_TYPE;		// They the same so keep it as is
	}
	
	@Override
	public String getDefaultValue(String codeLang) {
		// For C#
		if (codeLang.equals("CS")) {
			return "0";
		} 
		return "";
	}	
	
	@Override
	public String marshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getParaCppName() + " = " 
				+ getParaDotNetName() + ";\n");		
		
		return sb.toString();
	}

	@Override
	public String unmarshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getParaDotNetName() + " = "
				+ getParaCppName() + ";\n");
		
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
		return getDotNetType();
	}
}
