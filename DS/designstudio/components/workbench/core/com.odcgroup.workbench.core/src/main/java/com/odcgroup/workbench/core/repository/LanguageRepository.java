package com.odcgroup.workbench.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * TODO merge this class with the ModelLoader.
 */
public abstract class LanguageRepository implements ILanguageRepository {

	private String languageName;
	private IResourceServiceProvider resourceServiceProvider;
	private IResourceServiceProvider.Registry registry = IResourceServiceProvider.Registry.INSTANCE;

	protected final IResourceServiceProvider getResourceServiceProvider() {
		if (resourceServiceProvider == null) {
			resourceServiceProvider = registry.getResourceServiceProvider(URI.createURI("dummy." + languageName));
		}
		return resourceServiceProvider;
	}

	protected IResourceDescriptions getResourceDescriptions() {
		return getResourceServiceProvider().get(IResourceDescriptions.class);
	}
	
	protected IContainer.Manager getContainerManager() {
		return getResourceServiceProvider().get(IContainer.Manager.class);
	}
	
	protected IGlobalScopeProvider getScopeProvider() {
		return getResourceServiceProvider().get(IGlobalScopeProvider.class);
	}

	@Override
	public final String getLanguageName() {
		return this.languageName;
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass eClass) {
		return getResourceDescriptions().getExportedObjectsByType(eClass);
	}
	
	@Override
	public Iterable<IEObjectDescription> getExportedObjects(final EClass type, final QualifiedName name) {
		IResourceDescriptions rd = getResourceServiceProvider().get(IResourceDescriptions.class);
		return rd.getExportedObjects(type, name, false);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass eClass,
			Function1<IEObjectDescription, Boolean> _predicate) {
		Iterable<IEObjectDescription> iterable = getResourceDescriptions().getExportedObjectsByType(eClass);
		return IterableExtensions.<IEObjectDescription> filter(iterable, _predicate);
	}

	public LanguageRepository(String languageName) {
		this(languageName, null);
	}

	public LanguageRepository(String languageName, IResourceServiceProvider resourceServiceProvider) {
		this.languageName = languageName;
		this.resourceServiceProvider = resourceServiceProvider;
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EObject context, EReference reference) {
		IScope scope = getScopeProvider().getScope(context.eResource(), reference, null);
		return scope.getAllElements();
	}
	
	protected List<IContainer> getVisibleContainers(Resource resource) {
		List<IContainer> containers = new ArrayList<IContainer>();
		if (resource != null) {
			IResourceDescriptions index = getResourceDescriptions();
			if (index instanceof ResourceSetBasedResourceDescriptions) {
				ResourceSetBasedResourceDescriptions resourceSetBasedResourceDescriptions = (ResourceSetBasedResourceDescriptions) index;
				if (resourceSetBasedResourceDescriptions.getResourceSet() == null) {
					// This seems to be needed in some cases - incl. from standalone (SE) tests
					resourceSetBasedResourceDescriptions.setContext(resource);
				}
			}
		    IResourceDescription resDesc = index.getResourceDescription(resource.getURI());
		    if (resDesc != null) {
		    	containers.addAll(getContainerManager().getVisibleContainers(resDesc, index));
		    }
		}
		return containers;
	}
	
	@Override
	public  Iterable<IEObjectDescription> getExportedObjectsByType(Resource resource, EClass type) {
		List<IEObjectDescription> objectList = new ArrayList<IEObjectDescription>();
		for (IContainer container : getVisibleContainers(resource)) {
			for (IResourceDescription rd : container.getResourceDescriptions()) {
				for (IEObjectDescription objDesc :  rd.getExportedObjectsByType(type)) {
					objectList.add(objDesc);
				}
			}
		}
		return objectList;
	}	
	
	@Override
	public  Iterable<IEObjectDescription> getExportedObjects(Resource resource, EClass type, QualifiedName name, boolean ignoreCase) {
		List<IEObjectDescription> objectList = new ArrayList<IEObjectDescription>();
		for (IContainer container : getVisibleContainers(resource)) {
			for (IResourceDescription rd : container.getResourceDescriptions()) {
				for (IEObjectDescription objDesc :  rd.getExportedObjects(type, name, ignoreCase)) {
					objectList.add(objDesc);
				}
			}
		}
		return objectList;
	}
	
	@Override
	public  Iterable<IEObjectDescription> getExportedObjectsByType(EObject context, EClass type) {
		return getExportedObjectsByType(context.eResource(), type);
	}

}
