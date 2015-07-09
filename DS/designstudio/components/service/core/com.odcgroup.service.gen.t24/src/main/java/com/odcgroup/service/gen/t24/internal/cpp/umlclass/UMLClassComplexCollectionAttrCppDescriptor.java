package com.odcgroup.service.gen.t24.internal.cpp.umlclass;
/*
 * this class represents one member of UML class, which
 * is collection of UML complex type. 
 */
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLClassComplexCollectionAttrCppDescriptor extends
		UMLClassCollectionAttrCppDescriptor {
	
	//c++ type of the item of this collection member
	private String m_cppBaseType = null;
	
	//c++ default value to initialize this collection member
	//in c++ default constructor
	private static final String CPP_DEFAULT_VALUE = "";
		
	public UMLClassComplexCollectionAttrCppDescriptor(String umlName,
			                                   String umlComplexTypeName,
			                                   int index,
			                                   UMLClassCppDescriptor parent) {
		m_umlName = umlName;
		m_cppBaseType = umlComplexTypeName;		
		m_index = index;
		m_parent = parent;
		
		parent.addStdClsRefIntoBOHeader("vector");
		parent.addStdClsRefIntoBOHeader("cstdlib");
		
		parent.addTemenosClsIntoBOCpp("SOAException");
		parent.addUserClsRefIntoBOHeader(
				StringUtils.upperInitialCharacter(m_cppBaseType));
		parent.addUserClsIntoBOHandlerCpp(
				StringUtils.upperInitialCharacter(umlComplexTypeName)
						+ "Handler");			
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
				+ "for(" + CppConstants.SIZE_TYPE 
				+ " index = 0; index < " 
				+ getSizeMethodName() + "(); index++) {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ getCppReturnBaseType() + " "
				+ getItemParaName() 
				+ " = " + getGetItemMethodName() + "(index);\n");
		String stringVar = "outputStringFor" + getItemParaName();
		sb.append(formatter.currentIndent()
				+ "std::string " + stringVar 
				+ " = "
				+ getItemParaName() +".ToString();\n");
				
		sb.append(formatter.currentIndent()
				+ "oss << \" ( \" << "
				+ stringVar + " << \" ) | \";\n");
		sb.append(formatter.currentIndent() + "}\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() 
				+ "oss << \")\";\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		return sb.toString();
	}
	
	@Override
	protected String getCppParaBaseType() {
		return "const " + m_cppBaseType + "&";
	}

	@Override
	protected String getCppReturnBaseType() {		
		return m_cppBaseType;
	}
	
	@Override
	protected String getCppBaseTypeName(){
  	return m_cppBaseType;
	}
	private String getCppBaseTypeHandlerClassName() {
		return m_cppBaseType + "Handler";
	}
	@Override
	public String getCppDefaultValue() {		
		return CPP_DEFAULT_VALUE;
	}

	@Override
	public String getCppParaType() {
		return "const std::vector<" + m_cppBaseType + " > &";
	}

	@Override
	public String getCppReturnType() {
		return "std::vector<" + m_cppBaseType + " >";
	}

	@Override
	public String getCppTypeName() {
		return "std::vector<" + m_cppBaseType + " >";
	}
	
	@Override
	public String extractCppItemFromJBCVar() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();		
			
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::RaiseVar(session, "
				+ getItemJBCVarObjectName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ getCppBaseTypeHandlerClassName()
				+ "::FromVAR(session, " 
				+ getItemJBCVarObjectName()+ ".Get(), "
				+ getItemParaName() + ");\n");			
		sb.append(formatter.currentIndent() 
				+ getParent().getParaName() + "." 
				+ getAppendItemMethodName() + "("
				+ getItemParaName() + ");\n");
		
		return sb.toString();
	}
	
	@Override
	public String storeCppItemIntoJBCVar() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();
		
		
		sb.append(formatter.currentIndent()
				+ getCppBaseTypeHandlerClassName() 
				+ "::ToVAR(session, "
				+ getItemParaName() + ", "
				+ getItemJBCVarObjectName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::LowerVar(session, " 
				+ getItemJBCVarObjectName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
				+ "::InsertVarIntoVar(session, "
				+ getJBCObjectVarName() + ".Get(), "
				+ "index + 1, " 
				+ getItemJBCVarObjectName() + ".Get());\n");
		
		return sb.toString();
	}
}
