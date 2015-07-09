package com.odcgroup.process.diagram.custom.properties.filters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;

/**
 * @author pkk
 *
 */
public class ProcessPropertyFilter extends AbstractPropertyFilter {

	@Override
	public EObject fetchEObject(Object toTest) {
		if (toTest instanceof ProcessEditPart){
			ProcessEditPart pep = (ProcessEditPart)toTest;
			return ((Diagram)pep.getModel()).getElement();
		}
		return null;
	}

}
