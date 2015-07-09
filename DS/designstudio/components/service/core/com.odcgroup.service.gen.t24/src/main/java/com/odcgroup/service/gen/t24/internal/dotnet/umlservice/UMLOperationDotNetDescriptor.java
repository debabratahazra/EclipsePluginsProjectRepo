package com.odcgroup.service.gen.t24.internal.dotnet.umlservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.odcgroup.service.gen.t24.internal.categories.dotnet.api.DotNetFileType;
import com.odcgroup.service.gen.t24.internal.categories.dotnet.api.UMLElementDotNetFactory;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLOperationDotNetDescriptor {	
  private String m_umlOperationName = null;
	private ArrayList<UMLParaDotNetDescriptor> m_parameters = null;	
	private UMLParaDotNetDescriptor m_returnParameter = null;
	private UMLServiceDotNetDescriptor m_parent = null;
	
	public UMLOperationDotNetDescriptor(OperationDescriptor operationDesc,
																			UMLServiceDotNetDescriptor parent) {
		m_umlOperationName = operationDesc.getName();
		m_parameters = new ArrayList<UMLParaDotNetDescriptor>();
		
		m_parent = parent;
		
		init(operationDesc);
	}	
	/*
	 * construct UMLParameter object list for UML input parameters
	 * and return parameter of this UML operation	 
	 */
	private void init(OperationDescriptor opDesc) {
		if(opDesc != null) {
			List<ParamDescriptor> paraDescList = opDesc.getParameters();
		  Iterator<ParamDescriptor> it = paraDescList.iterator();
		
		  while(it.hasNext()) {
		  	ParamDescriptor paraDesc = it.next();
		  	
		  	UMLParaDotNetDescriptor umlParam = 
		  		UMLElementDotNetFactory.createUMLParameter(paraDesc, this);
		  	
		  	//as UML model parser will parameter descriptor for VOID,
		  	//the factory will return null if it is VOID
		  	if(umlParam != null)
		  		m_parameters.add(umlParam);
		  }
			
		  ParamDescriptor returnParaDesc = opDesc.getReturnParameter();
		  if(returnParaDesc != null) {		  	
		  	m_returnParameter = 
		  		UMLElementDotNetFactory.createUMLParameter(returnParaDesc, this);
		  }
		}
	}
	
	public UMLServiceDotNetDescriptor getParent() {
		return m_parent;
	}
	
	public UMLParaDotNetDescriptor[] getInputParameters() {
		return m_parameters.toArray(new UMLParaDotNetDescriptor[m_parameters.size()]);
	}
	
	// Get All parameters, here InputParameters == All
	public UMLParaDotNetDescriptor[] getParameters() {
		return getInputParameters();
	}
	
	public UMLParaDotNetDescriptor getReturnParameter() {
		return m_returnParameter;
	}
	
	//get operation name in c++ API
	public String getCppName() {
		return StringUtils.upperInitialCharacter(m_umlOperationName);
	}
	
	//get operation name in .NET API
	public String getDotNetName() {
		return StringUtils.upperInitialCharacter(m_umlOperationName);
	}
	
	// Get the operation name as it is in model
	public String getName() {
		return m_umlOperationName;
	}

	//declare comments for the operation in .NET API
	public String getDotNetOperationComment() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "/*********************************************/\n");
		sb.append(formatter.currentIndent()
				+ "/*Function : \t" + getDotNetName() + "*/\n");
		
		for(UMLParaDotNetDescriptor umlPara : m_parameters)
			sb.append(formatter.currentIndent()
					+ "/*  " + umlPara.getParaDotNetName() + " : \t" 
					+ umlPara.getDirection().toString() + " parameter*/\n");
		
		sb.append(formatter.currentIndent()
				+ "/*  responseDetail : \tOUT paremter to store error info*/\n");
		
		sb.append(formatter.currentIndent()
				+ "/*********************************************/");
		
		return sb.toString();
	}
	
	//declare operation in .NET API interface
	public String declareDotNetOperationInAPI() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
	  sb.append(formatter.currentIndent()
	  				+ declareDotNetOperation(getParent().getDotNetServiceAPIClassName(), 
	  																DotNetFileType.INTERFACE_CLASS_HEADER));
	  
	  return sb.toString();
		
	}
	
	//declare operation in .NET API impl
	public String declareDotNetOperationInImpl() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ declareDotNetOperation(getParent().getDotNetServiceImplClassName(), 
																DotNetFileType.REF_CLASS_HEADER));
		
		return sb.toString();
		
	}	
	
	//declare .NET operation for the given UML operation
	private String declareDotNetOperation(String className, DotNetFileType fileType) {		
		StringBuilder sb = new StringBuilder();
				
		sb.append(getDotNetOperationSignature(className, fileType));
				
		sb.append(";");		
		
		return sb.toString();
	}		
	
	//define .NET operation in the .NET impl
	public String defineDotNetOperationInImpl() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ getDotNetOperationSignature(getParent().getDotNetServiceImplClassName(), 
																			DotNetFileType.REF_CLASS_CPP));
		sb.append(" {\n");
		formatter.indent();
		
		sb.append(declareCppPara());
		
		sb.append(formatter.currentIndent()
				+ "try {\n");
		formatter.indent();
		
		sb.append(marshalDotNetPara());
		
		sb.append(callCppOperation());
		
		sb.append(unmarshalCppPara());
		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "} catch(Exception^ e) {\n");
		formatter.indent();
		
		sb.append(formatter.currentIndent() +
				"ManagedSOAResponseMessage^ managedResponseMessage = \n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+	"gcnew ManagedSOAResponseMessage(1, ManagedMessageType::NON_FATAL_ERROR, e->Message, e->StackTrace);\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent()
				+ "if(managedResponseDetail == nullptr) \n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "managedResponseDetail = gcnew ManagedSOAResponseDetail();\n");		
		formatter.outdent();
		
		sb.append(formatter.currentIndent()
				+ "managedResponseDetail->ResponseMessages->Add(managedResponseMessage);\n");
				
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		if(getReturnParameter() != null) 
			sb.append(formatter.currentIndent()
					+"return " + getReturnParameter().getParaCppName() + ";\n");
		
		formatter.outdent();
		sb.append(formatter.currentIndent() 
				+ "}\n");
		
		return sb.toString();
		
	}
	
	//.NET operation signature
	private String getDotNetOperationSignature(String className, DotNetFileType fileType) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		switch(fileType) {
		case INTERFACE_CLASS_HEADER:			
		case REF_CLASS_HEADER:
			sb.append("virtual ");
			break;
		case REF_CLASS_CPP:
		default:
			break;
		}
		
		if(m_returnParameter != null) 
			sb.append(m_returnParameter.getParaDotNetName() + " ");		
		else
			sb.append("void ");
		
		switch(fileType) {
		case REF_CLASS_CPP:
			sb.append(className + "::");
			break;
		case INTERFACE_CLASS_HEADER:			
		case REF_CLASS_HEADER:		
		default:
			break;
		}
		
		sb.append(getDotNetName());
				
		sb.append("(\n");
		
		formatter.indent();
		formatter.indent();
		
		for(UMLParaDotNetDescriptor umlPara : m_parameters) {
			sb.append(formatter.currentIndent() 
					+ umlPara.getParaDotNetType() 
					+ " " + umlPara.getParaDotNetName()
					+ ",\n");
			
		}		
		sb.append(formatter.currentIndent() 
				+ "ManagedSOAResponseDetail^%"
				+ " managedResponseDetail)");
			
		
		formatter.outdent();
		formatter.outdent();
				
		return sb.toString();
	}
	
	//declare corresponding c++ parameter for each .NET parameter
	private String declareCppPara() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *Declare corresponding c++ parameters, including returning parameter\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		for(UMLParaDotNetDescriptor para : getInputParameters()) {
			sb.append(formatter.currentIndent() 
					+ "//define c++ parameter for the given .NET input para,"
					+ para.getParaDotNetName() + "\n");
			sb.append(para.declareCppPara());
			sb.append("\n");
		}		
		
		if(m_returnParameter != null) {
			sb.append(formatter.currentIndent()
					+"//define C++ return parameter\n");
			sb.append(formatter.currentIndent()
					+ m_returnParameter.declareCppPara());			
			sb.append("\n");
		}
		
		sb.append(formatter.currentIndent() 
				+ "//define c++ responseDetail parameter\n");
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::SOAResponseDetail responseDetail;\n");
		
		sb.append("\n\n");
		
		return sb.toString();
	}
	
	//marshal IN and INOUT parameter from .NET to C++
	private String marshalDotNetPara() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *Marshal .NET in and inout parameters\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		for(UMLParaDotNetDescriptor para : getInputParameters()) {
			Direction direction = para.getDirection();
			if((direction == Direction.IN)
					|| (direction == Direction.INOUT)) {
				sb.append(formatter.currentIndent() 
						+ "//marshal .NET input para,"
						+ para.getParaDotNetName() + "\n");
				sb.append(para.marshal());
				sb.append("\n");
			}
		}		
		
		sb.append("\n\n");
		
		return sb.toString();
	}
	
	//unmarshal INOUT, OUT, and RETURN parameter from C++ to .NET
	private String unmarshalCppPara() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *unmarshal .NET inout and out parameters\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		for(UMLParaDotNetDescriptor para : getInputParameters()) {
			Direction direction = para.getDirection();
			if((direction == Direction.INOUT)
					|| (direction == Direction.OUT)
					|| (direction == Direction.RETURN)) {
				sb.append(formatter.currentIndent() 
						+ "//unmarshal .NET parameter, "
						+ para.getParaDotNetName() + "\n");
				sb.append(para.unmarshal());
				sb.append("\n");
			}
		}		
		
		if(m_returnParameter != null) {
			sb.append(formatter.currentIndent()
					+"//unmarshal return parameter\n");
			sb.append(formatter.currentIndent()
					+ m_returnParameter.unmarshal());			
			sb.append("\n");
		}
		
		sb.append(formatter.currentIndent() 
				+ "//unmarshal .NET responseDetail parameter\n");
		sb.append(formatter.currentIndent()
				+ "managedResponseDetail = temenos::soa::common::SOAResponseDetailMarshal::Unmarshal(responseDetail);\n");
		
		sb.append("\n\n");
		return sb.toString();
	}
	
	//invoke corresponding operation in C++ API
	private String callCppOperation() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *forward call to the corresponding operation in c++ API\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		if(getReturnParameter() != null) 
			sb.append(formatter.currentIndent()
					+ getReturnParameter().getParaCppName() + " = " 
					+ getParent().getCppServiceVarName() + "->"
					+ getCppName() + "(\n");
		else 
			sb.append(formatter.currentIndent()
					+ getParent().getCppServiceVarName() + "->"
					+ getCppName() + "(\n");
		
		formatter.indent();
		formatter.indent();
		
		for(UMLParaDotNetDescriptor para : getInputParameters()) {
			sb.append(formatter.currentIndent() 
					+ para.getParaCppName() + ",\n");			
		}
		
		sb.append(formatter.currentIndent()
				+ "responseDetail);\n");
		
		formatter.outdent();
		formatter.outdent();
		
		sb.append("\n\n");
		return sb.toString();
	}
	
	// Following method will generate WCF Service Operation contract for an Interface
	public String defineDotNetWCFOperationContract() {
		
		Formatter formatter = Formatter.getInstance();
		formatter.indent();
		formatter.indent();
		StringBuilder sb = new StringBuilder(formatter.currentIndent() + "[OperationContract]\n");
		sb.append(formatter.currentIndent() + 
					"void " + getDotNetWCFOperationWithParameters());
		sb.append(";\n");
		
		formatter.outdent();
		formatter.outdent();
		return sb.toString();
	}
	
	public String getDotNetWCFOperationWithParameters() {
		StringBuilder sb = new StringBuilder(m_umlOperationName + "(ManagedUserContext managedUserContext");
		Iterator<UMLParaDotNetDescriptor> it  = m_parameters.iterator();
		while(it.hasNext()) {
			UMLParaDotNetDescriptor paraDesc = it.next();
			
			// Check the direction first
		  	if (paraDesc.getDirection() == Direction.IN) {
		  		sb.append(", ");
		  	} else if ( paraDesc.getDirection() == Direction.INOUT) {
		  		sb.append (", ref ");		
		  	} else if (paraDesc.getDirection() == Direction.OUT ) {
		  		sb.append (", out ");		
		  	}
		  	sb.append(paraDesc.getDotNetWCFType() + " " + paraDesc.getParaDotNetName());
		}
		sb.append(", out ManagedSOAResponseDetail managedResponseDetail)");
		return sb.toString();
	}
	
	// Following method will generate DOTNET WCF Service Impl for an Interface
	public String defineDotNetWCFServiceImpl() {
		String implStr;
		
		// Get Native part
		implStr = defineDotNetWCFServiceImplNative();
		
		return implStr;
	}
	
	private String defineDotNetWCFServiceImplNative(){
		Formatter formatter = Formatter.getInstance();
		formatter.indent();
		formatter.indent();
		StringBuilder sb = new StringBuilder();
		// set the userContext
		sb.append (formatter.currentIndent() + "contextCallback.CurrentUserContext = managedUserContext;\n");
		sb.append (formatter.currentIndent() + "try\n");
		sb.append (formatter.currentIndent() + "{\n");
		formatter.indent();
		sb.append (formatter.currentIndent() + m_parent.getServiceNameInLowerInitialChar() + "API."
				+ getDotNetName() + "(");
		
		Iterator<UMLParaDotNetDescriptor> it2  = m_parameters.iterator();
		String tempStr = "";
		while(it2.hasNext()) {
			UMLParaDotNetDescriptor paraDesc = it2.next();
			if (paraDesc.getDirection().equals(Direction.INOUT) || paraDesc.getDirection().equals(Direction.OUT)) {
				tempStr += "ref ";
			}
			tempStr += paraDesc.getParaDotNetName() + ", ";
		}
		tempStr += "ref managedResponseDetail);\n";
		sb.append (tempStr);
		formatter.outdent();
		
		sb.append (formatter.currentIndent() + "}\n");
		sb.append (formatter.currentIndent() + "catch (Exception ex)\n");
		sb.append (formatter.currentIndent() + "{\n");
		formatter.indent();				
		sb.append (formatter.currentIndent() + "managedResponseDetail.ServiceName" +
				" = \"" + m_parent.getServiceName() + "." + m_umlOperationName +"\";\n");
		sb.append (formatter.currentIndent() + "managedResponseDetail.SOAResponseCode" +
				" = ManagedResponseCode.FAILURE;\n");

		sb.append (formatter.currentIndent() + "ManagedSOAResponseMessage soaResponseMessage" +
				" = new ManagedSOAResponseMessage();\n");
		sb.append (formatter.currentIndent() + "soaResponseMessage.ResponseMessageInfo" +
				" = \"EB-SERVICE.NOT.AVAILABLE\";\n");
		sb.append (formatter.currentIndent() + "soaResponseMessage.ResponseMessageText" +
				" = \"Service '" + m_parent.getServiceName() + "' is currently not available. Exception : \" + ex.Message;\n");
		sb.append (formatter.currentIndent() + "soaResponseMessage.ResponseMessageType" +
				" = ManagedMessageType.FATAL_ERROR;\n");

		sb.append (formatter.currentIndent() + "managedResponseDetail.ResponseMessages.Add(soaResponseMessage);\n");
		formatter.outdent();
		sb.append (formatter.currentIndent() + "}\n");
	
		formatter.outdent();
		return sb.toString();
	}
}
