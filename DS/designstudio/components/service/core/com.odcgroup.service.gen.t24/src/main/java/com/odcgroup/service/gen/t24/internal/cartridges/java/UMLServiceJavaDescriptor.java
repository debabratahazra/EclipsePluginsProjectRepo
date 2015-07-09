package com.odcgroup.service.gen.t24.internal.cartridges.java;

import java.util.ArrayList;

import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.JBCSubroutineNameUtils;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLServiceJavaDescriptor {
	private String umlName;
	private ArrayList<UMLOperationJavaDescriptor> umlOperationList;
	private ArrayList<UMLClassJavaDescriptor> umlClassList;
	
	public UMLServiceJavaDescriptor(ServiceDescriptor serviceDescriptor) {
		umlName = serviceDescriptor.getName();
		
		umlOperationList = new ArrayList<UMLOperationJavaDescriptor>();
		umlClassList = new ArrayList<UMLClassJavaDescriptor>();
		
		init(serviceDescriptor);
	}
	
	private void init(ServiceDescriptor serviceDescriptor) {
		if(serviceDescriptor != null) {
			if(serviceDescriptor.getOperations() != null) {
				for(OperationDescriptor operation : serviceDescriptor.getOperations()) {
					
				}
			}
			
			if(serviceDescriptor.getClassDefDescriptors() != null) {
				for(ClassDefDescriptor classDefDecriptor : serviceDescriptor.getClassDefDescriptors()) {
					UMLClassJavaDescriptor classDescriptor = 
						new UMLClassJavaDescriptor(classDefDecriptor, this);
					
					umlClassList.add(classDescriptor);
				}	
			}
		}
	}
	
	public String getName() {
		return StringUtils.upperInitialCharacter(umlName);
	}
	
	public String getPackageName() {
		return Constants.T24_SERVICE_PACKAGE_PREFIX 
						+ StringUtils.lowerInitialCharacter(getName()); 
	}
	
	public String getServiceAPIName() {
		return StringUtils.upperInitialCharacter(getName()) 
					+ "API";
	}
	
	public String getServiceImplName() {
		return StringUtils.upperInitialCharacter(getName())
    			+ "Impl";
	}
	
	public String jbcGetMetaDataToTAFJClassName() {
		return JBCSubroutineNameUtils.toTAFJClassName(getName() + ".getMetaData");
	}
	
}
