package com.odcgroup.pageflow.editor.diagram.custom.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.workbench.editors.properties.ISelectionAdapter;

/**
 *
 * @author pkk
 *
 */
public class PageflowValidationProvider implements ISelectionAdapter {
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.ISelectionAdapter#adaptModel(java.lang.Object)
	 */
	@Override
	public EObject adaptModel(Object object) {
		EObject model = null;
		if (object != null && object instanceof EditPart) {
			EditPart ep = (EditPart) object;
			if(ep.getModel() instanceof View) {
				View view = (View) ep.getModel();
				model = view.getElement();
			}	
		}
		return model;
	}
	

}
