package com.odcgroup.workbench.core.internal.resources;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.resources.OfsModelResource;

public class InternalResourceUtils {
	
	/**
	 * This method merges two element structures into one.
	 * 
	 * @param el elements of the first structure
	 * @param e2 elements of the second structure
	 * 
	 * @return the merged package structure
	 */
	static public Collection<? extends IOfsElement> merge(Collection<? extends IOfsElement> e1, Collection<? extends IOfsElement> e2) {
		Collection<IOfsElement> mergedList = new LinkedList<IOfsElement>();
		
		Map<URI, IOfsElement> e1Uris = getURIMap(e1);
		Map<URI, IOfsElement> e2Uris = getURIMap(e2);

		final Collection<URI> disjunctUris = CollectionUtils.disjunction(e1Uris.keySet(), e2Uris.keySet());
		
		// define predicate to create disjunct package collection
		Predicate predicate = new Predicate() {
			public boolean evaluate(Object obj) {
				if(obj instanceof IOfsElement) {
					IOfsElement element = (IOfsElement) obj;
					return disjunctUris.contains(element.getURI());
				} else {
					return false;
				}
			}
		};

		// first add all packages to the result that do not exist in both packages
		for(IOfsElement element : (Collection<IOfsElement>) CollectionUtils.select(
				CollectionUtils.union(e1, e2), predicate)) {
			mergedList.add(element);
		}
		
		for(URI uri : (Collection<URI>) CollectionUtils.intersection(e1Uris.keySet(), e2Uris.keySet())) {
			IOfsElement element1 = e1Uris.get(uri);
			IOfsElement element2 = e2Uris.get(uri);
			
			if(element1==null || element2 == null) continue;

			// select the element with the higher priority (= smaller scope value)
			// and use this for further processing
			IOfsElement highPrioElement = null;
			if((element1.getScope() & element2.getScope()) > 0) {
				// one seems to include the other, so select the one with more bits set
				highPrioElement = (element1.getScope()>element2.getScope())?element1:element2;
			} else {
				// likely both have only one bit set, so take the one with higher prio
				highPrioElement = (element1.getScope()<element2.getScope())?element1:element2;
			}
			
			// set the new scope to the combined value
			if(highPrioElement instanceof IOfsModelPackage) {
				highPrioElement.setScope(element1.getScope() | element2.getScope());
				mergedList.add(highPrioElement);
			} else if(highPrioElement instanceof IOfsModelResource) {
				// if it's a IOfsModelResource, we need to create a new instance because we must not change the scope value of 
				// existing instances in the ModelResourceSet
				OfsModelResource highPrioResource = (OfsModelResource) highPrioElement;
				IOfsModelResource mergedResource = new OfsModelResource(highPrioResource.getOfsProject(), 
						highPrioResource.getURI(), highPrioResource.getStorage(), element1.getScope() | element2.getScope());
				mergedList.add(mergedResource);
			}
		}
		
		return mergedList;
	}

	static private Map<URI, IOfsElement> getURIMap(Collection<? extends IOfsElement> packages) {
		Map<URI, IOfsElement> uriMap = new HashMap<URI, IOfsElement>();
		for(IOfsElement pkg : packages) {
			uriMap.put(pkg.getURI(), pkg);
		}
		return uriMap;
	}
}
