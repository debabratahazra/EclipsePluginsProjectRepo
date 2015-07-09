package com.odcgroup.mdf.ecore.lazy;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 *
 * @author satishnangi
 *
 */
public class MdfDocumentationAdapter extends AdapterImpl {
	private String documentation;

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		return type == MdfDocumentationAdapter.class;
	}
}
