/*
 * this root abstract class represents one parameter of UML operation. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public abstract class UMLParaDotNetDescriptor {  
	//uml parameter name specified in UML operation
	protected String m_umlName = null;
	
	//specify this parameter is IN, INOUT, OUT, or RETURN parameter
	protected Direction m_direction = null;
	
	// specifiy if this parameters is mandatory
	protected boolean m_isMandatory = false;
	
	//reference to UML operation represented by UMLOperation object
	protected UMLOperationDotNetDescriptor m_parent = null;
		
	public String getUMLName() {
		return m_umlName;
	}
	
	public UMLOperationDotNetDescriptor getParent() {
		return m_parent;
	}
	
	public Direction getDirection() {
		return m_direction;
	}
	
	public boolean isMandatory() {
		return m_isMandatory;
	}
	
	//.NET parameter type
	public String getParaDotNetType() {
		switch(getDirection()) {
		case OUT:
		case INOUT:
			return getDotNetType() + "%";
		case IN:		
		case RETURN:			
		default:
			return getDotNetType();
		}
	}
	
	//.NET parameter name
	public String getParaDotNetName() {
		return "managed" 
			+ StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//C++ parameter NAME 
	public String getParaCppName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}	
	
	//declare corresponding c++ parameter for each .NET parameter
	public String declareCppPara() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppType() + " " + getParaCppName() + ";\n");
		
		return sb.toString();
	}

	// UML Properties
	public abstract String getCardinality();
	public abstract String getComplexity();
	
	//c++ type 
	public abstract String getCppType();
	//.NET type
	public abstract String getDotNetType();
	//.NET csharp type
	public abstract String getDotNetWCFType();
	public abstract String getDotNetBaseType();
	
	public abstract String getDefaultValue(String codeLang);
	
	//marshal parameter from .NET to c++
	public abstract String marshal();
	//unmarshal parameter from c++ to .NET
	public abstract String unmarshal();	
	
}
