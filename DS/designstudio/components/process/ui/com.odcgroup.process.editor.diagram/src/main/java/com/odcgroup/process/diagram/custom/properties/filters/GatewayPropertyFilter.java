package com.odcgroup.process.diagram.custom.properties.filters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Node;

import com.odcgroup.process.diagram.custom.parts.GatewayShapeNodeEditPart;

public class GatewayPropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof GatewayShapeNodeEditPart){
			GatewayShapeNodeEditPart sep = (GatewayShapeNodeEditPart)toTest;
			Node node = (Node)sep.getModel();
			return node.getElement();
		}
		return null;
	}

}
