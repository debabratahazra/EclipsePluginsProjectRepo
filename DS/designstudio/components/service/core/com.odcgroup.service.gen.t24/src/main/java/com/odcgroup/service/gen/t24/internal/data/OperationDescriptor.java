package com.odcgroup.service.gen.t24.internal.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
import com.odcgroup.service.model.component.AccessSpecifier;

public class OperationDescriptor extends ElementDescriptor {
	private String service;	
	private String upperName;	// Name with upper case initial character
	private String lowerName;	// Name with lower case initial character
	private String tafjName;
	private String tafjProxyInterfaceName;
	
	// Non-return parameters
	private List<ParamDescriptor> parameters;
	private ParamDescriptor returnParameter;
	private boolean ifContainsPrimitveParams;
	private boolean ifContainsCollTypeParams;
	private AccessSpecifier scope;
	
	// INOUT and OUT parameters
	private List<ParamDescriptor> outParameters = new ArrayList<ParamDescriptor>();

	public OperationDescriptor(String service, String name, List<ParamDescriptor> parameters, ParamDescriptor returnParamDescriptor) {
		super(name);
		init(service, name, parameters, returnParamDescriptor, AccessSpecifier.PUBLIC);
	}

	public OperationDescriptor(String service, String name, List<ParamDescriptor> parameters, ParamDescriptor returnParamDescriptor,AccessSpecifier scope) {
		super(name);
		init(service, name, parameters, returnParamDescriptor,scope);
	}

	private void init(String service, String name, List<ParamDescriptor> parameters, ParamDescriptor returnParamDescriptor,AccessSpecifier scope) {
		
		this.service = service;
		this.upperName = StringUtils.upperInitialCharacter(name);
		this.lowerName = StringUtils.lowerInitialCharacter(name);
		this.tafjName = getT24ImplTafjClassName();
		this.tafjProxyInterfaceName = getProxyAdaptorInterfaceTafjClassName();
		this.returnParameter = returnParamDescriptor;
		this.parameters = parameters;
		this.scope = scope;
		setOutParameterDetails();
		setIfContainsPrimitveParams(parameters);
		setIfContainsCollTypeParams(parameters);
	}	
	
	public String getService() {
		return service;
	}
	
	public String getUpperName() {
		return upperName;
	}

	public String getLowerName() {
		return lowerName;
	}

	public String getTafjName() {
		return tafjName;
	}
	
	public String getTafjProxyInterfaceName() {
		return tafjProxyInterfaceName;
	}

	/** 
	 * @return list of non-return parameters (i.e. IN, OUT and INOUT) for this operation
	 */
	public List<ParamDescriptor> getParameters() {
		return parameters;
	}

	public ParamDescriptor getReturnParameter() {
		return returnParameter;
	}
	
	/** 
	 * @return a unique list complex class names for this operation
	 */
	public Set<String> getComplexClassNames() {
		Set<String> classes = new HashSet<String>();
		for (ParamDescriptor param: parameters) {
			if (param.getComplexity() == Complexity.COMPLEX) {
				classes.add(param.getTypeName());
			}
		}
		
		return classes;		
	}
	
	public List<ParamDescriptor> getOutParameters() {
		return outParameters;
	}
	
	// Work out which are the INOUT and OUT parameters of this operation
	private void setOutParameterDetails() {
		for (ParamDescriptor param: parameters) {
			if ( (param.getDirection() == Direction.INOUT) || (param.getDirection() == Direction.OUT) ) 
			{
				outParameters.add(param);
			}
		}
	}

	@Override
	public String toString() {		
		return String.format("OperationDescriptor: name=%s, upper name=%s, lower name=%s, return param=%s, parameters=%s, out parameters=%s, stereotypes=%s", getName(), upperName, lowerName, returnParameter, parameters, outParameters, getStereotypes());
	}
	
	public long getOpResClassSerialVersionUID() {
		long serialUID = 0L;
		for (ParamDescriptor param : getParameters()) {
			serialUID += param.getSerialVersionUID();
		}
		return serialUID;
	}
	
	private String getT24ImplTafjClassName()
	{
		return getTafjClassName("T24" + this.service + "Impl_" + this.lowerName);
	}
	
	private String getProxyAdaptorInterfaceTafjClassName() {
		return getTafjClassName("I" + this.service + "ProxyAdaptor." + this.lowerName);
	}
	
