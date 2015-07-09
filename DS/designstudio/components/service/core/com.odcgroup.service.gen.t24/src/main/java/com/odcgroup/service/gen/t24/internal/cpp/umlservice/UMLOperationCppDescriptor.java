/*
 * this class represents one UML operation defined in UML service model 
 */
package com.odcgroup.service.gen.t24.internal.cpp.umlservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.odcgroup.service.gen.t24.internal.cartridges.cpp.CppConstants;
import com.odcgroup.service.gen.t24.internal.cartridges.cpp.UMLElementCppFactory;
import com.odcgroup.service.gen.t24.internal.data.Direction;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ParamDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.JBCSubroutineNameUtils;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLOperationCppDescriptor {
	//UML operation name
	private String m_umlOperationName = null;
	
	//a list of UMLParameters objects to represent
	//UML input parameter for this UML operation
	private ArrayList<UMLParaCppDescriptor> m_parameters = null;
	
	//a UMLParameter object to represent return parameter
	//for this UML operation
	private UMLParaCppDescriptor m_returnParameter = null;
	
	//the reference to UMLService object
	//which represents the service model defined this UML operation
	private UMLServiceCppDescriptor m_parent = null;
	
	public UMLOperationCppDescriptor(OperationDescriptor operationDesc,
			                UMLServiceCppDescriptor parent) {
		m_umlOperationName = operationDesc.getName();
		m_parameters = new ArrayList<UMLParaCppDescriptor>();
		
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
		  	
		  	UMLParaCppDescriptor umlParam = 
		  		UMLElementCppFactory.createUMLParameter(paraDesc, this);
		  	
		  	//as UML model parser will parameter descriptor for VOID,
		  	//the factory will return null if it is VOID
		  	if(umlParam != null)
		  		m_parameters.add(umlParam);
		  }
			
		  ParamDescriptor returnParaDesc = opDesc.getReturnParameter();
		  if(returnParaDesc != null) {		  	
		  	m_returnParameter = 
		  		UMLElementCppFactory.createUMLParameter(returnParaDesc, this);
		  }
		}
	}
	
	public UMLServiceCppDescriptor getParent() {
		return m_parent;
	}
	
	//get input parameter list represented by UMLParamere object list
	public UMLParaCppDescriptor[] getInputParameters() {
		return m_parameters.toArray(new UMLParaCppDescriptor[m_parameters.size()]);
	}
	
	//get return parameter represented by UMLParameter object
	public UMLParaCppDescriptor getReturnParameter() {
		return m_returnParameter;
	}
		
	
	/************************************************************************
	 * 
	 * JBC API and JBC Impl
	 * 
	 ***********************************************************************/	
	//JBC API subroutine name
	public String getJBCAPISubroutineName() {
		StringBuilder sb = new StringBuilder();
		sb.append(getParent().getServiceName() 
				+ "."
				+ StringUtils.lowerInitialCharacter(m_umlOperationName));
		return sb.toString();
	}
	//C++ name of JBC API subroutine
	public String getJBCAPISubroutineCppName() {
		return JBCSubroutineNameUtils.toTAFCFunctionName(
				getJBCAPISubroutineName());			
	}
	
	//JBC Impl subroutine name
	public String getJBCImplSubroutineName() {
		StringBuilder sb = new StringBuilder();
		sb.append("T24" + getParent().getServiceName() + "Impl" 
				+ "."
				+ StringUtils.lowerInitialCharacter(m_umlOperationName));
		return sb.toString();
	}
	//C++ name for JBC Impl subroutine
	public String getJBCImplSubroutineCppName() {
		return JBCSubroutineNameUtils.toTAFCFunctionName(
				getJBCImplSubroutineName());
	}
	
	
	
	/************************************************************************
	 * 
	 * C++ Service API and Proxy
	 * 
	 ***********************************************************************/
	//get C++ name for the given UML operation 
	public String getCppName() {
		return StringUtils.upperInitialCharacter(
				m_umlOperationName);
	}
	//c++ comments for c++ operation in service API and proxy interface
	public String getCppOperationComment() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() 
				+ "/*********************************************/\n");
		sb.append(formatter.currentIndent()
				+ "/*Function : \t" + getCppName() + "*/\n");
		
		for(UMLParaCppDescriptor umlPara : m_parameters)
			sb.append(formatter.currentIndent()
					+ "/*  " + umlPara.getCppParaName() + " : \t" 
					+ umlPara.getDirection().toString() + " parameter*/\n");
		
		sb.append(formatter.currentIndent()
				+ "/*  responseDetail : \tOUT paremter to store error info*/\n");
		
		sb.append(formatter.currentIndent()
				+ "/*********************************************/");
		
		return sb.toString();
	}
	//declare c++ operation for the given UML operation
	public String declareCppOperation(boolean pureVirtual) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent() + "virtual ");
		
		sb.append(getCppOperationSignature(false, ""));
		
		if(pureVirtual) 
			sb.append("= 0;");
		else
			sb.append(";");		
		
		return sb.toString();
	}		
		
	private String getCppOperationSignature(boolean isInCpp, String cppClassName) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		if(m_returnParameter != null) 
			sb.append(m_returnParameter.getParaCppType() + " ");		
		else
			sb.append("void ");
		
		if(isInCpp)
			sb.append(cppClassName + "::");
		
		sb.append(getCppName());
		sb.append("(\n");
		
		formatter.indent();
		formatter.indent();
		
		for(UMLParaCppDescriptor umlPara : m_parameters) {
			sb.append(formatter.currentIndent() 
					+ umlPara.getParaCppType() 
					+ " " + umlPara.getCppParaName()
					+ ",\n");
			
		}		
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_CLASS
				+ "& responseDetail)");
		
		formatter.outdent();
		formatter.outdent();
		return sb.toString();
	}

	
	/************************************************************************
	 * 
	 * C++ Impl to wrap JBC impl
	 * 
	 ***********************************************************************/
	//declare C function generated by TAFC for JBC Impl
	public String declareJBCImplCppRef() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = Formatter.getInstance();
		sb.append(formatter.currentIndent() 
				+ "int " + getJBCImplSubroutineCppName() + "(\n");
		
		formatter.indent();
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "struct jBASEDataAreas* dp,\n");
		sb.append(formatter.currentIndent()
				+ "char* actualFlags,\n");
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			sb.append(formatter.currentIndent()
					+ "VAR* " + para.getJBCParaName() + ",\n");
		}
		
		if(getReturnParameter() != null) {
			sb.append(formatter.currentIndent() 
					+ "VAR* " + getReturnParameter().getJBCParaName() + ",\n");
		}
		sb.append(formatter.currentIndent()
				+ "VAR* responseDetailVar);\n");
		
		formatter.outdent();
		formatter.outdent();
		return sb.toString();
	}
	
	//define c++ operation to wrap JBC impl
	public String defineCppOperation() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent());
		sb.append(getCppOperationSignature(true, getParent().getServiceImplName()));
		sb.append(" {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_LOGGER_CLASS 
				+ "& logger = "
				+ CppConstants.SOA_LOGGER_CLASS 
				+ "::GetInstance();\n\n");
		
		sb.append(formatter.currentIndent() 
				+ "try {\n");
		
		formatter.indent();
		
		//define necessary JBC VAR variable 
		sb.append(defineJBCVarPara());
		
		//convert IN and INOUT cpp parameter to JBC VAR
		sb.append(converCppInputParaToJBCPara());
		
		//directly call JBC Impl
		sb.append(directCallJBCImplSubroutine());
		
		//convert INOUT, OUT, RETURN JBC VAR back to C++ parameter
		sb.append(convertOutputJBCParaToCppPara());
		
		formatter.outdent();
		
		//catch SOAException, then standard exception, 
		//then all other exception. 
		//this is because C++ API is using ResponseDetail to report error		
		sb.append(formatter.currentIndent()
				+ "} catch("
				+ CppConstants.SOA_EXCEPTION_CLASS 
				+ "& soaException) {\n");
		formatter.indent();			
		sb.append(formatter.currentIndent() 
				+"if(logger.IsEnabled())\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "logger.Log(soaException.GetFaultInfo(), __FILE__, __LINE__);\n\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::SOAExceptionToResponseDetail("
				+ "soaException, responseDetail);\n");		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "} catch(std::exception& ex) {\n");
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+"if(logger.IsEnabled()) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::string errMsg(ex.what());\n");
		sb.append(formatter.currentIndent()
				+ "logger.Log(errMsg, __FILE__, __LINE__);\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::ExceptionToResponseDetail("
		    +	"ex, responseDetail);\n");	
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "} catch(...) {\n");
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+"if(logger.IsEnabled()) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::string errMsg(\"Unknown Exception\");\n");
		sb.append(formatter.currentIndent()
				+ "logger.Log(errMsg, __FILE__, __LINE__);\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() 
				+ "}\n\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::UnknowExceptionToResponseDetail("
		    +	"responseDetail);\n");	
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");		
		
		//return statement		
		if(getReturnParameter() != null) {
			sb.append("\n\n");
			sb.append(formatter.currentIndent()
					+ "return " 
					+ getReturnParameter().getCppParaName()
					+ ";\n");
		}
		
		formatter.outdent();
		sb.append(formatter.currentIndent() + "}\n");
		
		return sb.toString();
	}
	
	//define JBC VAR for c++ input parameter
	private String defineJBCVarPara() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = Formatter.getInstance();
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *Declare necessary JBC VAR variable and return c++ parameter\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			sb.append(formatter.currentIndent() 
					+ "//define JBC VAR struct for the given c++ input para,"
					+ para.getCppParaName() + "\n");
			sb.append(para.defineJBCVar());
			sb.append("\n");
		}		
		
		if(m_returnParameter != null) {
			sb.append(formatter.currentIndent()
					+"//define C++ return parameter\n");
			sb.append(formatter.currentIndent()
					+ m_returnParameter.defineCppVar());
			sb.append(formatter.currentIndent() 
					+ "//define JBC VAR struct for c++ return parameter\n");
			sb.append(m_returnParameter.defineJBCVar());
			sb.append("\n");
		}
		
		sb.append(formatter.currentIndent() 
				+ "//define JBC VAR struct for c++ responseDetail parameter\n");
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::VARObject responseDetailVar(session);\n");
		
		sb.append("\n\n");
		
		return sb.toString();
	}

	//convert each IN and/or INOUT C++ parameter to JBC VAR struct
	private String converCppInputParaToJBCPara() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = Formatter.getInstance();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *Convert IN and/or INOUT C++ parameter to JBC VAR\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			Direction direction = para.getDirection();
			if((direction == Direction.IN)
					|| (direction == Direction.INOUT)) {
				sb.append(para.convertCppVarToJBCVar(false));
				sb.append("\n");
			}
		}
		return sb.toString();
	}
		
	//convert each INOUT, OUT and/or RETURN JBC parameter
	//back to C++ parameter	
	private String convertOutputJBCParaToCppPara() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = Formatter.getInstance();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *Convert INOUT,OUT, RETURN JBC VAR to C++ Variable\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			Direction direction = para.getDirection();
			if((direction == Direction.INOUT)
					|| (direction == Direction.OUT)
					|| (direction == Direction.RETURN)) {			
				sb.append(para.convertJBCVarToCppVar(false));
				sb.append("\n");
			}
		}
		
		if(m_returnParameter != null) {
			sb.append(m_returnParameter.convertJBCVarToCppVar(false));
		}
		
		sb.append(formatter.currentIndent()
				+ "//convert JBC VAR responseDetailVar back to C++ parameter responseDetail\n");
		sb.append(formatter.currentIndent() 
				+  CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::FromVAR(session, responseDetailVar.Get(), responseDetail);\n");
		return sb.toString();
	}
	
	//application logic to call JBC Impl
	private String directCallJBCImplSubroutine() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = Formatter.getInstance();
		
		sb.append(formatter.currentIndent()
				+ "/**************************************************\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " *Direct call JBC Impl subroutine\n");
		sb.append(formatter.currentIndent()
				+ " *\n");
		sb.append(formatter.currentIndent()
				+ " **************************************************/\n");
		sb.append(formatter.currentIndent()
				+ "//switch user context if necessary\n");
		sb.append(formatter.currentIndent()
				+ "if(m_contextCallback != NULL) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "const temenos::soa::common::UserContext& userContext = \n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "m_contextCallback->GetUserContext();\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "if(!currentUserContext.Equal(userContext)) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::T24ContextHandler::SwitchUserContext(session, userContext);\n");
		sb.append(formatter.currentIndent()
				+ "currentUserContext = userContext;\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n");
		
		sb.append(formatter.currentIndent()
				+ "const std::string routineName(\"" + getJBCImplSubroutineName() + "\");\n");
		sb.append(formatter.currentIndent()
				+ "// Create the argument list to call the subroutine with\n");
		sb.append(formatter.currentIndent()
				+ "tafc::session::ArgList args;\n");
		for(UMLParaCppDescriptor para : getInputParameters())  
			sb.append(formatter.currentIndent() 
				+ "args.push_back(" + para.getJBCParaName() + ".Get());\n");
		if(getReturnParameter() != null)
			sb.append(formatter.currentIndent()
				+ "args.push_back(" + getReturnParameter().getJBCParaName() + ".Get());\n");
		sb.append(formatter.currentIndent()
				+ "args.push_back(responseDetailVar.Get());\n\n");
		
		sb.append(formatter.currentIndent()
				+ "//We are ready, call the subroutine \n");
		sb.append(formatter.currentIndent() 
				+ "executeRoutine(routineName, args, responseDetail);\n\n");
		
		return sb.toString();
	}
	
	/*
	 * when directly call JBC subroutine, one flag should be used to
	 * indicate how many JBC VAR structs are passed
	 */
	private String getJBCSubroutineExpectedFlag() {
		StringBuilder sb = new StringBuilder();
		
		int numberOfPara = 0;
		numberOfPara += getInputParameters().length;
		
		if(getReturnParameter() != null)
			numberOfPara++;
		
		//also count responseDetailVar;
		numberOfPara++;
		
		for(int i = 0; i < numberOfPara; i++)
			sb.append("V");
		
		return sb.toString();
	}
	
	/************************************************************************
	 * 
	 * JBC proxy adaptor to wrap c++ proxy adaptor
	 * 
	 ***********************************************************************/
	//the name of JBC proxy adaptor subroutine to wrap C++ proxy adaptor	
	public String getJBCProxyAdaptorSubroutineName() {
		StringBuilder sb = new StringBuilder();
		sb.append("I" + getParent().getServiceProxyAdaptorName()
				+ "."
				+ StringUtils.lowerInitialCharacter(m_umlOperationName));
		return sb.toString();
	}
	//C++ name of JBC proxy adaptor subroutine to wrap C++ proxy adaptor
	public String getJBCProxyAdaptorSubroutineCppName() {
		return JBCSubroutineNameUtils.toTAFCFunctionName(
				getJBCProxyAdaptorSubroutineName());
	}
	//JBC source file name for JBC proxy adaptor subroutine
	public String getJBCProxyAdaptorSubroutineFileName() {
		return getJBCProxyAdaptorSubroutineName() + Constants.JBC_FILE_EXT;
	}
	//define JBC subroutine to wrap C function adaptor
	public String defineJBCProxyAdaptor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(formatter.currentIndent()
				+ "*-----------------------------------------------------------------------------\n");
		
		sb.append(formatter.currentIndent() 
				+ "SUBROUTINE " 
				+ getJBCProxyAdaptorSubroutineName() + "("); 
		for(UMLParaCppDescriptor para : getInputParameters()) {
			sb.append(para.getCppParaName() + ", ");
		}
		sb.append("responseDetail)\n");
		
		sb.append(formatter.currentIndent()
				+ "*-----------------------------------------------------------------------------\n");
		sb.append(formatter.currentIndent()
				+ "*\n");
		sb.append(formatter.currentIndent()
				+ "* Note : This subroutine is auto-generated and should not be changed.\n");
		sb.append(formatter.currentIndent()
				+ "*\n");
		sb.append(formatter.currentIndent() 
				+ "* @author Auto-generated\n");
		sb.append(formatter.currentIndent()
				+ "* @stereotype subroutine\n");		
		sb.append(formatter.currentIndent()
				+ "*\n");
		sb.append(formatter.currentIndent()
				+ "*-----------------------------------------------------------------------------\n");    
		
		formatter.indent();
		sb.append(formatter.currentIndent() 
				+ "CALLC " + getCppProxyAdaptorFuncName() + "(");
		for(UMLParaCppDescriptor para : this.getInputParameters()) {
			sb.append(para.getCppParaName() + ", ");
		}
		sb.append("responseDetail)\n\n");
		
		sb.append(formatter.currentIndent()
				+ "RETURN\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "END\n");
		return sb.toString();
	}
	
	
	/************************************************************************
	 * 
	 * C++ proxy adaptor to allow JBC API to access external service
	 * 
	 ***********************************************************************/
	//the name of C++ proxy adaptor for the given JBC API
	public String getCppProxyAdaptorFuncName() {
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.lowerInitialCharacter(
						getParent().getServiceProxyAdaptorName())				
				+ StringUtils.upperInitialCharacter(m_umlOperationName));
		return sb.toString();
	}	
	//declare C++ proxy adaptor
	public String declareCppProxyAdaptor() {		
		StringBuilder sb = new StringBuilder();
		
		sb.append(getCppProxyAdaptorSignature(false) + ";\n");
		
		return sb.toString();
	}

	//define C function adaptor
	public String defineCppProxyAdaptor() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(getCppProxyAdaptorSignature(true) + " {\n");
		
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ CppConstants.SOA_LOGGER_CLASS 
				+ "& logger = "
				+ CppConstants.SOA_LOGGER_CLASS 
				+ "::GetInstance();\n");
		sb.append(defineCppParameters() + "\n");
		
		sb.append(formatter.currentIndent()
				+ "//define RepsonseDetail object for JBC VAR, repsonseDetailVar\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_CLASS 
				+ " responseDetail;\n\n");
		
		sb.append(formatter.currentIndent()
				+ "// We need a TAFCSession to call internal operations. But we only have a dp.\n");
		sb.append(formatter.currentIndent()
				+ "// So 'fake up' a session pointer based on the dp.\n");
		sb.append(formatter.currentIndent()
				+ "tafc::session::TAFCSession localSession(dp);\n");
		sb.append(formatter.currentIndent()
				+ "tafc::session::TAFCSession *session = &localSession;\n\n");
		
		sb.append(callCppProxyService() + "\n");

		sb.append(formatter.currentIndent() 
				+  CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::ToVAR(session, responseDetail, responseDetailVar);\n");
		
		sb.append(formatter.currentIndent() 
				+ "return(resultVar);\n");
		formatter.outdent();
		
		sb.append(formatter.currentIndent() 
				+ "}\n\n");		
		
		return sb.toString();
	}
	
	//C function adaptor signature
	public String getCppProxyAdaptorSignature(boolean isInCppFile) {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent());
		if(!isInCppFile)
			sb.append("DLLDECL ");
		
		sb.append("VAR* "
				+ getCppProxyAdaptorFuncName() + "(\n");		
		
		formatter.indent();
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+ "struct jBASEDataAreas *dp,\n");
//				+ "tafc::session::TAFCSession *session,\n");		
		sb.append(formatter.currentIndent() + "VAR* resultVar,\n");
		
		for(UMLParaCppDescriptor umlPara : m_parameters) {		
			sb.append(formatter.currentIndent() + "VAR* "		
				+ umlPara.getJBCParaName()
			  + ",\n");
		}		
		
		sb.append(formatter.currentIndent()
				+ "VAR* responseDetailVar)");
		
		formatter.outdent();
		formatter.outdent();
		
		return sb.toString();
	}
	
	//define corresponding c++ parameter for each JBC VAR struct
	//parameter specified in C function adatpor
	private String defineCppParameters() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			sb.append(formatter.currentIndent() 
					+ "//define c++ variable for the given JBC parameter,"
					+ para.getJBCParaName() + "\n");
			sb.append(para.defineCppVar());
			sb.append("\n");
		}
		
		if(m_returnParameter != null) {
			sb.append(formatter.currentIndent() 
					+ "//define c++ variable for JBC return parameter\n");
			sb.append(m_returnParameter.defineCppVar());
		}
		
		return sb.toString();
	}
	
	//business logic define in C function adaptor 
	//to invoke corresponding method of service proxy
	private String callCppProxyService() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		sb.append(formatter.currentIndent()
				+ "try {\n");
		formatter.indent();
		
		//convert each IN and/or INPUT JBC VAR struct to corresponding
		//c++ variable
		sb.append(converJBCInputParaToCppPara() + "\n");
		
		//create service instance object 
		//and invoke corresponding member function defined in service proxy 
		sb.append(createServiceInstanceAndInvoke() + "\n");		
		
		//convert each INOUT, OUT and RETURN c++ variable 
		//to corresponding JBC VAR struct
		sb.append(convertOutputCppParaToJBCPara() + "\n");
				
		formatter.outdent();
		
		//catch SOAException, then standard exception, 
		//then all other exception. 
		//this is because JBC should handle exception,
		//not C/C++ proxy adaptor and proxy
		sb.append(formatter.currentIndent()
				+ "} catch("
				+ CppConstants.SOA_EXCEPTION_CLASS 
				+ "& soaException) {\n");
		formatter.indent();			
		sb.append(formatter.currentIndent() 
				+"if(logger.IsEnabled())\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "logger.Log(soaException.GetFaultInfo(), __FILE__, __LINE__);\n\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::SOAExceptionToResponseDetail("
				+ "soaException, responseDetail);\n");		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "} catch(std::exception& ex) {\n");
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+"if(logger.IsEnabled()) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::string errMsg(ex.what());\n");
		sb.append(formatter.currentIndent()
				+ "logger.Log(errMsg, __FILE__, __LINE__);\n");
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::ExceptionToResponseDetail("
		    +	"ex, responseDetail);\n");	
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "} catch(...) {\n");
		formatter.indent();		
		sb.append(formatter.currentIndent() 
				+"if(logger.IsEnabled()) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::string errMsg(\"Unknown Exception\");\n");
		sb.append(formatter.currentIndent()
				+ "logger.Log(errMsg, __FILE__, __LINE__);\n");
		formatter.outdent();
		sb.append(formatter.currentIndent() 
				+ "}\n\n");
		sb.append(formatter.currentIndent() 
				+ CppConstants.SOA_RESPONSE_DETAIL_HANDLER
				+ "::UnknowExceptionToResponseDetail("
		    +	"responseDetail);\n");	
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		
		return sb.toString();
	}
	//convert each IN and/or INOUT parameter from
	//JBC VAR struct to corresponding c++ variable
	private String converJBCInputParaToCppPara() {
		StringBuilder sb = new StringBuilder();
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			Direction direction = para.getDirection();
			if((direction == Direction.IN)
					|| (direction == Direction.INOUT)) {
				sb.append(para.convertJBCVarToCppVar(true));
				sb.append("\n");
			}
		}
		return sb.toString();
	}
		
	//convert each INOUT and OUT parameter from
	//c++ variable to corresponding JBC VAR struct
	private String convertOutputCppParaToJBCPara() {
		StringBuilder sb = new StringBuilder();
		
		for(UMLParaCppDescriptor para : getInputParameters()) {
			Direction direction = para.getDirection();
			if((direction == Direction.INOUT)
					|| (direction == Direction.OUT)
					|| (direction == Direction.RETURN)) {			
				sb.append(para.convertCppVarToJBCVar(true));
				sb.append("\n");
			}
		}
		
		if(m_returnParameter != null) {
			sb.append(m_returnParameter.convertCppVarToJBCVar(true));
		}		
		
		return sb.toString();
	}
	
	//create c++ service proxy instance 
	//and invoke corresponding member function defined in that service proxy
	private String createServiceInstanceAndInvoke() {
		Formatter formatter = Formatter.getInstance();
		StringBuilder sb = new StringBuilder();
		
		//define function pointer variable used in TAFC
		//and get pointer to create service method
		sb.append(formatter.currentIndent()
				+ getParent().getServiceProxyName() 
				+ "* (*fptr)() = NULL;\n");
		sb.append(formatter.currentIndent()
				+ "fptr = "  
				+ "reinterpret_cast<" + getParent().getServiceProxyName() + "* (*)()>(JediObjectFindFunction(session->getNonConstDp(), \""
				+ getParent().getCreateProxyMethodName() + "\", 0));\n");
		
		//check if the function pointer is NULL or not
		sb.append(formatter.currentIndent()
				+ "if(fptr == NULL) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::string errMsg(\"Failed to get the function pointer,"
				+ getParent().getCreateProxyMethodName() + "\");\n");
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
				+ "throw ex;\n");		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		
		//invoke create service C function
		sb.append(formatter.currentIndent()
				+ "std::auto_ptr<" + getParent().getServiceProxyName() + "> " 
				+ getParent().getServiceInstanceVarName()
				+ "((*fptr)());\n");
				
		//check if instance is created or not
		sb.append(formatter.currentIndent()
				+ "if(" + getParent().getServiceInstanceVarName() + ".get() == NULL) {\n");
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "std::string errMsg(\"Failed to create instance of "
				+ getParent().getServiceName() + "\");\n");
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
				+ "throw ex;\n");		
		formatter.outdent();
		sb.append(formatter.currentIndent()
				+ "}\n\n");
		
		//set context callback
		sb.append(formatter.currentIndent()
				+ "std::auto_ptr<temenos::soa::common::ContextCallback> " 
				+ getParent().getServiceContextCallbackVarName() + "(\n");
		formatter.indent();
		formatter.indent();
		sb.append(formatter.currentIndent()
				+ "temenos::soa::common::T24ContextCallBackFactory::createT24ContextCallback(session));\n");
		formatter.outdent();
		formatter.outdent();
				
		sb.append(formatter.currentIndent()
				+ getParent().getServiceInstanceVarName()
				+ "->SetContextCallback(" + getParent().getServiceContextCallbackVarName() + ".get());\n\n");
		
		if(m_returnParameter == null) {//no return parameter
			sb.append(formatter.currentIndent()
				+ getParent().getServiceInstanceVarName() + "->"
				+ getCppName() + "(");
		} else {//has return parameter
			sb.append(formatter.currentIndent() 
					+ m_returnParameter.getCppParaName() + " = "
					+ getParent().getServiceInstanceVarName() + "->"
					+ getCppName() + "(");
		}
		for(UMLParaCppDescriptor para : getInputParameters())
			sb.append(para.getCppParaName() + ",");
		sb.append("responseDetail);\n");		
		
		return sb.toString();
	}
}
