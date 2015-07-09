package com.odcgroup.workbench.tap.validation.internal;

import org.eclipse.emf.validation.model.IClientSelector;

public class ValidationClientSelector implements IClientSelector {
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.model.IClientSelector#selects(java.lang.Object)
	 */
	public boolean selects(Object object) {
		return true;
	}

}
