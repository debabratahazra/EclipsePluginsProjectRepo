/*
 * this abstract class represents one member of UML class, which
 * is collection member. 
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;


public abstract class UMLClassCollectionAttrDotNetDescriptor extends UMLClassAttrDotNetDescriptor {
	
	//variable name for the size of this collection member defined in c++ business object
	protected String getCppSizeVarName() {
		return "sizeOf" + StringUtils.upperInitialCharacter(getUMLName());
	}	
	
	//variable name for the item of this collection member defined in c++ business object  
	protected String getCppItemVarName() {
		return "itemOf" + StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//variable name of the item of this collection member defined in .NET business object
	protected String getDotNetItemVarName() {
		return "itemOfManaged" + StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//method name to get the size of this collection member defined in c++ business object
	protected String getCppSizeMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SizeOf" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		
		return sb.toString();
	}
	
	//method name to get one item of this collection member defined in c++ business object
	protected String getCppGetItemMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("GetItemOf" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		
		return sb.toString();
	}
	
	//method name to add one item into this collection member defined in c++ business object
	protected String getCppAppendItemMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("AppendItemInto" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		
		return sb.toString();
	}
	
	@Override
	public String marshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "if(nullptr != " 
				+ getParent().getDotNetParaName() + "->" + getDotNetPropertyName()
				+ ") {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "for each(" + getDotNetBaseType() + " " + getDotNetItemVarName() 
				+ " in " 
				+ getParent().getDotNetParaName() + "->" + getDotNetPropertyName()
				+ ") {\n");
		
		formatter.indent();
		sb.append(marshalItem());		
		sb.append(formatter.currentIndent()
				+ "}\n");		
		formatter.outdent();
		
		sb.append(formatter.currentIndent()
				+ "}\n");
				
				 
		return sb.toString();
	}

	@Override
	public String unmarshal() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "if(nullptr == " 
				+ getParent().getDotNetParaName() + "->" 
				+ getDotNetPropertyName() + ")\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ getParent().getDotNetParaName() + "->" 
				+ getDotNetPropertyName() 
				+ " = gcnew List<" + getDotNetBaseType() + ">();\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent()
				+ "int " + getCppSizeVarName() 
				+ " = (int)" 
				+ getParent().getCppParaName() 
				+ "." + getCppSizeMethodName() + "();\n");
		sb.append(formatter.currentIndent()
				+ "for(int index = 0; index < " + getCppSizeVarName() + "; index++) {\n");
		formatter.indent();
		
		sb.append(unmarshalItem());
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
				
		return sb.toString();
	}
	
	//get c++ base type
	protected abstract String getCppBaseType();
	
	//marshal collection item from .NET to c++
	protected abstract String marshalItem();
	//unmarshal collection item from c++ to .NET
	protected abstract String unmarshalItem();
	
}
