/*
 * This class represents one UML service defined in UML service model
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlclass.UMLClassDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLServiceDotNetDescriptor {
	//component name
	private String m_componentName = null;
	
	//UML service name
	private String m_serviceName = null;
		
	//.NET standard class reference
	private Set<String> dotNetStdClsRefList = null;
	
	//.NET user class reference
	private Set<String> dotNetUserClsRefList = null;
	
	//c++ standard class reference list
	private Set<String> cppStdClsRefList = null;
	
	//c++ user class reference list
	private Set<String> cppUserClsRefList = null;
	
	//UML operation list defined in the UML Service,
	//which represented by UMLOperation object
	private ArrayList<UMLOperationDotNetDescriptor> umlOperationList = null;
	private ArrayList<UMLOperationDotNetDescriptor> umlGenOperationList = null;
	
	//UML complex type list defined in the UML Service,
	//which represented by UMLClass object
	private ArrayList<UMLClassDotNetDescriptor> umlClsList = null;

	
	public UMLServiceDotNetDescriptor(ServiceDescriptor serviceDesc) {
		m_componentName = serviceDesc.getComponentName();
		m_serviceName = serviceDesc.getName();
				
		umlOperationList = new ArrayList<UMLOperationDotNetDescriptor>();
		umlGenOperationList = new ArrayList<UMLOperationDotNetDescriptor>();
		umlClsList = new ArrayList<UMLClassDotNetDescriptor>();		
		
		dotNetStdClsRefList = new HashSet<String>();
		//GetMetaData will return String^		
		dotNetStdClsRefList.add("System::String");
		
		dotNetUserClsRefList = new HashSet<String>();
		//SetContextCallback will reference ManagedContextCallback
		dotNetUserClsRefList.add("temenos::soa::common::ManagedContextCallback");
		
		cppStdClsRefList = new HashSet<String>();
		cppStdClsRefList.add("string");
		cppUserClsRefList = new HashSet<String>();
		cppUserClsRefList.add("PrimitiveMarshal");
		
		init(serviceDesc);
	}
	
	//construct UMLOperation object list and UMLClass object list
	//for each UML operation and UML complex type defined
	//UML service model, respectively
	private void init(ServiceDescriptor serviceDesc) {
		if(serviceDesc != null) { 
			if(serviceDesc.getClassDefDescriptors() != null) {
				for(ClassDefDescriptor classDefDesc : serviceDesc.getClassDefDescriptors()) {
					UMLClassDotNetDescriptor complexType = 
						new UMLClassDotNetDescriptor(classDefDesc, this);
					
					umlClsList.add(complexType);
				}
				
			}
			if(serviceDesc.getOperations() != null) {
				for(OperationDescriptor operationDesc : serviceDesc.getOperations() ) {
					UMLOperationDotNetDescriptor operation = 
						new UMLOperationDotNetDescriptor(operationDesc, this);
					umlOperationList.add(operation);
				}
			}
			if(serviceDesc.getGenOperations() != null) {
				for(OperationDescriptor operationDesc : serviceDesc.getGenOperations() ) {
					UMLOperationDotNetDescriptor operation =
						new UMLOperationDotNetDescriptor(operationDesc, this);
					umlGenOperationList.add(operation);
				}
			}
		}		
	}
	public String getComponentName() {
		return StringUtils.upperInitialCharacter(
				m_componentName);
	}
	public String getServiceName() {
		return StringUtils.upperInitialCharacter(
				m_serviceName);
	}
	
	public String getServiceNameInLowerInitialChar() {
		return StringUtils.lowerInitialCharacter(
				m_serviceName);
	}
	
	//name space that .NET API belong to
	public String getNamespace() {
		return getComponentName() + "NS";
	}	
	
  //get UML operation list defined in this service model
	public UMLOperationDotNetDescriptor[] getOperations() {
		return umlOperationList.toArray(
				new UMLOperationDotNetDescriptor[umlOperationList.size()]);
	}
	
	//get UML operation list defined in this service model
	public UMLOperationDotNetDescriptor[] getGenOperations() {
		return umlGenOperationList.toArray(
					new UMLOperationDotNetDescriptor[umlGenOperationList.size()]);
	}

	//get UML complex type list defined in this service model 
	public UMLClassDotNetDescriptor[] getUMLClasses() {		
		return umlClsList.toArray(
				new UMLClassDotNetDescriptor[umlClsList.size()]);
	}	
	
	//get and add .NET standard class reference
	public String[] getDotNetStdClsRefList() {
		return dotNetStdClsRefList.toArray(
				new String[dotNetStdClsRefList.size()]);
	}
	public void addDotNetStdClsRef(String dotNetStdClsRef) {
		dotNetStdClsRefList.add(dotNetStdClsRef);
	}
	
	//get and add .NET user class reference
	public String[] getDotNetUserClsRefList() {
		return dotNetUserClsRefList.toArray(
				new String[dotNetUserClsRefList.size()]);
	}
	public void addDotNetUserClsRef(String dotNetUserClsRef) {
		dotNetUserClsRefList.add(dotNetUserClsRef);
	}
	
	//get and add c++ standard class reference
	public String[] getCppStdClsRefList() {
		return cppStdClsRefList.toArray(
				new String[cppStdClsRefList.size()]);
	}
	public void addCppStdClsRef(String cppStdClsRef) {
		cppStdClsRefList.add(cppStdClsRef);
	}
	
	//get and add c++ user class reference
	public String[] getCppUserClsRefList() {
		return cppUserClsRefList.toArray(
				new String[cppUserClsRefList.size()]);
	}
	public void addCppUserClsRef(String cppUserClsRef) {
		cppUserClsRefList.add(cppUserClsRef);
	}

	//the name of .NET context call back reference
	public String getContextCallbackVarName() {
		return "m_managedContextCallback";
	}
	//the name of .NET context call back handle
	public String getContextCallbackHandleVarName() {
		return "m_managedContextCallbackHandle";
	}
	
	//the name of c++ API variable
	public String getCppServiceVarName() {
		return "m_" + StringUtils.lowerInitialCharacter(getServiceName());
	}
	
	//managed dll export header file name without extension
	public String getDotNetDLLDeclHeaderName() {
		return "Managed" + getServiceName() + "DataDLLDecl"
			+ Constants.CPP_HEADER_FILE_EXT;
	}
	
	//c++ API interface class name
	public String getCppServiceAPIClassName() {
		return StringUtils.upperInitialCharacter(m_serviceName)
			+ "API";
	}
	
	//.NET API interface name
  public String getDotNetServiceAPIClassName() {
  	return "Managed" + StringUtils.upperInitialCharacter(m_serviceName)
  		+ "API";
  }
  //the file name of .NET API interface .h
	public String getDotNetServiceAPIHeaderFileName() {		
		return getDotNetServiceAPIClassName() + Constants.CPP_HEADER_FILE_EXT;
	}
	
	//.NET API impl class name
	public String getDotNetServiceImplClassName() {
		return "Managed" + StringUtils.upperInitialCharacter(m_serviceName)
			+ "Impl";
	}
	//.NET API WCF Service class name
	public String getDotNetWCFServiceClassName() {
		return StringUtils.upperInitialCharacter(m_serviceName)
			+ "WS";
	}
	
	// .NET API WCF Service Behavior class name
	public String getDotNetWCFServiceBehaviorClassName() {
		return StringUtils.upperInitialCharacter(m_componentName)
			+ "Behavior";
	}
	
	
	//the file name of .NET API impl .h
	public String getDotNetServiceImplHeaderFileName() {
		return getDotNetServiceImplClassName() + Constants.CPP_HEADER_FILE_EXT;
	}
	//the file name of .NET API impl .cpp
	public String getDotNetServiceImplCppFileName() {
		return getDotNetServiceImplClassName() + Constants.CPP_FILE_EXT;
	}
	
	//the file name of .NET WCF Service .cs
	public String getDotNetWCFServiceCSFileName() {
		return getDotNetWCFServiceClassName() + Constants.CS_FILE_EXT;
	}
	
	//the file name of .NET WCF Service Impl (.svc.cs)
	public String getDotNetWCFServiceImplSVCCSFileName() {
		return getDotNetWCFServiceClassName() + Constants.SVCCS_FILE_EXT;
	}
	
	// The file name of the .NET WCF Service Behavior (.cs)
	public String getDotNetWCFServiceBehaviorCSFileName() {
		return getDotNetWCFServiceBehaviorClassName() + Constants.CS_FILE_EXT;
	}
	
	//the file name of .NET WCF Service Markup XML (.svc)
	public String getDotNetWCFServiceSVCMarkupXMLFileName() {
		return getDotNetWCFServiceClassName() + Constants.SVC_FILE_EXT;
	}
	
	//declare .NET API impl constructor
	public String declareImplConstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getDotNetServiceImplClassName() + "(ManagedContextCallback^ contextCallback);\n");
				
		return sb.toString();
	}
	
	//define .NET API impl constructor
	public String defineImplConstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getDotNetServiceImplClassName()
				+ "::"
				+ getDotNetServiceImplClassName()
				+ "(\n");
		formatter.indent();
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+ "ManagedContextCallback^ contextCallback)\n");
		sb.append(formatter.currentIndent()
				+ ":" + getContextCallbackVarName() + "(contextCallback),\n");
		sb.append(formatter.currentIndent()
				+ getCppServiceVarName() + "(NULL),\n");
		sb.append(formatter.currentIndent()
				+ getContextCallbackHandleVarName() + "(NULL) {\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent()
				+ getCppServiceVarName() + " = " 
				+ "create" + StringUtils.upperInitialCharacter(getServiceName())
				+ "();\n");
		
		sb.append(formatter.currentIndent()
				+ "if(" + getCppServiceVarName() + ") {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ getContextCallbackHandleVarName() + " = "
				+ "new temenos::soa::common::ManagedContextCallbackHandle("
				+ getContextCallbackVarName() + ");\n");
		sb.append(formatter.currentIndent()
				+ getCppServiceVarName() + "->SetContextCallback(" 
				+ getContextCallbackHandleVarName() + ");\n");
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		return sb.toString();
	}
	
	//declare .NET API impl deconstructor
	public String declareImplDeconstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "virtual ~" 
				+ getDotNetServiceImplClassName() + "();\n");
		
		return sb.toString();
	}
	
	//define .NET API impl deconstructor
	public String defineImplDeconstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getDotNetServiceImplClassName()
				+ "::~" 
				+ getDotNetServiceImplClassName() + "() {\n");
		formatter.indent();
	
		sb.append(formatter.currentIndent()
				+ "this->!" + getDotNetServiceImplClassName() + "();\n");
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		
		return sb.toString();
	}
	
	//declare .NET API impl finalizer
	public String declareImplFinalizer() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "!" 
				+ getDotNetServiceImplClassName() + "();\n");
		
		return sb.toString();
	}
	
	//define .NET API impl finalizer
	public String defineImplFinalizer() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getDotNetServiceImplClassName()
				+ "::!" 
				+ getDotNetServiceImplClassName() + "() {\n");
		formatter.indent();
		
		sb.append(formatter.currentIndent()
				+ "try {}\n");
		sb.append(formatter.currentIndent()
				+ "finally {\n");
		formatter.indent();
		
		sb.append(formatter.currentIndent()
				+ "if(" + getContextCallbackHandleVarName() + ") {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "delete " + getContextCallbackHandleVarName() + ";\n");
		sb.append(formatter.currentIndent()
				+ getContextCallbackHandleVarName() + " = NULL;\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() + "}\n");
		
		sb.append(formatter.currentIndent()
				+ "if(" + getCppServiceVarName() + ") {\n");
		formatter.indent();
		
		sb.append(formatter.currentIndent()
				+ "destroy" + StringUtils.upperInitialCharacter(getServiceName())
				+ "(" + getCppServiceVarName() + ");\n");
		sb.append(formatter.currentIndent()
				+ getCppServiceVarName() + " = NULL;\n");
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");		
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		return sb.toString();
	}

	//declare member of .NET API impl class
	public String declareImplMember() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "ManagedContextCallback^ "
				+ getContextCallbackVarName() 
				+ ";\n");
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::ManagedContextCallbackHandle* "
				+ getContextCallbackHandleVarName() 
				+ ";\n");
		sb.append(formatter.currentIndent()
				+ getCppServiceAPIClassName() 
				+ "* " 
				+ getCppServiceVarName()
				+ ";\n");
		
		return sb.toString();
	}
	
	//declare method to get meta data 
	public String declareGetMetaDataMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "virtual String^ GetMetaData();\n");
		
		return sb.toString();
	}
	
	//define method to get meta data
	public String defineGetMetaDataMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "String^ " + getDotNetServiceImplClassName()+ "::GetMetaData() {\n");
	  formatter.indent();
	  
	  sb.append(formatter.currentIndent()
	  		+ "std::string metaData = " + getCppServiceVarName() + "->GetMetaData();\n");
	  sb.append(formatter.currentIndent() 
	  		+ "String^ managedMetaData = temenos::soa::common::PrimitiveMarshal::Unmarshal(metaData);\n");
	  sb.append(formatter.currentIndent()
	  		+ "return managedMetaData;\n");
	  
	  formatter.outdent();
	  sb.append(formatter.currentIndent()
	  		+ "}\n");
		return sb.toString();
	}
	
	//declare method to set managed context call back
	public String declareSetContextCallbackMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "virtual void SetContextCallback(ManagedContextCallback^ managedContextCallback);\n");
		
		return sb.toString();
	}
	
	//define method to set managed context call back
	public String defineSetContextCallbackMethod() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "void " + getDotNetServiceImplClassName() + "::SetContextCallback(ManagedContextCallback^ managedContextCallback) {\n");
		formatter.indent();
		
		sb.append(formatter.currentIndent()
				+ getContextCallbackHandleVarName()+ "->Reset(managedContextCallback);\n");				
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		return sb.toString();
	}
}
