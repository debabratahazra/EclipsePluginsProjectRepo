/*
 * this class represents one member of UML class, which
 * is one singleton member. 
 */

package com.odcgroup.service.gen.t24.internal.cpp.umlclass;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
public abstract class UMLClassSingleAttrCppDescriptor extends UMLClassAttrCppDescriptor {
  
	//the c++ method name to get the singleton member
	public String getCppSetMethodName() {
		return "Set" 
			+	StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//the c++ method name to set the singleton member
	public String getCppGetMethodName() {
		return "Get" 
			+ StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//the c++ method signature to set the singleton member from business object
	private String getCppSetMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("void ");
		if(inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getCppSetMethodName());
		sb.append("(");
		sb.append(getCppParaType() + " ");
		sb.append(getParaName());
		sb.append(")");
		
		return sb.toString();		
	}
	
	//the c++ method signature to get the singleton member from business object
	private String getCppGetMethodSignature(boolean inCppFile) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getCppReturnType() + " ");
		if(inCppFile)
			sb.append(getParent().getClassName() + "::");
		sb.append(getCppGetMethodName());
		sb.append("(void) const");		
		
		return sb.toString();	
	}
	
	//declare the c++ method to get the singleton member from business object
	private String declareCppGetMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL " 
				+ getCppGetMethodSignature(false)
				+ ";\n");				
		
		return sb.toString();
	}
	
	//define the c++ method to get the singleton member from business object
	private String defineCppGetMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()).append(
				getCppGetMethodSignature(true)).append(" {\n");
		formatter.indent();
		
		sb.append(formatter.currentIndent()).append("return ").append(
				getVarName()).append(";\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent()).append("}\n");
		
		return sb.toString();
	}
	
	//declare the c++ method to set the singleton member of business object
	private String declareCppSetMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
        + "DLLDECL "
        +	getCppSetMethodSignature(false)
        + ";");
		
		return sb.toString();
	}
	
	//define the c++ method to set the singleton member of business object
	private String defineCppSetMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()).append(
				getCppSetMethodSignature(true)).append(" {\n");
				
		formatter.indent();
		sb.append(formatter.currentIndent()).append(
				getVarName()).append(" = ").append(
						getParaName()).append(";\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	
	@Override
	public String convertToString() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "oss << \"" + getVarName() + " : \" << " 
				+ getCppGetMethodName() + "() << \" | \";\n");
		
		return sb.toString();
	}
  
  @Override
	public String declareCppMemberOperationMethod() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(declareCppGetMethod());
		sb.append(declareCppSetMethod());
		sb.append("\n");
		
		return sb.toString();
	}

	@Override
	public String defineCppMemberOperationMethod() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(defineCppGetMethod());
		sb.append("\n");
		sb.append(defineCppSetMethod());
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
				+ getCppJBCObjectType() + " "
				+ getJBCObjectVarName() + "(session);\n");
		sb.append(formatter.currentIndent() 
				+ getCppTypeName() + " " 
				+ getVarName() + " = "
				+ getParent().getParaName() 
				+ "." 
				+ getCppGetMethodName() + "();\n");
		sb.append(formatter.currentIndent()
				+ cppVarToJBCVarMethodName() + "(session, " 
				+ getVarName() + ", "
				+ getJBCObjectVarName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::InsertVarIntoVar(session, "  
				+ getParent().getJBCParaVarName() + ", "
				+ getIndexHelperVarName() + ", " 
		    + getJBCObjectVarName() + ".Get());\n\n");		
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
				+ getCppJBCObjectType() + " "
				+ getJBCObjectVarName() + "(session);\n");
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::ExtractVarFromVar(session, "				
				+ getJBCObjectVarName() + ".Get(), " 
				+ getIndexHelperVarName() + ", "
				+ getParent().getJBCParaVarName() + ");\n");		
		sb.append(formatter.currentIndent() 
				+ getCppTypeName() + " " 
				+ getVarName() + ";\n"); 
		sb.append(formatter.currentIndent()
				+ JBCVarToCppVarMethodName()+ "(session, "
				+ getJBCObjectVarName() + ".Get(), "
				+ getVarName() + ");\n");	
		sb.append(formatter.currentIndent()
				+ getParent().getParaName() + "."
				+ getCppSetMethodName() + "("
				+ getVarName() + ");\n\n");	
		
		return sb.toString();
		
	}
	
	//c++ method name to convert singleton member to JBC VAR struct
	public abstract String cppVarToJBCVarMethodName();
	//c++ method name to convert JBC VAR struct to singleton member
	public abstract String JBCVarToCppVarMethodName();

}
