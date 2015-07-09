package com.odcgroup.pageflow.editor.diagram.custom.properties.filter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

import com.odcgroup.pageflow.editor.diagram.edit.parts.PageflowEditPart;

public class PageflowPropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof PageflowEditPart){
			PageflowEditPart pep = (PageflowEditPart)toTest;
			return ((Diagram)pep.getModel()).getElement();
		}
		return null;
	}

}