	private String getTafjClassName(String basicName) {
		StringBuilder tafjName = new StringBuilder(); 
		int noLowerChars = 0;
		for ( int i = 0; i < basicName.length(); i++ ) {
			char c = convertInvalidChar(basicName.charAt(i));
			tafjName.append(c);
			if ( isLowerCaseChar(c)) {
			    noLowerChars++;
			}
		}

		if ( noLowerChars > 0 ) {
			tafjName.append('_');
			tafjName.append(noLowerChars);
		}

		return tafjName.append("_cl").toString();
	}

	private boolean isLowerCaseChar(char c) {
		// is c a lower case character?
		return (c >= 'a') && (c <= 'z');
	}
	
	private char convertInvalidChar(char c) {
		if (c == '.') {
			return '_';
		} else {
			return c;
		}
	}
	
	/**
	 * This method will return custom marker separated parameters list as String
	 * 
	 * @param separator this could be @AM, @VM or @SVM
	 * @return String, Custom marker separated parameters List e.g. 'abc' : @AM : 'def'
	 */
	public String getParamNamesAsStringForJBC(String jbcMarker){
		String argsName = "''";
		int paramCount = 0;
		for(ParamDescriptor parameter : parameters) {
			if (paramCount == 0) {
				argsName = "'" + parameter.getLowerCamelCaseName() + "'";
				paramCount++;
			} else {
				argsName = (new StringBuilder()).append(argsName).append(" : ").append(jbcMarker).append(" : '").append(parameter.getLowerCamelCaseName()).append("'").toString();
			}
		}
		return argsName;
	}
	
	/**
	 * This method will return @AM separated parameters value list as String
	 * 
	 * @return String, @AM separated parameters Value List e.g. LOWER(abc) : @AM : LOWER(def)
	 */
	public String getParamValuesAsStringForJBC(){
		String argsValue = "''";
		int paramCount = 0;
		for(ParamDescriptor parameter : parameters) {
			if (paramCount == 0) {
				argsValue = "LOWER(" + parameter.getLowerCamelCaseName() + ")";
				paramCount += 1;
			} else {
				argsValue = (new StringBuilder()).append(argsValue).append(" : @AM : LOWER(").append(parameter.getLowerCamelCaseName()).append(")").toString();
			}
		}
		return argsValue;
	}
	
	public String getOperationParamsForRMI() {
		return getWSOperationParams();
	}
	
	public String getWSOperationParams() {
		StringBuilder operationStr = new  StringBuilder();
		int i = 0;
		for(ParamDescriptor parameter : parameters) {
			if (parameter.getDirection() != Direction.OUT){
				i++;
				if (i > 1) {
					operationStr.append(", ");
				}
				if (parameter.getCardinality() == Cardinality.SINGLE) {
					operationStr.append(parameter.getTypeName());
				} else {
					operationStr.append(parameter.getTypeName()).append("[]");
				}
				operationStr.append(" ").append(parameter.getName());
			}
		}
		return operationStr.toString();
	}
 
	// Check if we have single primitive parameter, useful at code generation time
	private void setIfContainsPrimitveParams (List<ParamDescriptor> parameters) {
		if (parameters != null && parameters.size() > 0) {
			for (ParamDescriptor param : parameters) {
				if (param.getComplexity().equals(Complexity.PRIMITIVE)) {
					ifContainsPrimitveParams = true;		// Set to true and break
					break;
				}
			}
		} else {
			ifContainsPrimitveParams = false;
		}
	}
	
	public boolean ifContainsPrimitiveParams() {
		return ifContainsPrimitveParams;
	}
	
	// Check if Operation have any Collection of parameters, useful at code generation time
	private void setIfContainsCollTypeParams(List<ParamDescriptor> parameters) {
		if (parameters != null && parameters.size() > 0) {
			for (ParamDescriptor param : parameters) {
				if (param.getCardinality().equals(Cardinality.MULTIPLE)) {
					ifContainsCollTypeParams = true;		// Set to true and break
					break;
				}
			}
		} else {
			ifContainsCollTypeParams = false;
		}
	}
	
	public boolean getIfContainsCollTypeParams() {
		return ifContainsCollTypeParams;
	}

	public AccessSpecifier getAccessSpecifier() {
		return scope;
	}
}
