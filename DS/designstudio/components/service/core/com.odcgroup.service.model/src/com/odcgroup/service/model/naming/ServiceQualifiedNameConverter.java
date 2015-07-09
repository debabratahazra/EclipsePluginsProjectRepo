package com.odcgroup.service.model.naming;

import org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl;

public class ServiceQualifiedNameConverter extends DefaultImpl {
	@Override
	public String getDelimiter() {
		return ":";
	}
}
