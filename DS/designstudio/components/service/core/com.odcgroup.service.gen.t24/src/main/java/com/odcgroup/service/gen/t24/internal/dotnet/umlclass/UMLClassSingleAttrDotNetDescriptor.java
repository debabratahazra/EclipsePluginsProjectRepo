package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;

import com.odcgroup.service.gen.t24.internal.utils.StringUtils;


public abstract class UMLClassSingleAttrDotNetDescriptor extends UMLClassAttrDotNetDescriptor{
	//the c++ method name to get non-collection member
	protected String getCppSetMethodName() {
		return "Set" 
			+	StringUtils.upperInitialCharacter(getUMLName());
	}
	
	//the c++ method name to set non-collection member
	protected String getCppGetMethodName() {
		return "Get" 
		+ StringUtils.upperInitialCharacter(getUMLName());
	}	
}
