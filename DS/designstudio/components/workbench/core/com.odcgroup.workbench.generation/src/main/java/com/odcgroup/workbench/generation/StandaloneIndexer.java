package com.odcgroup.workbench.generation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.di.ILangSpecificProviderImpl;

/**
 * Utility for indexing in standalone headless generation scenario.
 *
 * This helper does what the section "// Fill index" of org.eclipse.xtext.builder.standalone.StandaloneBuilder does.
 *
 * @see http://rd.oams.com/browse/DS-8837
 *
 * @author Michael Vorburger
 */
public class StandaloneIndexer {

	private static ILangSpecificProvider<IResourceDescription.Manager> resourceDescriptionManagerProvider
		= new ILangSpecificProviderImpl<IResourceDescription.Manager>(IResourceDescription.Manager.class);

	void doIndex(XtextResourceSet resourceSet) {
		int size = resourceSet.getResources().size();
		List<URI> allResourcesURIs = new ArrayList<URI>(size);
		for (Resource resource : resourceSet.getResources()) {
			allResourcesURIs.add(resource.getURI());
		}
		List<IResourceDescription> emptyResourceDescriptions = new ArrayList<IResourceDescription>(size);
		ResourceDescriptionsData index = new ResourceDescriptionsData(emptyResourceDescriptions);
		Iterator<URI> allResourceIterator = allResourcesURIs.iterator();
		while (allResourceIterator.hasNext()) {
			List<Resource> resources = new ArrayList<Resource>();
			int clusterIndex = 0;
//			boolean doContinue = true;
			while (allResourceIterator.hasNext() /*&& doContinue*/) {
				URI uri = allResourceIterator.next();
				Resource resource = resourceSet.getResource(uri, true);
				resources.add(resource);
				fillIndex(uri, resource, index);
				clusterIndex++;
//				if (!strategy.continueProcessing(resourceSet, null, clusterIndex)) {
//					continue = false
//				}
			}
//			if (!continue)
//				resourceSet.clearResourceSet
		}
		installIndex(resourceSet, index);
	}

	void installIndex(XtextResourceSet resourceSet, ResourceDescriptionsData index) {
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);
	}

	void fillIndex(URI uri, Resource resource, ResourceDescriptionsData index) {
		// description = languageAccess(uri).resourceDescriptionManager.getResourceDescription(resource);

		IResourceDescription.Manager resourceDescriptionManager = resourceDescriptionManagerProvider.get(resource.getURI());
//		IResourceServiceProvider.Registry registry = IResourceServiceProvider.Registry.INSTANCE;
//		IResourceServiceProvider resourceServiceProvider = registry.getResourceServiceProvider(resource.getURI());
//		IResourceDescription.Manager resourceDescriptionManager = resourceServiceProvider.getResourceDescriptionManager();

		IResourceDescription description = resourceDescriptionManager.getResourceDescription(resource);
		index.addDescription(uri, description);
	}

}