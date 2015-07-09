package com.odcgroup.process.model.translation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * @author yan
 */
public interface IProcessElementAdapter {

	public EObject getAdaptee();
	
	@SuppressWarnings("unchecked")
	public EList getTranslations();
	
}
