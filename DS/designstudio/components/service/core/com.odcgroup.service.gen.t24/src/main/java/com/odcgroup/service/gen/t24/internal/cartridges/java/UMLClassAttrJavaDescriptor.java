package com.odcgroup.service.gen.t24.internal.cartridges.java;

import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;

public class UMLClassAttrJavaDescriptor {
	private String umlName;
	
	public UMLClassAttrJavaDescriptor(ClassDefDescriptor classDescriptor) {
		umlName = classDescriptor.getName();
	}
	
	public String getName() {
		return umlName;
	}	
}
