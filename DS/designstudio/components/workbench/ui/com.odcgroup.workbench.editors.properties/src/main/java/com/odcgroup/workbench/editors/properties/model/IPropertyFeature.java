package com.odcgroup.workbench.editors.properties.model;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 *
 * @author pkk
 *
 */
public interface IPropertyFeature extends IPropertyFeatureWidget {
	
	public String getLabel();
	public String getTooltip();
	public EStructuralFeature getStructuralFeature();
	//public void dispose();

}
