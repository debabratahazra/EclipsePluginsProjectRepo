package com.odcgroup.service.gen.t24.internal.cartridges.java;

import com.odcgroup.service.gen.t24.internal.data.OperationDescriptor;

public class UMLOperationJavaDescriptor {
	private String umlName;
	
	public UMLOperationJavaDescriptor(OperationDescriptor operationDescriptor) {
		umlName = operationDescriptor.getName();
	}
	
	public String getName() {
		return umlName;
	}
}
