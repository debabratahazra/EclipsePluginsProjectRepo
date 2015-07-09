/*
 * this abstract class represents one parameter of UML operation, which
 * is collection parameter. 
 */

package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;

public abstract class UMLCollectionParaCppDescriptor 
	extends UMLParaCppDescriptor{
	
	//the variable name to store the size of 
	//this collection parameter which is C++ type parameter
	public String getCppParaSizeVarName() {
		return "m_sizeOf" + getCppParaName();
	}
	
	//the JBC variable name to store the size of
	//this collection parameter which is JBC VAR struct
	public String getJBCParaSizeVarName() {
		return "m_sizeOf" + getJBCParaName();
	}
	
	//the c++ item variable name to store item 
	//of this collection parameter 
	public String getCppItemVarName() {
		return getCppParaName() + "Item";
	}
	
	//the JBC item VAR object variable name to
	//store the item of this collection parameter
	public String getJBCItemVarName() {
		return getCppParaName() + "ItemObj";
	}	
	
	@Override
	public String getParaCppType() {
		switch(getDirection()) {
		case IN:
			return "const std::vector<" + getCppBaseType() + " >&";
		case OUT:
		case INOUT:
			return "std::vector<" + getCppBaseType() + " >&";
		case RETURN:
			return "std::vector<" + getCppBaseType() + " >";
		default:
			return "std::vector<" + getCppBaseType() + " >";
		}		
	}
	
	@Override
	public String convertCppVarToJBCVar(boolean varIsRawPointer) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();		
		sb.append(formatter.currentIndent()
				+ "//convert C++ variable " + getCppParaName() 
				+ " To JBC VAR " + getJBCParaName() + "\n");
		
		sb.append(formatter.currentIndent()
				+ "int " + getCppParaSizeVarName() 
				+ " = (int)" + getCppParaName() + ".size();\n");
		sb.append(formatter.currentIndent()
				+ "for(int index = 0; index < " 
				+ getCppParaSizeVarName() + "; index++) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+  CppConstants.SOA_VAROBJECT_CLASS
				+ " " + getJBCItemVarName() + "(session);\n");
		sb.append(formatter.currentIndent() 
				+ getCppBaseType() + " " + getCppItemVarName() + " = \n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ getCppParaName() + ".at(index);\n");
		formatter.outdent();
			
		sb.append(convertCppItemVarToJBCVar() + "\n");		
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::InsertVarIntoVar(session, ");
		if(varIsRawPointer)
			sb.append(getJBCParaName() + ", ");
		else
			sb.append(getJBCParaName()+ ".Get(), ");
		sb.append("(index + 1), " 
				+ getJBCItemVarName() + ".Get());\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() + "}\n");		
		
		return sb.toString();
	}
	
	@Override
	public String convertJBCVarToCppVar(boolean varIsRawPointer) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(formatter.currentIndent()
				+ "//convert JBC VAR " + getJBCParaName() 
				+ " To C++ variable " + getCppParaName() + "\n");
		
		sb.append(formatter.currentIndent()
				+ "//clear the content of c++ variable " 
				+ getCppParaName() + "\n");
		sb.append(formatter.currentIndent()
				+ getCppParaName() + ".clear();\n\n");
		
		sb.append(formatter.currentIndent()
				+ "int " + getJBCParaSizeVarName() 
				+ " = " 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::Count(session, ");
		if(varIsRawPointer)
			sb.append(getJBCParaName() + ", ");
		else
			sb.append(getJBCParaName() + ".Get(), ");
		sb.append( CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::AM);\n");
		sb.append(formatter.currentIndent() 
				+ "for(int index = 0; index < " 
				+ getJBCParaSizeVarName() + "; index++) {\n");
		formatter.indent();			
		sb.append(formatter.currentIndent() 
				+  CppConstants.SOA_VAROBJECT_CLASS 
				+ " " + getJBCItemVarName()
				+ "(session);\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ExtractVarFromVar(session, " 
				+ getJBCItemVarName() + ".Get(), index + 1, ");
		if(varIsRawPointer)
			sb.append(getJBCParaName());
		else
			sb.append(getJBCParaName() + ".Get()");
		sb.append(");\n");
		sb.append(formatter.currentIndent() 
				+ getCppBaseType() + " " 
				+ getCppItemVarName() + ";\n");
		sb.append(convertJBCItemVarIntoCppItemVar() + "\n");
		sb.append(formatter.currentIndent()
				+ getCppParaName() + ".push_back(" 
				+ getCppItemVarName() + ");\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");		
		
		return sb.toString();
	}
	
	//get the c++ type of the item of this UML parameter
	public abstract String getCppBaseType();
	//convert the item of this collection parameter 
	//from c++ to JBC VAR struct if this parameter is 
	//INOUT or OUT parameter 
	public abstract String convertCppItemVarToJBCVar();
	//convert the item of this collection parameter 
	//from JBC VAR struct to c++ if this parameter is
	//IN or INOUT parameter
	public abstract String convertJBCItemVarIntoCppItemVar();
	
}
