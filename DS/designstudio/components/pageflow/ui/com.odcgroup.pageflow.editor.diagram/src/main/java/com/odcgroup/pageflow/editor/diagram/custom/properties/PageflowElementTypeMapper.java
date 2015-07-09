package com.odcgroup.pageflow.editor.diagram.custom.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.gmf.runtime.notation.impl.EdgeImpl;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper;

public class PageflowElementTypeMapper extends AbstractTypeMapper {

	/**
	 * Constructor for ModelElementTypeMapper.
	 */
	public PageflowElementTypeMapper() {
		super();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper#mapType(java.lang.Object)
	 */
	public Class mapType(Object input) {
		Class type = super.mapType(input);
		if (input instanceof EditPart) {
			Object tmpObj = ((EditPart) input).getModel();
			if (tmpObj instanceof EObject) {
				type = getEObjectType((EObject) tmpObj);
			} else {
				type = tmpObj.getClass();
			}
		} else if (input instanceof EObject) {
			return getEObjectType((EObject) input);
		}
		return type;
	}

	/**
	 * Returns the type of the EObject. Subclasses may override.
	 * 
	 * @param eObj  EObject whose type is being examined
	 * @return Type of the EObject
	 */
	protected Class getEObjectType(EObject eObj) {
		if (eObj instanceof NodeImpl) {
			NodeImpl node = (NodeImpl) eObj;
			EObject ob = node.getElement();
			if (ob != null)
				return ob.getClass();
			else
				return eObj.getClass();
		} else if (eObj instanceof EdgeImpl) {
			EdgeImpl edge = (EdgeImpl) eObj;
			if (edge.getElement() != null){
				return edge.getElement().getClass();
			}
		} else if (eObj instanceof DiagramImpl) {
			DiagramImpl diagram = (DiagramImpl) eObj;
			return diagram.getElement().getClass();
		}
		return eObj.getClass();
	}
}