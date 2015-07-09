package com.odcgroup.workbench.core.xtext;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * ResourceSet which delegates to another one.
 *
 * @author Michael Vorburger
 */
public abstract class DelegatingResourceSet implements ResourceSet {

	private final ResourceSet delegate;

	protected DelegatingResourceSet(ResourceSet delegate) {
		super();
		this.delegate = delegate;
	}

	@Override public EList<Adapter> eAdapters() {
		return delegate.eAdapters();
	}

	@Override public boolean eDeliver() {
		return delegate.eDeliver();
	}

	@Override public void eSetDeliver(boolean deliver) {
		delegate.eSetDeliver(deliver);
	}

	@Override public void eNotify(Notification notification) {
		delegate.eNotify(notification);
	}

	@Override public EList<Resource> getResources() {
		return delegate.getResources();
	}

	@Override public TreeIterator<Notifier> getAllContents() {
		return delegate.getAllContents();
	}

	@Override public EList<AdapterFactory> getAdapterFactories() {
		return delegate.getAdapterFactories();
	}

	@Override public Map<Object, Object> getLoadOptions() {
		return delegate.getLoadOptions();
	}

	@Override public EObject getEObject(URI uri, boolean loadOnDemand) {
		return delegate.getEObject(uri, loadOnDemand);
	}

	@Override public Resource getResource(URI uri, boolean loadOnDemand) {
		return delegate.getResource(uri, loadOnDemand);
	}

	@Override public Resource createResource(URI uri) {
		return delegate.createResource(uri);
	}

	@Override public Resource createResource(URI uri, String contentType) {
		return delegate.createResource(uri, contentType);
	}

	@Override public Registry getResourceFactoryRegistry() {
		return delegate.getResourceFactoryRegistry();
	}

	@Override public void setResourceFactoryRegistry(Registry resourceFactoryRegistry) {
		delegate.setResourceFactoryRegistry(resourceFactoryRegistry);
	}

	@Override public URIConverter getURIConverter() {
		return delegate.getURIConverter();
	}

	@Override public void setURIConverter(URIConverter converter) {
		delegate.setURIConverter(converter);
	}

	@Override public EPackage.Registry getPackageRegistry() {
		return delegate.getPackageRegistry();
	}

	@Override public void setPackageRegistry(EPackage.Registry packageRegistry) {
		delegate.setPackageRegistry(packageRegistry);
	}

}
