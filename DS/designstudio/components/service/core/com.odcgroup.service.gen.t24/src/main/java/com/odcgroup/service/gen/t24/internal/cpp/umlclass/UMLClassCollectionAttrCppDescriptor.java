/*
 * this abstract class represents one member of UML class, which
 * is collection member. 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlclass;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
public abstract class UMLClassCollectionAttrCppDescriptor extends UMLClassAttrCppDescriptor {
		
	//get the parameter name when the collection item 
	//as function input parameter
	protected String getItemParaName() {
		return getParaName() + "Item";
	}
	
	//get the variable name when the collection item as
	//JBC VAR object
	protected String getItemJBCVarObjectName() {
		return getItemParaName()+ "Obj";
	}
	
	//define size variable to store the size of this collection member
	private String getSizeHelperVarName() {
		return "m_sizeOf" + getParaName();
	}
	
	//define the method name to get the size of this collection member
	protected String getSizeMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SizeOf" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		
		return sb.toString();
	}
	
	//define the method name to get one item of this collection member
	protected String getGetItemMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("GetItemOf" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		
		return sb.toString();
	}
	
	//define the method name to add one item into this collection member
	protected String getAppendItemMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("AppendItemInto" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		
		return sb.toString();
	}
	
	//define the method name to add one item into this collection member
	//at the given position
	protected String getAddItemMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("AddItemInto" 
				+ StringUtils.upperInitialCharacter(getUMLName())
				+"At");
		
		return sb.toString();
	}
	//remove one item at the given position
	protected String getRemoveMethodName() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("RemoveItemFrom"
				+ StringUtils.upperInitialCharacter(getUMLName())
				+ "At");
		
		return sb.toString();
	}
	//clear all the item in this collection member
	protected String getClearMethodName() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClearAllIn" 
				+ StringUtils.upperInitialCharacter(getUMLName()));
		return sb.toString();
	}
	//define the signature of the method 
	//to get the size of this collection member 
	private String getCppSizeMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(CppConstants.SIZE_TYPE + " ");
		if (inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getSizeMethodName());
		sb.append("(void) const");
		
		return sb.toString();
	}
  
	//define the signature of the method
	//to get one item of this collection member
	private String getCppGetItemMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getCppReturnBaseType() + " ");
		if (inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getGetItemMethodName());
		sb.append("(" + CppConstants.SIZE_TYPE + " index) const");
		return sb.toString();
	}

	//define the signature of the method
	//to add one item into this collection member
	private String getCppAppendItemMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("void ");
		if (inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getAppendItemMethodName());
		sb.append("(");
		sb.append(getCppParaBaseType() + " ");
		sb.append(getItemParaName());
		sb.append(")");
		
		return sb.toString();
	}
	
	//define the signature of the method
	//to add one item into this collection member at the given position
	private String getCppAddItemMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("void ");
		if (inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getAddItemMethodName());
		sb.append("(");
		sb.append(getCppParaBaseType() + " ");
		sb.append(getItemParaName() + ", ");
		sb.append(CppConstants.SIZE_TYPE +  " index)");
		
		return sb.toString();
	}
	
	//define the signature of the method to remove
	// one item at the given position
	private String getCppRemoveItemMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("void ");
		if(inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getRemoveMethodName());
		sb.append("(");
		sb.append(CppConstants.SIZE_TYPE +  " index)");
		
		return sb.toString();
	}
	//define the signature of the method
	//to clear all the items in this collection member
	private String getCppClearItemMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("void ");
		if (inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(this.getClearMethodName());
		sb.append("()");;
		
		return sb.toString();
	}
	
	//declare the c++ method to get the size of this collection member
	private String declareCppSizeMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL "
				+	getCppSizeMethodSignature(false)
				+	";");
		
		return sb.toString();
	}
	
	//define the c++ method to get the size of this collection member
	private String defineCppSizeMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()).append(
				getCppSizeMethodSignature(true)).append(" {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent()).append("return ").append(
				getVarName()).append(".size();\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent()).append("}");
		
		return sb.toString();
	}
	
	//declare the c++ method to get one item of this collection member
	private String declareCppGetItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL "
				+ getCppGetItemMethodSignature(false)
				+ ";");		
		
		return sb.toString();
	}
	
	//define the c++ method to get one itme of this collection member
	private String defineCppGetItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()).append(
				getCppGetItemMethodSignature(true)).append(" {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ CppConstants.SIZE_TYPE 
				+ " size = " + getSizeMethodName() + "()"
				+";\n\n");
		
		//check whether index is out of bound or not
		sb.append(formatter.currentIndent() + "if(index >= size) {\n");		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::ostringstream oss(std::ostringstream::out);\n");
		sb.append(formatter.currentIndent() 
				+ "oss << \"index\" << index << \" is out of range \" << size;\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_EXCEPTION_CLASS 
				+" ex(\"INDEX_OUT_OF_BOUND\", \"index is out of bound\", oss.str());\n");
		sb.append(formatter.currentIndent() 
				+ "ex.AppendContext(__FILE__, __LINE__);\n");
		sb.append(formatter.currentIndent() + "throw ex;\n");		
		formatter.outdent();		
		sb.append(formatter.currentIndent() + "}\n");
		
		
		sb.append(formatter.currentIndent() 
				+ "return " + getVarName() + ".at(index);\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}");
		
		return sb.toString();
	}
	
	//declare the c++ method to add one item into this collection member
	private String declareCppAppendItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL "
				+ getCppAppendItemMethodSignature(false)
		    + ";");
		
		return sb.toString();
	}
	
	//define the c++ method to add one item into this collection member
	private String defineCppAppendItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppAppendItemMethodSignature(true)
		    + " {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				      + getVarName() +".push_back(" 
				      + getItemParaName()
				      +");\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	
	//declare the c++ method to add one item into 
	//this collection member at the given position
	private String declareCppAddItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL "
				+ getCppAddItemMethodSignature(false)
		    + ";");
		
		return sb.toString();
	}
	
	//define the c++ method to add one item into 
	//this collection member at the given position
	private String defineCppAddItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppAddItemMethodSignature(true)
		    + " {\n");
		
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+ CppConstants.SIZE_TYPE 
				+ " size = " + getSizeMethodName() + "()"
				+";\n\n");
		
		//check whether index is out of bound or not
		sb.append(formatter.currentIndent() + "if(index > size) {\n");		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::ostringstream oss(std::ostringstream::out);\n");
		sb.append(formatter.currentIndent() 
				+ "oss << \"index\" << index << \" is out of range \" << size;\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_EXCEPTION_CLASS 
				+" ex(\"INDEX_OUT_OF_BOUND\", \"index is out of bound\", oss.str());\n");
		sb.append(formatter.currentIndent() 
				+ "ex.AppendContext(__FILE__, __LINE__);\n");
		sb.append(formatter.currentIndent() + "throw ex;\n");		
		formatter.outdent();		
		sb.append(formatter.currentIndent() + "}\n");
		
		sb.append(formatter.currentIndent() 
				      + getVarName() +".insert("
				      +getVarName() + ".begin() + index, "				       
				      + getItemParaName()
				      +");\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	//declare the c++ method to remove one item at the given position
	private String declareCppRemoveItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL "
				+ getCppRemoveItemMethodSignature(false)
		    + ";");
		
		return sb.toString();
	}
	
	//define the c++ method to remove one item at the given position
	private String defineCppRemoveItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppRemoveItemMethodSignature(true)
		    + " {\n");
		
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+ CppConstants.SIZE_TYPE 
				+ " size = " + getSizeMethodName() + "()"
				+";\n\n");
		
		//check whether index is out of bound or not
		sb.append(formatter.currentIndent() + "if((size == 0) || (index >= size)) {\n");		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::ostringstream oss(std::ostringstream::out);\n");
		sb.append(formatter.currentIndent() 
				+ "oss << \"index\" << index << \" is out of range \" << size;\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_EXCEPTION_CLASS 
				+" ex(\"INDEX_OUT_OF_BOUND\", \"index is out of bound\", oss.str());\n");
		sb.append(formatter.currentIndent() 
				+ "ex.AppendContext(__FILE__, __LINE__);\n");
		sb.append(formatter.currentIndent() + "throw ex;\n");		
		formatter.outdent();		
		sb.append(formatter.currentIndent() + "}\n");
		
		sb.append(formatter.currentIndent() 
				      + getVarName() +".erase("
				      +getVarName() + ".begin() + index);\n");      
				     
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	//declare the c++ method to add one item into this collection member
	private String declareCppClearItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL "
				+ getCppClearItemMethodSignature(false)
		    + ";");
		
		return sb.toString();
	}
	
	//define the c++ method to add one item into this collection member
	private String defineCppClearItemMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getCppClearItemMethodSignature(true)
		    + " {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				      + getVarName() +".clear();\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	@Override
	public String convertToString() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "oss << \"size of " + getVarName() + " : \""
				+ " << " + getSizeMethodName() + "() << \" | \";\n");
		
		sb.append(formatter.currentIndent()
				+ "if(" + getSizeMethodName() + "() > 0) {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ "oss << \"list of " + getVarName() + " : (\";\n");
		sb.append(formatter.currentIndent() 
				+ "for(" + CppConstants.SIZE_TYPE + " index = 0; index < " 
				+ getSizeMethodName() + "(); index++) \n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ "oss << " + getGetItemMethodName() + "(index) << \" | \";\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() 
				+ "oss << \")\";\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		return sb.toString();
	}
	
	@Override
	public String declareCppMemberOperationMethod() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(declareCppSizeMethod());
		sb.append("\n");
		sb.append(declareCppGetItemMethod());
		sb.append("\n");
		sb.append(declareCppAppendItemMethod());
		sb.append("\n");
		sb.append(declareCppAddItemMethod());
		sb.append("\n");
		sb.append(declareCppRemoveItemMethod());
		sb.append("\n");
		sb.append(declareCppClearItemMethod());
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public String defineCppMemberOperationMethod() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(defineCppSizeMethod());
		sb.append("\n");
		sb.append(defineCppGetItemMethod());
		sb.append("\n");
		sb.append(defineCppAppendItemMethod());
		sb.append("\n");		
		sb.append(defineCppAddItemMethod());
		sb.append("\n");
		sb.append(defineCppRemoveItemMethod());
		sb.append("\n");
		sb.append(defineCppClearItemMethod());
		sb.append("\n");
		return sb.toString();
	}	
	
	@Override
	public String storeToCppJBCVar() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();		
		
		sb.append(formatter.currentIndent() 
				+	"//store member " 
				+ getParaName() 
				+ " into the given JBC VAR\n");
		sb.append(formatter.currentIndent()
				+ "int " + getSizeHelperVarName()  
				+ " = (int)" + getParent().getParaName() 
				+ "." + getSizeMethodName() + "();\n");
		sb.append(formatter.currentIndent() 
				+  "if(" + getSizeHelperVarName() + " > 0) {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ getCppJBCObjectType() + " "
				+ getJBCObjectVarName() + "(session);\n\n");
		sb.append(formatter.currentIndent() 
				+ "for(int index = 0; index < "
				+ getSizeHelperVarName() + "; index++) {\n");
	  formatter.indent();
	  sb.append(formatter.currentIndent() 
	  		+ getCppJBCObjectType() + " "
	  		+ getItemJBCVarObjectName() + "(session);\n");
	  sb.append(formatter.currentIndent() 
	  		+ getCppBaseTypeName() + " "
	  		+ getItemParaName() + " = "
	  		+ getParent().getParaName() + "." 
	  		+ getGetItemMethodName() + "(index);\n");
	  sb.append(storeCppItemIntoJBCVar());	  
	  formatter.outdent();
	  sb.append(formatter.currentIndent()
	  		+ "}\n\n");	  
	  sb.append(formatter.currentIndent()
	  		+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
	  		+ "::LowerVar(session, " 
	  		+ getJBCObjectVarName() + ".Get());\n");
	  sb.append(formatter.currentIndent()
			+  CppConstants.SOA_JBC_VAR_HANDLER_CLASS
	  		+ "::InsertVarIntoVar(session, " 
	  		+ getParent().getJBCParaVarName() + ", "
	  		+ getIndexHelperVarName() + ", " 
	  		+ getJBCObjectVarName() +".Get());\n");		
		formatter.outdent();
		
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		return sb.toString();
	}
	
	@Override
	public String extractFromCppJBCVar() {
		
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+	"//extract member " 
				+ getParaName() 
				+ " from the given JBC VAR\n");
		
		sb.append(formatter.currentIndent()
				+"//clear the content of c++ member " 
				+getParaName()
				+"\n");
		sb.append(formatter.currentIndent()
				+ getParent().getParaName() + "." 
				+ getClearMethodName()
				+ "();\n\n");	
		
		sb.append(formatter.currentIndent() 
				+ getCppJBCObjectType() + " "
				+ getJBCObjectVarName() + "(session);\n");
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ExtractVarFromVar(session, " 
				+ getJBCObjectVarName() + ".Get(), "
				+ getIndexHelperVarName() + ", "
				+ getParent().getJBCParaVarName() + ");\n\n");
		
		sb.append(formatter.currentIndent()
				+ "int " + getSizeHelperVarName() 
				+ " = " 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::Count(session, " 
				+ getJBCObjectVarName() + ".Get(), "
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::MV);\n");
		sb.append(formatter.currentIndent()
				+ "if(" + getSizeHelperVarName() + " > 0) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::RaiseVar(session, " 
				+ getJBCObjectVarName() + ".Get());\n\n");
		sb.append(formatter.currentIndent() 
				+ "for(int index = 0; index < " 
				+ getSizeHelperVarName() + "; index++) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ getCppJBCObjectType() 
				+ " " + getItemJBCVarObjectName() + "(session);\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ExtractVarFromVar(session, " 
				+ getItemJBCVarObjectName() + ".Get(), "
				+ "index + 1, "
				+ getJBCObjectVarName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ getCppBaseTypeName() + " " 
				+ getItemParaName() + ";\n");
	  sb.append(extractCppItemFromJBCVar());
		formatter.outdent();		
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		return sb.toString();
		
	}
	
	//get the c++ type when the item of this collection member
	//as the input parameter of c/c++ function
	protected abstract String getCppParaBaseType();
	//get the c++ return type when c/c++ function returns
	//one item of this collection member
	protected abstract String getCppReturnBaseType();
	//get the c++ type name of the item of this collection member
	protected abstract String getCppBaseTypeName(); 	
	//store c++ item value into JBC VAR struct
	public abstract String storeCppItemIntoJBCVar();
	//extract c++ item value from JBC VAR struct
	public abstract String extractCppItemFromJBCVar();	
}
