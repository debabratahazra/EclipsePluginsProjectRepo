package com.odcgroup.workbench.editors.properties.model;

import org.eclipse.emf.ecore.EObject;

/**
 *
 * @author pkk
 *
 */
public interface IPropertyDefinitionDialog extends IPropertySection {
	
	public EObject getDefinedProperty();
	
	public int open();
	
	public int getReturnCode();
	
	public boolean validate(EObject element);

}
