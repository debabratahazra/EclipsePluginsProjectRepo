package com.odcgroup.mdf.compare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.compare.diff.engine.GenericDiffEngine;
import org.eclipse.emf.compare.diff.engine.check.ReferencesCheck;
import org.eclipse.emf.compare.diff.metamodel.DiffFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.match.metamodel.Match2Elements;
import org.eclipse.emf.compare.match.metamodel.Match3Elements;
import org.eclipse.emf.compare.match.metamodel.MatchPackage;
import org.eclipse.emf.compare.match.metamodel.Side;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author pkk
 *
 */
public class MdfDiffEngine extends GenericDiffEngine {	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.engine.GenericDiffEngine#getReferencesChecker()
	 */
	protected ReferencesCheck getReferencesChecker() {
		return new MdfReferencesCheck(matchCrossReferencer);
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.engine.GenericDiffEngine#checkMoves(org.eclipse.emf.compare.diff.metamodel.DiffGroup, org.eclipse.emf.compare.match.metamodel.Match2Elements)
	 */
	protected void checkMoves(DiffGroup root, Match2Elements matchElement) {
		//ignore the moves, as most of the moves are because of the sorting
		// also ignore the moves by mdfenumvalue
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.engine.GenericDiffEngine#checkMoves(org.eclipse.emf.compare.diff.metamodel.DiffGroup, org.eclipse.emf.compare.match.metamodel.Match3Elements)
	 */
	protected void checkMoves(DiffGroup root, Match3Elements matchElement) {
		//ignore the moves, as most of the moves are because of the sorting
		// also ignore the moves by mdfenumvalue
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.diff.engine.GenericDiffEngine#processUnmatchedElements(org.eclipse.emf.compare.diff.metamodel.DiffGroup, java.util.List)
	 */
	protected void processUnmatchedElements(DiffGroup diffRoot, List<UnmatchElement> unmatched) {
		final List<UnmatchElement> filteredUnmatched = new ArrayList<UnmatchElement>(unmatched.size());
		for (final UnmatchElement element : unmatched) {
			if (!(element.getElement() instanceof EGenericType)) {
				filteredUnmatched.add(element);
			}
		}
		for (final UnmatchElement unmatchElement : filteredUnmatched) {
			final EObject element = unmatchElement.getElement();
			if (unmatchElement.getSide() == Side.RIGHT) {
				// add RemoveModelElement
				final ModelElementChangeRightTarget operation = DiffFactory.eINSTANCE
						.createModelElementChangeRightTarget();
				operation.setRightElement(element);
				// Container will be null if we're adding a root
				if (element.eContainer() != null) {
					operation.setLeftParent(getMatchingEObject(element.eContainer()));
					addInContainerPackage(diffRoot, operation, getMatchingEObject(element.eContainer()));
				} else {
					operation.setLeftParent(element.eContainer());
					addInContainerPackage(diffRoot, operation, element.eContainer());
				}
			} else {
				// add AddModelElement
				final ModelElementChangeLeftTarget operation = DiffFactory.eINSTANCE
						.createModelElementChangeLeftTarget();
				operation.setLeftElement(element);
				// Container will be null if we're adding a root
				if (element.eContainer() != null) {
					operation.setRightParent(getMatchingEObject(element.eContainer()));
					addInContainerPackage(diffRoot, operation, getMatchingEObject(element.eContainer()));
				} else {
					operation.setRightParent(element.eContainer());
					addInContainerPackage(diffRoot, operation, element.eContainer());
				}
			}
		}
	}
	
	/**
	 * had to override this behavior as the matchingObject from parent
	 * implementation results in the same object
	 * 
	 * @param from
	 * @return
	 */
	private  EObject getMatchingEObject(EObject from) {
		EObject matchedEObject = null;
		final Collection<EStructuralFeature.Setting> settings = matchCrossReferencer.get(from);
		if (settings == null)
			return null;
		for (final org.eclipse.emf.ecore.EStructuralFeature.Setting setting : settings) {
			if (setting.getEObject() instanceof Match2Elements) {
				if (setting.getEStructuralFeature().getFeatureID() == MatchPackage.MATCH2_ELEMENTS__LEFT_ELEMENT) {
					matchedEObject = ((Match2Elements)setting.getEObject()).getRightElement();
					if (matchedEObject==from) {
						matchedEObject = ((Match2Elements)setting.getEObject()).getLeftElement();
					}
				} else if (setting.getEStructuralFeature().getFeatureID() == MatchPackage.MATCH2_ELEMENTS__RIGHT_ELEMENT) {
					matchedEObject = ((Match2Elements)setting.getEObject()).getLeftElement();
					if (matchedEObject==from) {
						matchedEObject = ((Match2Elements)setting.getEObject()).getRightElement();
					}
				}
			}
		}
		return matchedEObject;
	}

}
