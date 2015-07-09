package com.odcgroup.process.diagram.custom.properties.filters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Node;

import com.odcgroup.process.diagram.edit.parts.ServiceTaskEditPart;

/**
 * @author pkk
 *
 */
public class ServiceTaskPropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof ServiceTaskEditPart){
			ServiceTaskEditPart uep = (ServiceTaskEditPart)toTest;
			Node node = (Node)uep.getModel();
			return node.getElement();
		}
		return null;
	}

}
