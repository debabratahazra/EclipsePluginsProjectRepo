package com.odcgroup.process.diagram.custom.properties.filters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Node;

import com.odcgroup.process.diagram.edit.parts.UserTaskEditPart;

/**
 * @author pkk
 *
 */
public class UserTaskPropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof UserTaskEditPart){
			UserTaskEditPart uep = (UserTaskEditPart)toTest;
			Node node = (Node)uep.getModel();
			return node.getElement();
		}
		return null;
	}

}
