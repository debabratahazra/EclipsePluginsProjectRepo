package com.odcgroup.pageflow.editor.diagram.custom.properties.filter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Node;

import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateEditPart;

public class DecisionStatePropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof DecisionStateEditPart){
			DecisionStateEditPart sep = (DecisionStateEditPart)toTest;
			Node node = (Node)sep.getModel();
			return node.getElement();
		}
		return null;
	}

}
