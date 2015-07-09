package com.odcgroup.workbench.compare.gmf.engine;

import java.util.Map;

import org.eclipse.emf.compare.match.engine.GenericMatchEngine;
import org.eclipse.emf.compare.match.metamodel.Match2Elements;
import org.eclipse.emf.compare.match.metamodel.MatchElement;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Anchor;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;

public class GMFMatchEngine extends GenericMatchEngine {
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.match.engine.GenericMatchEngine#resourceMatch(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, java.util.Map)
	 */
	public MatchModel resourceMatch(Resource leftResource, Resource rightResource,
			Map<String, Object> optionMap) throws InterruptedException {
		MatchModel match = super.resourceMatch(leftResource, rightResource, optionMap);
		return removeDiagramElements(match);
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.match.engine.GenericMatchEngine#resourceMatch(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, java.util.Map)
	 */
	public MatchModel resourceMatch(Resource leftResource, Resource rightResource, Resource ancestorResource,
			Map<String, Object> optionMap) throws InterruptedException {
		MatchModel match =  super.resourceMatch(leftResource, rightResource, ancestorResource, optionMap);
		return removeDiagramElements(match);
	}

	/**
	 * @param matchModel
	 * @return
	 */
	private MatchModel removeDiagramElements(MatchModel matchModel) {
		// remove all unmatched diagram elements
		UnmatchElement[] unmatchedElements = (UnmatchElement[]) matchModel.getUnmatchedElements().toArray();
		for(UnmatchElement unmatchedElement : unmatchedElements) {
			if(isDiagramElement(unmatchedElement.getElement())) {
				matchModel.getUnmatchedElements().remove(unmatchedElement);
			}
		}
		
		// remove all matched diagram elements
		if(matchModel.getMatchedElements().size()==1) {
			MatchElement rootMatchedElement = (MatchElement) matchModel.getMatchedElements().get(0);
			MatchElement[] matchedElements = (MatchElement[]) rootMatchedElement.getSubMatchElements().toArray();
			for(MatchElement matchedElement : matchedElements) {
				if(matchedElement instanceof Match2Elements) {
					Match2Elements match2Elements = (Match2Elements) matchedElement;
					if(isDiagramElement(match2Elements.getLeftElement())) {
						rootMatchedElement.getSubMatchElements().remove(matchedElement);
					}
				}
			}
		}
		return matchModel;
	}
	
	/**
	 * @param object
	 * @return
	 */
	private boolean isDiagramElement(Object object) {
		if(object instanceof View) 				return true;
		if(object instanceof Bendpoints) 		return true;
		if(object instanceof LayoutConstraint) 	return true;
		if(object instanceof Style) 			return true;
		if(object instanceof Anchor) 			return true;

		return false;
	}

	
}
