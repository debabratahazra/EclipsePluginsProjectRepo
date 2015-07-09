/*
 * This class represents one UML service defined in UML service model
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.odcgroup.service.gen.t24.internal.cpp.umlclass.UMLClassCppDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLServiceCppDescriptor {
	//component name
	private String m_componentName = null;
	//UML service name
	private String m_serviceName = null;
	
	//context call back var
	private String m_contextCallbackVar = null;
	
	//UML operation list defined in the UML Service,
	//which represented by UMLOperation object
	private ArrayList<UMLOperationCppDescriptor> umlOperationList = null;
	private ArrayList<UMLOperationCppDescriptor> umlGenOperationList = null;
	
	//UML complex type list defined in the UML Service,
	//which represented by UMLClass object
	private ArrayList<UMLClassCppDescriptor> umlClsList = null;
	
	//c++ standard class reference list in service proxy interface
	private Set<String> stdClsRefListInInterface = null;
	//temenos class reference list in service proxy interface
	private Set<String> temenosClsRefListInInterface = null;
	//user defined class reference list in service proxy interface
	private Set<String> userClsRefListInInterface = null;
	
	//c++ standard class reference list in service proxy adaptor header
	private Set<String> stdClsRefListInAdaptorHeader = null;
	//temenos class reference list in service proxy adaptor header
	private Set<String> temenosClsRefListInAdaptorHeader = null;
	//user defined class reference list in service proxy adaptor header
	private Set<String> userClsRefListInAdaptorHeader = null;
	
	
	//c++ standard class reference list in service proxy adaptor .cpp
	private Set<String> stdClsRefListInAdaptorCpp = null;
	//temenos class reference list in service proxy adaptor .cpp
	private Set<String> temenosClsRefListInAdaptorCpp = null;
	//user defined class reference list in service proxy adaptor c.pp
	private Set<String> userClsRefListInAdaptorCpp = null;
	
	
	public UMLServiceCppDescriptor(ServiceDescriptor serviceDesc) {
		m_componentName = serviceDesc.getComponentName();
		m_serviceName = serviceDesc.getName();
		m_contextCallbackVar = 
			StringUtils.lowerInitialCharacter(serviceDesc.getName())
			+ "ContextCallback";
		
		umlOperationList = new ArrayList<UMLOperationCppDescriptor>();
		umlGenOperationList = new ArrayList<UMLOperationCppDescriptor>();
		umlClsList = new ArrayList<UMLClassCppDescriptor>();
		
		stdClsRefListInInterface = new HashSet<String>();
		temenosClsRefListInInterface = new HashSet<String>();
		userClsRefListInInterface = new HashSet<String>();
		
		stdClsRefListInAdaptorHeader = new HashSet<String>();
		temenosClsRefListInAdaptorHeader = new HashSet<String>();
		userClsRefListInAdaptorHeader = new HashSet<String>();
		
		
		stdClsRefListInAdaptorCpp = new HashSet<String>();
		temenosClsRefListInAdaptorCpp = new HashSet<String>();
		userClsRefListInAdaptorCpp = new HashSet<String>();
		
		init(serviceDesc);
	}
	
	//construct UMLOperation object list and UMLClass object list
	//for each UML operation and UML complex type defined
	//UML service model, respectively
	private void init(ServiceDescriptor serviceDesc) {
		if(serviceDesc != null) { 
			if(serviceDesc.getClassDefDescriptors() != null) {
				for(ClassDefDescriptor classDefDesc : serviceDesc.getClassDefDescriptors()) {
					UMLClassCppDescriptor complexType = 
						new UMLClassCppDescriptor(classDefDesc, this);
					
					umlClsList.add(complexType);
				}
				
			}
			if(serviceDesc.getOperations() != null) {
				for(OperationDescriptor operationDesc : serviceDesc.getOperations() ) {
					UMLOperationCppDescriptor operation = 
						new UMLOperationCppDescriptor(operationDesc, this);
					umlOperationList.add(operation);
				}
			}
			if(serviceDesc.getGenOperations() != null) {
				for(OperationDescriptor operationDesc : serviceDesc.getGenOperations() ) {
					UMLOperationCppDescriptor operation =
						new UMLOperationCppDescriptor(operationDesc, this);
					umlGenOperationList.add(operation);
				}
			}
		}
		
		//adaptor use the std_ptr to wrap the pointer 
		//to the instance of service
		stdClsRefListInAdaptorCpp.add("memory");
	}
	public String getComponentName() {
		return StringUtils.upperInitialCharacter(
				m_componentName);
	}
	public String getServiceName() {
		return StringUtils.upperInitialCharacter(
				m_serviceName);
	}
	public String getServiceContextCallbackVarName() {
		return m_contextCallbackVar;
	}
	//the c++ name space that C function adaptor
	//and C++ service proxy belong to 
	public String getNamespace() {
		return getComponentName() + "NS";
	}	
	
  //get UML operation list defined in this service model
	public UMLOperationCppDescriptor[] getOperations() {
		return umlOperationList.toArray(
				new UMLOperationCppDescriptor[umlOperationList.size()]);
	}
	
	//get UML operation list defined in this service model
	public UMLOperationCppDescriptor[] getGenOperations() {
			return umlGenOperationList.toArray(
					new UMLOperationCppDescriptor[umlGenOperationList.size()]);
	}

	//get UML complex type list defined in this service model 
	public UMLClassCppDescriptor[] getUMLClasses() {		
		return umlClsList.toArray(
				new UMLClassCppDescriptor[umlClsList.size()]);
	}
	
	public String[] getStdClsRefListInInterface() {
		return stdClsRefListInInterface.toArray(
				new String[stdClsRefListInInterface.size()]);
	}
	
	public void addStdClsRefIntoInterface(String clsRef) {
		stdClsRefListInInterface.add(clsRef);
	}
	
	public String[] getTemenosClsRefListInInterface() {
		return temenosClsRefListInInterface.toArray(
				new String[temenosClsRefListInInterface.size()]);
	}
	
	public void addTemenosClsRefIntoInterface(String clsRef) {
		temenosClsRefListInInterface.add(clsRef);
	}
	
	public String[] getUserClsRefListInInterface() {
		return userClsRefListInInterface.toArray(
				new String[userClsRefListInInterface.size()]);
	}
	
	public void addUserClsRefIntoInterface(String clsRef) {
		userClsRefListInInterface.add(clsRef);
	} 


	public String[] getStdClsRefListInAdaptorHeader() {
		return stdClsRefListInAdaptorHeader.toArray(
				new String[stdClsRefListInAdaptorHeader.size()]);
	}
	
	public void addStdClsRefIntoAdaptorHeader(String clsRef) {
		stdClsRefListInAdaptorHeader.add(clsRef);
	} 
	
	public String[] getTemenosClsRefListInAdaptorHeader() {
		return temenosClsRefListInAdaptorHeader.toArray(
				new String[temenosClsRefListInAdaptorHeader.size()]);
	}
	
	public void addTemenosClsRefIntoAdaptorHeader(String clsRef) {
		temenosClsRefListInAdaptorHeader.add(clsRef);
	}  
	
	public String[] getUserClsRefListInAdaptorHeader() {
		return userClsRefListInAdaptorHeader.toArray(
				new String[userClsRefListInAdaptorHeader.size()]);
	}
	
	public void addUserClsRefIntoAdaptorHeader(String clsRef) {
		userClsRefListInAdaptorHeader.add(clsRef);
	}	
	
	public String[] getStdClsRefListInAdaptorCpp() {
		return stdClsRefListInAdaptorCpp.toArray(
				new String[stdClsRefListInAdaptorCpp.size()]);
	}
	
	public void addStdClsRefIntoAdaptorCpp(String clsRef) {
		stdClsRefListInAdaptorCpp.add(clsRef);
	} 
	
	public String[] getTemenosClsRefListInAdaptorCpp() {
		return temenosClsRefListInAdaptorCpp.toArray(
				new String[temenosClsRefListInAdaptorCpp.size()]);
	}
	
	public void addTemenosClsRefIntoAdaptorCpp(String clsRef) {
		temenosClsRefListInAdaptorCpp.add(clsRef);
	}  
	
	public String[] getUserClsRefListInAdaptorCpp() {
		return userClsRefListInAdaptorCpp.toArray(
				new String[userClsRefListInAdaptorCpp.size()]);
	}
	
	public void addUserClsRefIntoAdaptorCpp(String clsRef) {
		userClsRefListInAdaptorCpp.add(clsRef);
	}	
	
	/***********************************************************************
	 * 
	 * service proxy interface to access external service
	 * 
	 **********************************************************************/
	//the name of the service proxy interface to access external service 
	public String getServiceProxyName () {
		return StringUtils.upperInitialCharacter(
				m_serviceName) + "Proxy";
	}
	
	//service proxy interface .h file name
	public String getServiceProxyHeaderFileName() {
		return getServiceProxyName() 
					 + Constants.CPP_HEADER_FILE_EXT;
	}
	
	//service proxy instance parameter name in destory method 
	public String getServiceProxyVarName() {
		return  StringUtils.lowerInitialCharacter(
				m_serviceName);
	}
	
	//declare customer service proxy de-constructor
	public String declareServiceProxyDeconstructor() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "virtual ~" + this.getServiceProxyName() + "(){}\n");
		
		return sb.toString();
	}
	
	//the method name to create instance of service proxy
	public String getCreateProxyMethodName() {
		StringBuilder sb = new StringBuilder();
		sb.append("create");
		sb.append(
				StringUtils.upperInitialCharacter(m_serviceName));
		sb.append("Proxy");
		return sb.toString();
	}
	
	//the method name to destroy service proxy instance
	public String getDestroyProxyMethodName() {
		StringBuilder sb = new StringBuilder();
		sb.append("destroy");
		sb.append(
				StringUtils.upperInitialCharacter(m_serviceName));
		sb.append("Proxy");
		return sb.toString();
	}
	
	//the name of service proxy object instance used in
	//C proxy adatpor
	public String getServiceInstanceVarName() {
		return StringUtils.lowerInitialCharacter(
				getServiceProxyName ()) + "Instance";
	}
	
	//declare the method to create service proxy instance
	public String getCreateProxyFuncDeclaration() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();		
		
		sb.append(formatter.currentIndent()
		    + "DLLDECL " + getServiceProxyName() + "* "
		    + getCreateProxyMethodName() + "(void);");
		
	  return sb.toString(); 
	}
	
	//delcare the method to destroy service proxy instance
	public String getDestroyProxyFuncDeclaration() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL void "
				+ getDestroyProxyMethodName()
				+ "("
				+ getServiceProxyName() + "* "
				+ getServiceProxyVarName()
				+ ");");
		
		return sb.toString();
	}	
	
	
	/*********************************************************************
	 * 
	 * service proxy adaptor between JBC API and external service
	 * 
	 *********************************************************************/		
	//service proxy adaptor class name
	public String getServiceProxyAdaptorName() {
		return StringUtils.upperInitialCharacter(
				m_serviceName) + "ProxyAdaptor";
	}	
	
	//service proxy adaptor .h file name
	public String getServiceProxyAdaptorHeaderFileName() {
		return getServiceProxyAdaptorName() 
			+ Constants.CPP_HEADER_FILE_EXT;
	}
	//service proxy adaptor .cpp file name
	public String getServiceProxyAdaptorCppFileName() {
		return getServiceProxyAdaptorName() + Constants.CPP_FILE_EXT;
	}
	
	/*********************************************************************
	 * 
	 * C++ service API 
	 * 
	 *********************************************************************/
	//the name of c++ service interface
	public String getServiceAPIName() {
		return StringUtils.upperInitialCharacter(
				m_serviceName) + "API";
	}
	
	//service API .h file name
	public String getServiceAPIHeaderFileName() {
		return getServiceAPIName() 
					+ Constants.CPP_HEADER_FILE_EXT;
	}	
	
	//service API instance parameter name in destroy method
	public String getServiceAPIVarName() {
		return StringUtils.lowerInitialCharacter(
				m_serviceName);
	}
	
	//declare service API de-constructor
	public String declareServiceDeconstructor() {
		Formatter formatter = Formatter.getInstance();		
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "virtual ~" + this.getServiceAPIName() + "(){}\n");
		
		return sb.toString();
	}
	
	//the method name to create instance of service API
	public String getCreateServiceMethodName() {
		StringBuilder sb = new StringBuilder();
		sb.append("create");
		sb.append(
				StringUtils.upperInitialCharacter(m_serviceName));		
		return sb.toString();
	}
	
	//the method name to destroy service proxy instance
	public String getDestroyServiceMethodName() {
		StringBuilder sb = new StringBuilder();
		sb.append("destroy");
		sb.append(
				StringUtils.upperInitialCharacter(m_serviceName));		
		return sb.toString();
	}	
	
	//declare the method to create service impl instance
	public String declareCreateCppServiceFunc() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();		
		
		sb.append(formatter.currentIndent()
		    + "DLLDECL " + getServiceAPIName() + "* "
		    + getCreateServiceMethodName() + "(void);");
		
	  return sb.toString(); 
	}
	
	//define the method to create service impl instance
	public String defineCreateCppServiceFunc() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getServiceAPIName() + "* "
				+ getCreateServiceMethodName() + "(void) {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ getServiceAPIName() 
				+ "* instance = new " 
				+ getServiceImplName() + "();\n\n");
		sb.append(formatter.currentIndent()
				+ "return instance;\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}");
		return sb.toString();
	}
	//delcare the method to destroy service proxy instance
	public String declareDestroyCppServiceFunc() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "DLLDECL void "
				+ getDestroyServiceMethodName()
				+ "("
				+ getServiceAPIName() + "* "
				+ getServiceAPIVarName()
				+ ");");
		
		return sb.toString();
	}
	
	//define the method to destroy service impl instance
	public String defineDestroyCppServiceFunc() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "void "
				+ getDestroyServiceMethodName()
				+ "("
				+ getServiceAPIName() + "* instance) {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "delete instance;\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() 
				+ "}\n");
				
		
		return sb.toString();
	}
	
	/*********************************************************************
	 * 
	 * C++ service Impl to  wrap JBC Impl
	 * 
	 *********************************************************************/
  //the name of c++ service implementation
	public String getServiceImplName() {
		return StringUtils.upperInitialCharacter(
				m_serviceName) + "Impl";
	}
			 
	//service Impl .h file name
	public String getServiceImplHeaderFileName() {
		return getServiceImplName()
			+ Constants.CPP_HEADER_FILE_EXT;
	}
	
	//service Impl .cpp file name
	public String getServiceImplCppFileName() {
		return getServiceImplName()
			+ Constants.CPP_FILE_EXT;
	}
	
	public String declareServiceImplConstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getServiceImplName()
				+ "();");
		
		return sb.toString(); 
	}
	public String defineServiceImplConstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
				
		sb.append(formatter.currentIndent()
				+ getServiceImplName() 
				+ "::"
				+ getServiceImplName()
				+ "() : currentUserContext() {\n");	
		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "using namespace temenos::soa::common;\n");
		sb.append(formatter.currentIndent()
				+ "m_contextCallback = NULL;\n\n");
		
		sb.append(formatter.currentIndent()
				+ "initSession();\n");
		formatter.outdent();		
		
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	public String declareServiceImplDeconstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "virtual ~"
				+ getServiceImplName()
				+ "();");
		
		return sb.toString();
	}
	public String defineServiceImplDeconstructor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getServiceImplName() 
				+ "::~"
				+ getServiceImplName()
				+ "() {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "using namespace temenos::soa::common;\n");
		
		sb.append(formatter.currentIndent() 
				+ "cleanupSession();\n");
		
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	
	/*********************************************************************
	 * 
	 * DLL export  
	 * 
	 *********************************************************************/	
	//dll export header file name without extension
	public String getDLLDeclHeaderName() {
		return getServiceName() + "DataDLLDecl";
	}
	//managed dll export header file name without extension
	public String getDotNetDLLDeclHeaderName() {
		return "Managed" + getServiceName() + "DataDLLDecl";
	}
	
	//dll export header file name 
	public String getDLLDeclHeaderFileName() {
		return getDLLDeclHeaderName() 
				+	Constants.CPP_HEADER_FILE_EXT;
	}
	//managed dll export header file name without extension
	public String getDotNetDLLDeclHeaderFileName() {
		return getDotNetDLLDeclHeaderName() 
			+ Constants.CPP_HEADER_FILE_EXT;
	}
	/*********************************************************************
	 * 
	 * DLL .def file  
	 * 
	 *********************************************************************/
	//DLL .def file for service proxy adaptor library
	public String getProxyAdaptorDLLDefFileName() {
		return "lib" 
					+ StringUtils.upperInitialCharacter(m_serviceName) + "ProxyAdaptor.def";
	}
	
	//DLL .def file for service library
	public String getServiceDLLDefFileName() {
		return "lib" 
					+ StringUtils.upperInitialCharacter(m_serviceName) + ".def";
	}
	
	//DLL .def header 
	public String getDLLDefHeader() {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf( "win" ) >= 0) {
			return "EXPORTS";
		}else {
			return "#!";
		}
	}	
}
