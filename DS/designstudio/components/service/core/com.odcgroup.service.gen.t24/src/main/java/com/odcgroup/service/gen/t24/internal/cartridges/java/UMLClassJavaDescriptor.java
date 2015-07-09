package com.odcgroup.service.gen.t24.internal.cartridges.java;

import java.util.ArrayList;

import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLClassJavaDescriptor {
	
	private String umlName;
	private ArrayList<UMLClassAttrJavaDescriptor> attrDescriptorList;
	
	private UMLServiceJavaDescriptor parent;
	
	public UMLClassJavaDescriptor(ClassDefDescriptor classDefDescriptor,
																UMLServiceJavaDescriptor parent) {
		umlName = classDefDescriptor.getName();
		
		attrDescriptorList = new ArrayList<UMLClassAttrJavaDescriptor>();
		
		this.parent = parent;
		
		initClassAttr(classDefDescriptor);
	}
	
	private void initClassAttr(ClassDefDescriptor classDefDecriptor) {
		if(classDefDecriptor != null) {
			if(classDefDecriptor.getAttributes() != null) {
				for(AttributeDescriptor attrDescriptor : classDefDecriptor.getAttributes()) {
				
				}
			}			
		}
	}
	
	public String getName() {
		return umlName;
	}
	
	public String getClassName() {
		return StringUtils.upperInitialCharacter(getName());
	}
	
	public String getPackageName() {
		return Constants.T24_SERVICE_PACKAGE_PREFIX + "data."
			+ StringUtils.lowerInitialCharacter(getName()); 
	}
}
