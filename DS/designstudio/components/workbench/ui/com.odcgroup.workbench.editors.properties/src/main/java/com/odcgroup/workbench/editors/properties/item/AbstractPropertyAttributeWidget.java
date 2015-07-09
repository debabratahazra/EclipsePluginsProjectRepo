package com.odcgroup.workbench.editors.properties.item;

import org.eclipse.emf.ecore.EAttribute;

import com.odcgroup.workbench.editors.properties.model.IPropertyAttribute;

/**
 *
 * @author pkk
 *
 */
public abstract class AbstractPropertyAttributeWidget extends AbstractPropertyWidget implements IPropertyAttribute {
	
	/**
	 * @param attribute
	 * @param label
	 * @param tooltip
	 */
	public AbstractPropertyAttributeWidget(EAttribute attribute, String label, String tooltip) {
		super(attribute, label, tooltip);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyAttribute#getAttribute()
	 */
	public EAttribute getAttribute() {
		return (EAttribute) getStructuralFeature();
	}	

}
