package com.odcgroup.menu.editor.properties;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.workbench.editors.properties.ISelectionAdapter;

/**
 * @author snn
 *
 */
public class MenuValidationProvider implements ISelectionAdapter {

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.ISelectionAdapter#adaptModel(java.lang.Object)
	 */
	public EObject adaptModel(Object object) {
		if (object instanceof EObject) {
			return (EObject) object;
		}
		return null;
	}

}
