package com.odcgroup.pageflow.editor.diagram.custom.properties.filter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Edge;

import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionEditPart;

public class TransitionPropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof TransitionEditPart){
			TransitionEditPart tep = (TransitionEditPart)toTest;
			return ((Edge)tep.getModel()).getElement();
		}
		return null;
	}

}
