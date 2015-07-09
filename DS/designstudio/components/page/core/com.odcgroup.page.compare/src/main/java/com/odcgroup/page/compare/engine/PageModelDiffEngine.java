package com.odcgroup.page.compare.engine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.compare.diff.metamodel.DiffFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.match.metamodel.Side;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.model.Property;
import com.odcgroup.workbench.compare.engine.BaseModelDiffEngine;

/**
 * @author pkk
 *
 */
public class PageModelDiffEngine extends BaseModelDiffEngine {
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.engine.GenericDiffEngine#processUnmatchedElements(org.eclipse.emf.compare.diff.metamodel.DiffGroup, java.util.List)
	 */
	protected void processUnmatchedElements(DiffGroup diffRoot, List<UnmatchElement> unmatched) {
		final List<UnmatchElement> filteredUnmatched = new ArrayList<UnmatchElement>(unmatched.size());
		for (final UnmatchElement element : unmatched) {
			if (!(element.getElement() instanceof EGenericType) 
					&& !isUnwantedProperty(element.getElement())) {
				filteredUnmatched.add(element);
			}
		}
		for (final UnmatchElement unmatchElement : filteredUnmatched) {
			final EObject element = unmatchElement.getElement();
			final EObject matchedParent = getMatchedEObject(element.eContainer());
			if (unmatchElement.getSide() == Side.RIGHT) {
				// add RemoveModelElement
				final ModelElementChangeRightTarget operation = DiffFactory.eINSTANCE
						.createModelElementChangeRightTarget();
				operation.setRightElement(element);
				operation.setLeftParent(matchedParent);
				if (unmatchElement.isRemote()) {
					operation.setRemote(true);
				}
				addInContainerPackage(diffRoot, operation, matchedParent);
			} else {
				// add AddModelElement
				final ModelElementChangeLeftTarget operation = DiffFactory.eINSTANCE
						.createModelElementChangeLeftTarget();
				operation.setLeftElement(element);
				operation.setRightParent(matchedParent);
				if (unmatchElement.isRemote()) {
					operation.setRemote(true);
				}
				addInContainerPackage(diffRoot, operation, matchedParent);
			}
		}
	
	}
	
	/**
	 * @param object
	 * @return
	 */
	private boolean isUnwantedProperty(Object object) {
		if (object instanceof Property) {
			Property property = (Property) object;
			if (property.getTypeName().equals("collapsible") 
					|| property.getTypeName().equals("collapsed")
					|| property.getTypeName().equals("format")) {
				return true;
			}
		}
		return false;
	}

}
