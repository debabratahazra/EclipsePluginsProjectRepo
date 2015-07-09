/*
 * this class represents one parameter of UML operation, which
 * is complex type parameter. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLComplexSingleParaDotNetDescriptor 
	extends UMLParaDotNetDescriptor {

	// UML Properties
	private static final String CARDINALTIY = "SINGLE";
	private static final String COMPLEXITY = "COMPLEX";
	
	private String m_cppType = null;
	private String m_dotNetType = null;
	private String m_dotNetWCFType = null;
	private String m_umlComplexType = null;

  public UMLComplexSingleParaDotNetDescriptor(String umlName,
  		                                Direction direction,
  		                                boolean isMandatory,
  		                                String umlComplexTypeName,
  		                                UMLOperationDotNetDescriptor parent) {
  	m_umlName = umlName;
  	m_direction = direction;
  	m_isMandatory = isMandatory;
  	m_umlComplexType = umlComplexTypeName;
  	m_cppType  = StringUtils.upperInitialCharacter(umlComplexTypeName);
  
  	m_dotNetType =
  		"Managed" 
  		+ StringUtils.upperInitialCharacter(umlComplexTypeName)
  		+ "^";
  	m_dotNetWCFType =
  		"Managed" 
  		+ StringUtils.upperInitialCharacter(umlComplexTypeName);
  	m_parent = parent;
  	
  	parent.getParent().addDotNetUserClsRef("temenos::soa::" 
				+ getParent().getParent().getNamespace() 
				+ "::Managed"
				+ StringUtils.upperInitialCharacter(umlComplexTypeName));
  	  	
		parent.getParent().addCppUserClsRef(
				StringUtils.upperInitialCharacter(umlComplexTypeName));
		parent.getParent().addCppUserClsRef( 
				StringUtils.upperInitialCharacter(umlComplexTypeName) + "Marshal");
  }

	@Override
	public String getCppType() {
		return m_cppType;
	}

	@Override
	public String getDotNetType() {		
		return m_dotNetType;		
	}	

	@Override
	public String getDotNetWCFType() {		
		return m_dotNetWCFType;		
	}	

	@Override
	public String getDefaultValue(String codeLang) {
		// For C#
		if (codeLang.equals("CS")) {
			return "new " + m_dotNetWCFType + "()";
		} 
		return "";
	}	
	
	@Override
	public String marshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ StringUtils.upperInitialCharacter(m_umlComplexType) + "Marshal::Marshal(" 
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
				+ StringUtils.upperInitialCharacter(m_umlComplexType) + "Marshal::Unmarshal(" 
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
