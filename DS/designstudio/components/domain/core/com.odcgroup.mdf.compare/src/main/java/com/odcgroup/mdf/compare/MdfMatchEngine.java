package com.odcgroup.mdf.compare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.match.MatchOptions;
import org.eclipse.emf.compare.match.metamodel.Match2Elements;
import org.eclipse.emf.compare.match.metamodel.MatchFactory;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.workbench.compare.engine.OfsModelMatchEngine;

/**
 * @author pkk
 *
 */
public class MdfMatchEngine extends OfsModelMatchEngine {	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.engine.OfsModelMatchEngine#resourceMatch(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, java.util.Map)
	 */
	public MatchModel resourceMatch(Resource leftResource, Resource rightResource,
			Map<String, Object> optionMap) throws InterruptedException {
		optionMap.put(MatchOptions.OPTION_IGNORE_XMI_ID, true);
		optionMap.put(MatchOptions.OPTION_IGNORE_ID, true);
		return super.resourceMatch(leftResource, rightResource, optionMap);
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.engine.OfsModelMatchEngine#resourceMatch(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.resource.Resource, java.util.Map)
	 */
	public MatchModel resourceMatch(Resource leftResource, Resource rightResource, Resource ancestorResource,
			Map<String, Object> optionMap) throws InterruptedException {
		optionMap.put(MatchOptions.OPTION_IGNORE_XMI_ID, true);
		optionMap.put(MatchOptions.OPTION_IGNORE_ID, true);
		return  super.resourceMatch(leftResource, rightResource, ancestorResource, optionMap);
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.engine.OfsModelMatchEngine#mapLists(java.util.List, java.util.List, int, org.eclipse.emf.common.util.Monitor)
	 */
	protected List<Match2Elements> mapLists(List<EObject> list1, List<EObject> list2, int window,
			Monitor monitor) throws FactoryException, InterruptedException {
		final List<Match2Elements> result = new ArrayList<Match2Elements>();
		int curIndex = 0 - window / 2;
		final List<EObject> notFoundList1 = new ArrayList<EObject>(list1);
		final List<EObject> notFoundList2 = new ArrayList<EObject>(list2);

		final Iterator<EObject> it1 = list1.iterator();
		// then iterate over the 2 lists and compare the elements
		while (it1.hasNext() && notFoundList2.size() > 0) {
			final EObject obj1 = it1.next();
			
			EObject obj2 = null;	

			obj2 = findMostSimilar(obj1, notFoundList2);
			if (obj2 != null) {
				// checks if the most similar to obj2 is obj1
				final EObject obj1Check = findMostSimilar(obj2, notFoundList1);
				if (obj1Check != obj1 && obj1Check != null && isSimilar(obj1Check, obj2)) {
					continue;
				}
			}

			if (notFoundList1.contains(obj1) && notFoundList2.contains(obj2) && isSimilar(obj1, obj2)) {
				final Match2Elements mapping = MatchFactory.eINSTANCE.createMatch2Elements();
				final double metric = absoluteMetric(obj1, obj2);

				mapping.setLeftElement(obj1);
				mapping.setRightElement(obj2);
				mapping.setSimilarity(metric);
				result.add(mapping);
				notFoundList1.remove(obj1);
				notFoundList2.remove(obj2);
			}
			curIndex += 1;
			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new InterruptedException();
			}
		}

		// now putting the not found elements aside for later
		stillToFindFromModel1.addAll(notFoundList1);
		stillToFindFromModel2.addAll(notFoundList2);
		return result;
	}
	

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.engine.OfsModelMatchEngine#isSimilar(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	protected boolean isSimilar(EObject obj1, EObject obj2) throws FactoryException {		
		if(obj1 instanceof MdfEntity && obj2 instanceof MdfEntity) {
			MdfEntity left = (MdfEntity) obj1;
			MdfEntity right = (MdfEntity) obj2;
			if (left.getQualifiedName().equals(right.getQualifiedName())) {
				return true;
			}
			return false;
		} else if (obj1 instanceof MdfAttribute && obj2 instanceof MdfAttribute) {
			MdfAttribute left = (MdfAttribute) obj1;
			MdfAttribute right = (MdfAttribute) obj2;
			if (isSimilar((EObject)left.getParentClass(), (EObject) right.getParentClass())) {
				if (left.getQualifiedName().equals(right.getQualifiedName())) {
					return true;
				}
			}
			return false;
		} else if (obj1 instanceof MdfAssociation && obj2 instanceof MdfAssociation) {
			MdfAssociation left = (MdfAssociation) obj1;
			MdfAssociation right = (MdfAssociation) obj2;
			if (isSimilar((EObject)left.getParentClass(), (EObject) right.getParentClass())) {
				if (left.getQualifiedName().equals(right.getQualifiedName())) {
					return true;
				}
			}
			return false;
		} else if (obj1 instanceof MdfReverseAssociation && obj2 instanceof MdfReverseAssociation) {
			MdfReverseAssociation left = (MdfReverseAssociation) obj1;
			MdfReverseAssociation right = (MdfReverseAssociation) obj2;
			if (isSimilar((EObject)left.getAssociation(), (EObject) right.getAssociation())) {
				if (left.getQualifiedName().equals(right.getQualifiedName())) {
					return true;
				}
			}
			return false;
		} else if (obj1 instanceof MdfEnumeration && obj2 instanceof MdfEnumeration) {
			MdfEnumeration left = (MdfEnumeration) obj1;
			MdfEnumeration right = (MdfEnumeration) obj2;			
			if (left.getQualifiedName().equals(right.getQualifiedName())) {
				return true;
			}
			return false;
		} else if (obj1 instanceof MdfEnumValue && obj2 instanceof MdfEnumValue) {
			MdfEnumValue left = (MdfEnumValue) obj1;
			MdfEnumValue right = (MdfEnumValue) obj2;
			if (isSimilar((EObject)left.getParentEnumeration(), (EObject) right.getParentEnumeration())) {
				if (left.getQualifiedName().equals(right.getQualifiedName())) {
					return true;
				}
			}
			return false;
		} else if (obj1 instanceof MdfDatasetProperty && obj2 instanceof MdfDatasetProperty) {
			MdfDatasetProperty left = (MdfDatasetProperty) obj1;
			MdfDatasetProperty right = (MdfDatasetProperty) obj2;
			if (isSimilar((EObject)left.getParentDataset(), (EObject) right.getParentDataset())) {
				if (left.getQualifiedName().equals(right.getQualifiedName())) {
					return true;
				}
			}
			return false;			
		}
		return super.isSimilar(obj1, obj2);
	}
	

}
