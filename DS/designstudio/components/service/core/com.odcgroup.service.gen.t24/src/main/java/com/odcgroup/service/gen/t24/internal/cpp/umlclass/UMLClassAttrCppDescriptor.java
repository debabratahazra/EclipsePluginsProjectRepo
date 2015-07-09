/*
 * this class is abstract class to represent each member
 * defined in UML class.
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlclass;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
public abstract class UMLClassAttrCppDescriptor {	
	//JBC VAR struct pointer
	private static final String CPP_JBC_VAR_RAW_TYPE = "VAR *";
  
	//uml name of this member defined in UML class
	protected String m_umlName = null;
	
	//index of this member in the UML class
	protected int m_index = 0;
	
	//the reference to UMLClass parent
	protected UMLClassCppDescriptor m_parent = null;	
	
	public UMLClassCppDescriptor getParent() {
		return m_parent;
	}
	
	public String getUMLName() {
		return m_umlName;
	}
	
	//get JBC VAR c++ object type
	public String getCppJBCObjectType() {
		return CppConstants.SOA_VAROBJECT_CLASS;
	}
	
	//get JBC VAR struct pointer
	public String getCppJBCRawType() {
		return CPP_JBC_VAR_RAW_TYPE;
	}	
	
	//define member variable name in business object class
	public String getVarName() {
		return "m_" 
			+ StringUtils.lowerInitialCharacter(getUMLName());
	}
	
	//define the name of the input parameter in the SET method 
	//to set this member
	public String getParaName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}
	
	//define the name of this member as JBC VAR struct variable
	public String getJBCRawVarName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}
	
	//define the name of this member as JBC VAR c++ object variable
	public String getJBCObjectVarName() {
		return StringUtils.lowerInitialCharacter(getUMLName()) 
			+ "Obj";
	}
	
	//get the index of this member in the UML class
	public int getIndex() {
		return m_index;
	}
	
	//get the index helper variable name
	public String getIndexHelperVarName() {
		return getVarName() + "Index";
	}
	
	//declare this member in c++ business object .h file
	public String declareCppMemberInBO() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ getCppTypeName()
				+ " " 
				+ getVarName()
				+ ";");
		
		return sb.toString();
	}
	
	//declare index helper variable in business object handler .h file
	public String declareCppIndexHelperVarInBOHandler() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(formatter.currentIndent() 
				+ "//index of member " + getUMLName() + " in the corrpsonding JBC VAR\n");
		sb.append(formatter.currentIndent() 
				+ "static const int " 
				+ getIndexHelperVarName()
				+ ";");
		
		return sb.toString();
	}
	
	//define index helper variable in business object handler .cpp file
	public String defineCppIndexHelperVarInBOHandler() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "const int "
				+ getParent().getHandlerClassName() + "::"
				+ getIndexHelperVarName()
				+ " = " + getIndex() + ";");
		
		return sb.toString();
	}
	
	//get c++ return type when c/c++ function returns this member
	public abstract String getCppReturnType();
	//get c++ type when this member is as one input parameter of c/c++ function	
	public abstract String getCppParaType();
	//get c++ type name of this member 
	public abstract String getCppTypeName();
	//declare the methods in business object to operate this member 
	public abstract String declareCppMemberOperationMethod();
	//define the methods in business object to operate this member
	public abstract String defineCppMemberOperationMethod();
	//store c++ member to JBC VAR struct
	public abstract String storeToCppJBCVar();
	//extract value from JBC VAR struct and store it to this c++ member
	public abstract String extractFromCppJBCVar();
	//represent the value of this c++ member with string 
	public abstract String convertToString();
	//get the default value to initialize this c++ member
	public abstract String getCppDefaultValue();	
}
