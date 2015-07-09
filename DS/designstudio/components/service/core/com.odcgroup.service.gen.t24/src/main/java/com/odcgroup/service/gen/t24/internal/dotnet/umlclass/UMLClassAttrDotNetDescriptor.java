/*
 * this class is abstract class to represent each member
 * defined in UML class.
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public abstract class UMLClassAttrDotNetDescriptor {	
	  
	//uml name of this member defined in UML class
	protected String m_umlName = null;
	
	//index of this member in the UML class
	protected int m_index = 0;
	
	//the reference to UMLClass parent
	protected UMLClassDotNetDescriptor m_parent = null;	
	
	public UMLClassDotNetDescriptor getParent() {
		return m_parent;
	}
	
	public String getUMLName() {
		return m_umlName;
	}
	
	//get the index of this member in the UML class
	public int getIndex() {
		return m_index;
	}
		
	//get c++ variable name 
	public String getCppVarName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}
	
	//get .NET member variable name in managed business object
	public String getDotNetVarName() {
		return "m_managed" 
			+ StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//get .NET property name in managed business object
	public String getDotNetPropertyName() {
		return StringUtils.upperInitialCharacter(getUMLName());
	}
	//get .NET parameter name in get and set method in managed business object
	public String getDotNetParaVarName() {
		return "managed" 
			+ StringUtils.upperInitialCharacter(getUMLName());
	}	
	
	public String getCapitalisedName() {
		return m_umlName.toUpperCase();
	}
	//declare .NET property for this member
	public String declareDotNetProperty() {
		Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	 
  	sb.append(formatter.currentIndent()
  			+"property " + getDotNetType() 
  			+ " " + getDotNetPropertyName() + " {\n");
  	
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ "void set(" + getDotNetType() 
  			+ " " + getDotNetParaVarName()
  			+ ");\n");
  	sb.append(formatter.currentIndent()
  			+ getDotNetType() 
  			+ " get();\n");
  	formatter.outdent();
  	
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	return sb.toString();
	}
	
	//define .NET proeprty for this member 
	public String defineDotNetProperty() {
		Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	  	
  	sb.append(formatter.currentIndent()
  			+ "void " + getParent().getDotNetClassName()
  			+ "::" + this.getDotNetPropertyName() 
  			+ "::set(" + getDotNetType() 
  			+ " " + getDotNetParaVarName()
  			+ ") {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ getDotNetVarName() 
  			+ " = " + getDotNetParaVarName() + ";\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetType() + " " 
  			+ getParent().getDotNetClassName()
  			+ "::" + getDotNetPropertyName() 
  			+ "::get() {\n");
  			
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ "return " + getDotNetVarName() + ";\n");  		
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	
  	
  	return sb.toString();
	}
	//declare .NET member variable
	public String declareDotNetMember() {
		Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetType() + " " + getDotNetVarName() + ";\n");
  	
  	return sb.toString();
	}	
	
	// UML Properties
	public abstract String getCardinality();
	public abstract String getComplexity();
	
	//get c++ type
	public abstract String getCppType();
	// get the base type of the member
	public abstract String getDotNetBaseType();
	// get the base type of the member
	public abstract String getDotNetCSBaseType();
	//get .NET type
	public abstract String getDotNetType();	
	// get .NET C# Type
	public abstract String getDotNetCSType();
	//get the default value to initialize this .net member
	public abstract String getDotNetDefaultValue();
	//get the default value to initialize this .net c# member
	public abstract String getDotNetCSDefaultValue();
	
	//marshal data class member from managed to unmanaged
	public abstract String marshal();
	//unmarshal data class member from unmanaged to managed
	public abstract String unmarshal();
	
}
