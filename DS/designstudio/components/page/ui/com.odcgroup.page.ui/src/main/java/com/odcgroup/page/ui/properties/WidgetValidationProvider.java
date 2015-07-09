package com.odcgroup.page.ui.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;

import com.odcgroup.workbench.editors.properties.ISelectionAdapter;

/**
 *
 *
 * @author pkk
 *
 */
public class WidgetValidationProvider implements ISelectionAdapter {

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.ISelectionAdapter#adaptModel(java.lang.Object)
	 */
	@Override
	public EObject adaptModel(Object object) {
		if (object != null && object instanceof EditPart) {
			EditPart ep = (EditPart) object;
			return (EObject) ep.getModel();
		}
		return null;
	}

}
