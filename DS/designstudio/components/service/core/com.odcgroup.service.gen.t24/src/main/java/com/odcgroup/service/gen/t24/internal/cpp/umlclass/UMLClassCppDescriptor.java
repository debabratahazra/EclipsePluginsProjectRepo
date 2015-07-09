/*
 * This class contains the information how to generate
 * business object and business object handler for one
 * user defined UML complex type, called UML class.
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlclass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.UMLElementCppFactory;
import com.odcgroup.service.gen.t24.internal.cpp.umlservice.UMLServiceCppDescriptor;
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLClassCppDescriptor {
	//uml class name defined in UML model
	private String m_umlName = null;
	
	//member list of UML class
	private ArrayList<UMLClassAttrCppDescriptor> attrDescriptorList = null;
	
	//specific class reference list in business object .h file 
	private Set<String> userClsRefListInBOHeader = null;
	//temenos class reference list in business object .h file
	private Set<String> temenosClsRefListInBOHeader = null;
	//c++ standard class reference list in business object .h file
	private Set<String> stdClsRefListInBOHeader = null;
	//specific class reference list in business object .cpp file
	private Set<String> userClsRefListInBOCpp = null;
	//temenos class reference list in business object .cpp file
	private Set<String> temenosClsRefListInBOCpp = null;
	//c++ standard class reference list in business object .cpp file
	private Set<String> stdClsRefListInBOCpp = null;
	
	
	//specific class reference list in business object handler .h file
	private Set<String> userClsRefListInBOHandlerHeader = null;
	//temenos class reference list in business object handler .h file
	private Set<String> temenosClsRefListInBOHandlerHeader = null;
	//standard class reference list in business object handler .h file
	private Set<String> stdClsRefListInBOHandlerHeader = null;
	//specific class reference list in business object handler .cpp file
	private Set<String> userClsRefListInBOHandlerCpp = null;
	//temenos class reference list in business object handler .cpp file
	private Set<String> temenosClsRefListInBOHandlerCpp = null;
	//c++ standard class reference list in business object handler .cpp file
	private Set<String> stdClsRefListInBOHandlerCpp = null;
		
	private UMLServiceCppDescriptor m_parent = null;
	public UMLClassCppDescriptor(ClassDefDescriptor classDefDesc,
									UMLServiceCppDescriptor parent) {
	  m_umlName = classDefDesc.getName();
	  attrDescriptorList = new ArrayList<UMLClassAttrCppDescriptor>();
	  m_parent = parent;
	  
	  userClsRefListInBOHeader = new HashSet<String>();
		temenosClsRefListInBOHeader = new HashSet<String>();
		stdClsRefListInBOHeader = new HashSet<String>();		
		userClsRefListInBOCpp = new HashSet<String>();
		temenosClsRefListInBOCpp = new HashSet<String>();
		stdClsRefListInBOCpp = new HashSet<String>();
			
		
		userClsRefListInBOHandlerHeader = new HashSet<String>();		
		temenosClsRefListInBOHandlerHeader = new HashSet<String>();
		stdClsRefListInBOHandlerHeader = new HashSet<String>();
		userClsRefListInBOHandlerCpp = new HashSet<String>();
		
		temenosClsRefListInBOHandlerCpp = new HashSet<String>();
				
		stdClsRefListInBOHandlerCpp = new HashSet<String>();	
		
		initClassAttr(classDefDesc);	  
	}
	
	//construct UMLClassMemeber object list for members 
	//defined in the UML class
	private void initClassAttr(ClassDefDescriptor classDefDesc) {
		if(classDefDesc != null) {
			List<AttributeDescriptor> attrs = classDefDesc.getAttributes();
			Iterator<AttributeDescriptor> it = attrs.iterator();
			
			//index of first member in JBC VAR will be 1, not 0 
			int index = 1;
			while(it.hasNext()) {
				AttributeDescriptor attr = it.next();
				
				UMLClassAttrCppDescriptor member = 
					UMLElementCppFactory.createUMLClassMember(attr, index, this);
				
				attrDescriptorList.add(member);
				
				index++;
			}
		}	
		
		this.addUserClsRefIntoBOHandlerHeader(getClassName());
	}
	public UMLServiceCppDescriptor getParent() {
		return m_parent;
	}
	
	public String getUMLName() {
		return m_umlName;
	}
	
	//construct c++ parameter name when its type is UML class 
	public String getParaName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}
	
	//construct JBC parameter name when its type is UML Class
	public String getJBCParaVarName() {
		return StringUtils.lowerInitialCharacter(getUMLName()) 
			+ "Var";
	}	
	
	//construct business object class name for this UML class
	public String getClassName() {
		return StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//construct business object .h file name for this UML class
	public String getClassHeaderFileName() {
		return getClassName() + Constants.CPP_HEADER_FILE_EXT;
	}
	
	//construct business object .cpp file name for this UML class
	public String getClassCppFileName() {
		return getClassName() + Constants.CPP_FILE_EXT;
	}
	
	//construct business object handler class name for this UML class 
	public String getHandlerClassName() {
		return getClassName() + "Handler";
	}
	
	//construct business object handler .h file name for this UML class
	public String getHandlerClassHeaderFileName() {
		return getHandlerClassName() + Constants.CPP_HEADER_FILE_EXT;
	}
	
	//construct business object handler .cpp file name for this UML class
	public String getHandlerClassCppFileName() {
		return getHandlerClassName() + Constants.CPP_FILE_EXT;
	}	
	
	/*
	 * these following methods are to get and add class references
	 */
	public String[] getStdClsRefListInBOHeader() {
		return stdClsRefListInBOHeader.toArray(
				new String[stdClsRefListInBOHeader.size()]);
	}
	public void addStdClsRefIntoBOHeader(String clsRef) {
		stdClsRefListInBOHeader.add(clsRef);
	}
	
	public String[] getTemenosClsRefListInBOHeader() {
		return temenosClsRefListInBOHeader.toArray(
				new String[temenosClsRefListInBOHeader.size()]);
	}
	public void addTemenosClsIntoBoHeader(String clsRef) {
		temenosClsRefListInBOHeader.add(clsRef);
	}
	
	public String[] getUserClsRefListInBOHeader() {
		return userClsRefListInBOHeader.toArray(
				new String[userClsRefListInBOHeader.size()]);
	}
	public void addUserClsRefIntoBOHeader(String clsRef) {
		userClsRefListInBOHeader.add(clsRef);
	}
	public String[] getStdClsRefListInBOCpp() {
		return stdClsRefListInBOCpp.toArray(
				new String[stdClsRefListInBOCpp.size()]);
	}
	public void addStdClsRefIntoBOCpp(String clsRef) {
		stdClsRefListInBOCpp.add(clsRef);
	}
	public String[] getTemenosClsRefListInBOCpp() {
		return temenosClsRefListInBOCpp.toArray(
				new String[temenosClsRefListInBOCpp.size()]);
	}
	public void addTemenosClsIntoBOCpp(String clsRef) {
		temenosClsRefListInBOCpp.add(clsRef);
	}
	
	public String[] getUserClsRefListInBOCpp() {
		return userClsRefListInBOCpp.toArray(
				new String[userClsRefListInBOCpp.size()]);
	}
	public void addUserClsIntoBoCpp(String clsRef) {
		userClsRefListInBOCpp.add(clsRef);
	}
	
	public String[] getstdClsRefListInBOHanderHeader() {
		return stdClsRefListInBOHandlerHeader.toArray(
				new String[stdClsRefListInBOHandlerHeader.size()]);
	}
	public void addStdClsRefIntoBoHanlderHeader(String clsRef) {
		stdClsRefListInBOHandlerHeader.add(clsRef);
	}
	
	public String[] getTemenosClsRefListInBOHandlerHeader() {
		return temenosClsRefListInBOHandlerHeader.toArray(
				new String[temenosClsRefListInBOHandlerHeader.size()]);
	}
	public void addTemenosClsIntoBOHandlerHeader(String clsRef) {
		temenosClsRefListInBOHandlerHeader.add(clsRef);
	}
	
	public String[] getUserClsRefListInBOHandlerHeader() {
		return userClsRefListInBOHandlerHeader.toArray(
				new String[userClsRefListInBOHandlerHeader.size()]);
	}
	public void addUserClsRefIntoBOHandlerHeader(String clsRef) {
		userClsRefListInBOHandlerHeader.add(clsRef);
	}
	public String[] getStdClsRefListInBOHandlerCpp() {
		return stdClsRefListInBOHandlerCpp.toArray(
				new String[stdClsRefListInBOHandlerCpp.size()]);
	}
	public void addStdClsRefIntoBoHandlerCpp(String clsRef) {
		stdClsRefListInBOHandlerCpp.add(clsRef);
	}
	public String[] getTemenosClsRefListInBOHandlerCpp() {
		return temenosClsRefListInBOHandlerCpp.toArray(
				new String[temenosClsRefListInBOHandlerCpp.size()]);
	}
	public void addTemenosClsIntoBOHandlerCpp(String clsRef) {
		temenosClsRefListInBOHandlerCpp.add(clsRef);
	}
	
	public String[] getUserClsRefListInBOHandlerCpp() {
		return userClsRefListInBOHandlerCpp.toArray(
				new String[userClsRefListInBOHandlerCpp.size()]);
	}
	public void addUserClsIntoBOHandlerCpp(String clsRef) {
		userClsRefListInBOHandlerCpp.add(clsRef);
	}	
	
	//get all the members defined in this UML Class
	//each member is represented with UMLClassMember object
	public UMLClassAttrCppDescriptor[] getMembers() {
		return attrDescriptorList.toArray(new UMLClassAttrCppDescriptor[attrDescriptorList.size()]);
	}
	
	//declare default constructor of business object class
	public String declareDefaultConstrutor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "DLLDECL explicit " + getClassName() + "();");
		
		return sb.toString();
	}
	
	//define default constructor of business object class
	public String defineDefaultConstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ getClassName() + "::" + getClassName() + "()\n");
		
		formatter.indent();
		formatter.indent();
		
		//initialize each member with default value
		int index = 0;
		for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
			sb.append(formatter.currentIndent());
			if(index == 0)
				sb.append(":");
			sb.append(member.getVarName() + "(");
			sb.append(member.getCppDefaultValue());
			sb.append(")");
			
			if(index < (attrDescriptorList.size() - 1))
				sb.append(",\n");
			else
				sb.append(" {\n");
			
			index++;
		}
		
		formatter.outdent();
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
	
		return sb.toString();
	}
	
	//declare constructor of business object
  public String declareConstructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent() 
  			+ "DLLDECL explicit " + getClassName()
  	    + "(\n");
  	    
  	formatter.indent();
  	formatter.indent();
  	
  	//construct parameter list for constructor
  	int index = 0;
  	for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
  		sb.append(formatter.currentIndent()
  				+ member.getCppParaType() + " "  		
  				+ member.getParaName());
  		
  		if(index < (attrDescriptorList.size() - 1))
  			sb.append(",\n");
  		else
  			sb.append(");\n");
  		index++;
  	}  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();
  }
  
  //define constructor of business object
  public String defineConstructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ getClassName() + "::" + getClassName()
  	    + "(\n");
  	
  	formatter.indent();
  	formatter.indent();
  	
  	//construct parameter list for constructor
  	int index = 0;
  	for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
  		sb.append(formatter.currentIndent() 
  				+ member.getCppParaType()
  		    + " " + member.getParaName());
  		
  		if(index < (attrDescriptorList.size() - 1))
  			sb.append(",\n");
  		else
  			sb.append(")\n");
  		index++;
  	}
  	
  	//initialize each member with corresponding input parameter
  	index = 0;
  	for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
			sb.append(formatter.currentIndent());
			
			if(index == 0)
				sb.append(":");
			
			sb.append(member.getVarName() 
					+ "(" 
					+ member.getParaName()
					+")");
			
			if(index < (attrDescriptorList.size() - 1))
				sb.append(",\n");
			else
				sb.append(" {\n");
			
			index++;
		}		
  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	sb.append(formatter.currentIndent() + "}\n");
  	
  	return sb.toString();
  }
  
  //declare deconstructor of business object
  public String declareDestructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+"DLLDECL virtual ~" + getClassName() + "();");
  	
  	return sb.toString();
  }
  
  //define deconstructor of business object
  public String defineDestructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ getClassName() + "::~" + getClassName() + "() {\n");
  	
  	sb.append(formatter.currentIndent() + "}");
  	
  	return sb.toString();
  }
  
  //declare ToString() method of business object
  public String declareToStringMethod() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent() 
  			+ "DLLDECL std::string ToString() const;");
  	
  	return sb.toString();
  	
  }
  
  //define ToString() method of business object
  public String defineToStringMethod() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent() 
  			+ "std::string " + this.getClassName() + "::" 
  			+ "ToString() const {\n");
  	
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent() 
  			+ "std::ostringstream oss(std::ostringstream::out);\n");
  	sb.append("\n");
  	
  	for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
  		sb.append(member.convertToString());
  	}
  	
  	sb.append("\n");
  	sb.append(formatter.currentIndent() 
  			+ "return oss.str();\n");  	
  	formatter.outdent();
  	
  	sb.append(formatter.currentIndent() + "}\n");
  	
  	return sb.toString();
  }
  
  //declare the method to convert c++ business object
  //to JBC VAR struct
  public String declareBOToJBCVarMethod() {
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(BOToJBCVarMethodSignature(false) + ";");
  	
  	return sb.toString();
  }
  
  //define the method to convert JBC VAR struct
  //to c++ business object
  public String defineBOToJBCVarMethod() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(BOToJBCVarMethodSignature(true) + "{\n");
  	
  	formatter.indent();
  	  	
  	sb.append(formatter.currentIndent() 
  			+ CppConstants.SOA_LOGGER_CLASS + "& logger = "
  			+ CppConstants.SOA_LOGGER_CLASS + "::GetInstance();\n\n");
  	
  	//check whether input parameters are valid or not
  	sb.append(formatter.currentIndent() 
  			+ "//check whether input parameters are valid or not\n");
  	sb.append(formatter.currentIndent() 
  			+ "if((session == NULL) || (" 
  			+ getJBCParaVarName() + " == NULL)) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "std::string errMsg(\"session and/or " 
  			+ getJBCParaVarName() + " is NULL\");\n\n");
  	sb.append(formatter.currentIndent() 
  			+ "if(logger.IsEnabled())\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ "logger.Log(errMsg, __FILE__, __LINE__);\n\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent() 
  			+ CppConstants.SOA_EXCEPTION_CLASS 
  			+ " ex(errMsg);\n");
  	sb.append(formatter.currentIndent() 
  			+ "ex.AppendContext(__FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent()
  			+ "throw ex;\n");
  	formatter.outdent();  	
  	sb.append(formatter.currentIndent() + "}\n\n");
  	
  	//log trace information
  	sb.append(formatter.currentIndent() 
  			+ "//log debug information: function initial status\n");
  	sb.append(formatter.currentIndent() 
  			+ "if(logger.IsEnabled()) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "std::string varOutputString = " 
   			+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
  			+ "::ToString(session, " 
  			+ getJBCParaVarName() + ");\n\n");
  	sb.append(formatter.currentIndent() 
  			+ "std::string boOutputString = "
  	    + getParaName() + ".ToString();\n\n");
  	sb.append(formatter.currentIndent()
  			+ "logger.Log(\"START " + getHandlerClassName()
  			+ "::" + BOToJBCVarMethodName() + "()\", __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+getParaName() + " : \" + " 
  			+ "boOutputString, __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+ getJBCParaVarName() + " : \" + " 
  			+ "varOutputString, __FILE__, __LINE__);\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n\n");
  	
  	//store each member in business object into JBC VAR struct
  	sb.append(formatter.currentIndent() 
  			+ "//store business object into JBC VAR\n");
  	sb.append(formatter.currentIndent()
  			+ "try {\n");
  	formatter.indent();
  	for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
  		sb.append(member.storeToCppJBCVar());
  	}
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "} catch("
  			+ CppConstants.SOA_EXCEPTION_CLASS  
  			+ "& ex) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "ex.AppendContext(__FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent()
  			+ "throw;\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n\n");
  	
  	//log the trace information
  	sb.append(formatter.currentIndent() 
  			+ "//log debug information: function end status\n");
  	sb.append(formatter.currentIndent() 
  			+ "if(logger.IsEnabled()) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "std::string varOutputString = "
  			+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
  			+ "::ToString(session, " 
  			+ getJBCParaVarName() 
  			+ ");\n\n");
  	sb.append(formatter.currentIndent() 
  			+ "std::string boOutputString = "
  			+ getParaName() + ".ToString();\n\n");
  	sb.append(formatter.currentIndent()
  			+ "logger.Log(\"END " + getHandlerClassName()
  			+ "::" + BOToJBCVarMethodName() + "()\", __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+getParaName() + " : \" + " 
  			+ "boOutputString, __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+ getJBCParaVarName() + " : \" + " 
  			+ "varOutputString, __FILE__, __LINE__);\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent() + "}");
  	
  	return sb.toString();
  }
  
  //the signature of the method to convert business object 
  //to JBC VAR struct
  private String BOToJBCVarMethodSignature(boolean isInCppFile) {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	if(isInCppFile) 
  		sb.append(formatter.currentIndent() 
  			+ "void ");
  	else
  		sb.append(formatter.currentIndent() 
  	  			+ "static void ");
  	
  	if(isInCppFile)
  		sb.append(this.getHandlerClassName() + "::");
  	
  	sb.append(BOToJBCVarMethodName() + "(\n");
  	  	
  	formatter.indent();
  	formatter.indent();
  	
  	//sb.append(formatter.currentIndent() 
  	//		+ "struct jBASEDataAreas* dp,\n");
  	sb.append(formatter.currentIndent()
  			+ "tafc::session::TAFCSession *session,\n");
  	sb.append(formatter.currentIndent() 
  			+ "const " + getClassName() + "& " + getParaName()+",\n");
  	sb.append(formatter.currentIndent() 
  			+"VAR* " + getJBCParaVarName() + ")");
  	  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();
  }
  
  //method name to convert c++ business object to
  //JBC VAR struct
  public String BOToJBCVarMethodName() {
  	return "ToVAR";
  }
  
  //declare the method to convert JBC VAR struct
  //to c++ business object
  public String declareJBCVarToBOMethod() {
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(this.JBCVarToBOMethodSignature(false) + ";");
  	
  	return sb.toString();
  }
  
  //define the method to convert JBC VAR struct
  //to c++ business object
  public String defineJBCVarToBOMethod() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(JBCVarToBOMethodSignature(true) + "{\n");
  	
  	formatter.indent();
  	  	
  	sb.append(formatter.currentIndent() 
  			+ CppConstants.SOA_LOGGER_CLASS + "& logger = "
  			+ CppConstants.SOA_LOGGER_CLASS + "::GetInstance();\n\n");
  	
  	//check whether input parameter are valid or not
  	sb.append(formatter.currentIndent() 
  			+ "//check whether input parameters are valid or not\n");
  	sb.append(formatter.currentIndent() 
  			+ "if((session == NULL) || (" 
  			+ getJBCParaVarName() + " == NULL)) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "std::string errMsg(\"session and/or " 
  			+ getJBCParaVarName() + " is NULL\");\n\n");
  	sb.append(formatter.currentIndent() 
  			+ "if(logger.IsEnabled())\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ "logger.Log(errMsg, __FILE__, __LINE__);\n\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent() 
  			+ CppConstants.SOA_EXCEPTION_CLASS 
  			+ " ex(errMsg);\n");
  	sb.append(formatter.currentIndent() 
  			+ "ex.AppendContext(__FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent()
  			+ "throw ex;\n");  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent() + "}\n\n");
  	
  	//log trace information
  	sb.append(formatter.currentIndent() 
  			+ "//log debug information: function initial status\n");
  	sb.append(formatter.currentIndent() 
  			+ "if(logger.IsEnabled()) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "std::string varOutputString = "
  			+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS 
  			+ "::ToString(session, " 
  			+ getJBCParaVarName() 
  			+ ");\n\n");  
  	sb.append(formatter.currentIndent() 
  			+ "std::string boOutputString = "
  			+ getParaName() + ".ToString();\n\n");
  	sb.append(formatter.currentIndent()
  			+ "logger.Log(\"START " + getHandlerClassName()
  			+ "::" + JBCVarToBOMethodName() + "()\", __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+ getJBCParaVarName() + " : \" + " 
  			+ "varOutputString, __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+getParaName() + " : \" + " 
  			+ "boOutputString, __FILE__, __LINE__);\n");  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n\n");
  	
  	//convert JBC VAR to c++ business object
  	sb.append(formatter.currentIndent() 
  			+ "//convert JBC VAR to Business Object\n");
  	sb.append(formatter.currentIndent()
  			+ "try {\n");
  	formatter.indent();
  	for(UMLClassAttrCppDescriptor member : attrDescriptorList) {
  		sb.append(member.extractFromCppJBCVar());
  	}
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "} catch("
  			+ CppConstants.SOA_EXCEPTION_CLASS
  			+ "& ex) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "ex.AppendContext(__FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent()
  			+ "throw;\n");
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n\n");
  	
  	//log trace information
  	sb.append(formatter.currentIndent() 
  			+ "//log debug information: function end status\n");
  	sb.append(formatter.currentIndent() 
  			+ "if(logger.IsEnabled()) {\n");
  	formatter.indent();
  	sb.append(formatter.currentIndent() 
  			+ "std::string varOutputString;\n");
  	sb.append(formatter.currentIndent()
  			+ CppConstants.SOA_JBC_VAR_HANDLER_CLASS
  			+ "::ToString(session, " 
  			+ getJBCParaVarName() 
  			+ ");\n\n");
  	sb.append(formatter.currentIndent() 
  			+ "std::string boOutputString = "
  			+ getParaName() + ".ToString();\n\n");
  	sb.append(formatter.currentIndent()
  			+ "logger.Log(\"END " + getHandlerClassName()
  			+ "::" + JBCVarToBOMethodName() + "()\", __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+ getJBCParaVarName() + " : \" + " 
  			+ "varOutputString, __FILE__, __LINE__);\n");
  	sb.append(formatter.currentIndent() 
  			+ "logger.Log(\"   " 
  			+getParaName() + " : \" + " 
  			+ "boOutputString, __FILE__, __LINE__);\n");  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent() + "}");
  	
  	return sb.toString();
  }
  
  //the signature of the method to convert JBC VAR struct to 
  //c++ business object
  private String JBCVarToBOMethodSignature(boolean isInCppFile) {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	if(isInCppFile)
  		sb.append(formatter.currentIndent() 
  			+ "void ");
  	else
  		sb.append(formatter.currentIndent() 
  			+ "static void ");
  	
  	if(isInCppFile)
  		sb.append(this.getHandlerClassName() + "::");
  	
  	sb.append(JBCVarToBOMethodName() + "(\n");
  	  	
  	formatter.indent();
  	formatter.indent();
  	
  	//sb.append(formatter.currentIndent() 
  	//		+ "struct jBASEDataAreas* dp,\n");
  	sb.append(formatter.currentIndent() 
  			+ "tafc::session::TAFCSession *session,\n");
  	sb.append(formatter.currentIndent() 
  			+"VAR* " + getJBCParaVarName() + ",\n");
  	sb.append(formatter.currentIndent() 
  			+this.getClassName() + "& " + getParaName()+")");
  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	
  	return sb.toString();
  }
  
  //the name of the method to convert JBC VAR struct
  //c++ business object
  private String JBCVarToBOMethodName() {
  	return "FromVAR";
  } 
  
}
