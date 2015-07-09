package com.odcgroup.page.compare.engine;

import java.util.Map;

import org.eclipse.emf.compare.match.MatchOptions;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.metamodel.UnmatchElement;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.model.Property;
import com.odcgroup.workbench.compare.engine.OfsModelMatchEngine;

/**
 *
 */
public class PageModelMatchEngine extends OfsModelMatchEngine {

	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.match.engine.GenericMatchEngine#resourceMatch(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, java.util.Map)
	 */
	public MatchModel resourceMatch(Resource leftResource, Resource rightResource,
			Map<String, Object> optionMap) throws InterruptedException {
		optionMap.put(MatchOptions.OPTION_IGNORE_XMI_ID, true);
		MatchModel match = super.resourceMatch(leftResource, rightResource, optionMap);
		return removeCollapseProperties(match);
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.compare.match.engine.GenericMatchEngine#resourceMatch(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, java.util.Map)
	 */
	public MatchModel resourceMatch(Resource leftResource, Resource rightResource, Resource ancestorResource,
			Map<String, Object> optionMap) throws InterruptedException {
		optionMap.put(MatchOptions.OPTION_IGNORE_XMI_ID, true);
		MatchModel match =  super.resourceMatch(leftResource, rightResource, ancestorResource, optionMap);
		return removeCollapseProperties(match);
	}


	/**
	 * @param matchModel
	 * @return
	 */
	private MatchModel removeCollapseProperties(MatchModel matchModel) {
		// remove all unmatched diagram elements
		UnmatchElement[] unmatchedElements = (UnmatchElement[]) matchModel.getUnmatchedElements().toArray();
		for(UnmatchElement unmatchedElement : unmatchedElements) {
			if(isUnwantedProperty(unmatchedElement.getElement())) {
				matchModel.getUnmatchedElements().remove(unmatchedElement);
			}
		}
		return matchModel;
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
