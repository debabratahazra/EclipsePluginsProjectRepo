package com.odcgroup.workbench.generation.cartridge.ng;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eson.resource.EFactoryResource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

// Integration tested in com.odcgroup.domain.workbenchgen.tests.ModelLoaderImplTest
public class ModelLoaderImpl implements ModelLoader {
	private static Logger _logger = LoggerFactory.getLogger(ModelLoaderImpl.class);

	private IResourceServiceProvider.Registry registry = IResourceServiceProvider.Registry.INSTANCE;
	
	private final ResourceSet rs;
	
	protected interface IEObjectDescriptionFinder<T> {
		 boolean findObjects(IResourceDescription rd);
		 boolean singleReturn();
	}

	@Inject
	public ModelLoaderImpl(ResourceSet rs) {
		super();
		this.rs = rs;
	}

	// intentionally package local
	ResourceSet getResourceSet() {
		return rs;
	}
	
	@Override
	public Resource getResource(URI uri) throws Exception {
		Resource r = rs.getResource(uri, true);
		if (r == null)
			throw new IllegalArgumentException("getResource() == null, cannot find/load URI: " + uri.toString());
		return r;
	}

	@Override
	public <T> T getRootEObject(URI uri, Class<T> klass) throws Exception {
		Resource r = getResource(uri);
		return getRootEObject(r);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getRootEObject(Resource r) {
		Preconditions.checkNotNull(r, "null EMF Resource");

		if (r.getContents() == null || r.getContents().isEmpty()) {
			throw new IllegalArgumentException("Loading failed, resource == null, or no contents: " + r.getURI());
		}

		if (r instanceof EFactoryResource) {
			return (T) EFactoryResource.getEFactoryEObject(r);
		} else {
			return (T) r.getContents().get(0);
		}
	}
	
	// ---

	protected <T> T getNamedEObject(EObject context, final QualifiedName qName, final EClass eClass, boolean resolveProxy) {
		final List<T> result = new ArrayList<T>();
		final IEObjectDescriptionFinder finder = new IEObjectDescriptionFinder<T>() {
			@Override
			@SuppressWarnings("unchecked")
			public boolean findObjects(IResourceDescription rd) {
				Iterable<IEObjectDescription> visibleObjectDescr = rd.getExportedObjects(eClass, qName, false);
				for (IEObjectDescription eObjDesc : visibleObjectDescr) {
					result.add((T)eObjDesc.getEObjectOrProxy());
					if (singleReturn()){
						return true;
					}
				}
				return result.size() > 0;
			}

			@Override
			public boolean singleReturn() {
				return true;
			}
		};
		return getNamedEObject(context, result, finder, resolveProxy, qName.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getEObjectByFullName(EObject context, final String[] fullyQualifiedNames, final EClass type, boolean resolveProxy){
		List<IContainer> visibleContainers = getVisibleContainers(context);
		for (IContainer visibleContainer : visibleContainers) {
			for (IResourceDescription visibleResourceDesc : visibleContainer.getResourceDescriptions()) {
				Iterable<IEObjectDescription> visibleObjectDescr= visibleResourceDesc.getExportedObjectsByType(type);
				for (IEObjectDescription oneDesc : visibleObjectDescr){
					String qName = oneDesc.getQualifiedName().toString(); 
					for(String sOneName : fullyQualifiedNames){
						if (sOneName.equals(qName)){
							EObject ret = oneDesc.getEObjectOrProxy();
							if (resolveProxy && ret.eIsProxy()){
								return (T)resolveProxy(ret, context);
							}else{
								return (T)ret;
							} 
						}
					}
				}
			}
		}
		return null;
	}
	
	protected <T> T getNamedEObject(EObject context, final String partialQualifiedName, final EClass eClass, final boolean ignoreCase, final boolean endsOnly, boolean resolveProxy) {
		final List<T> result = new ArrayList<T>();
		final IEObjectDescriptionFinder finder = new IEObjectDescriptionFinder<T>() {
			@Override
			@SuppressWarnings("unchecked")
			public boolean findObjects(IResourceDescription rd) {
				Iterable<IEObjectDescription> visibleObjectDescr= rd.getExportedObjectsByType(eClass);

				final Function1<IEObjectDescription,Boolean> _predicate = new Function1<IEObjectDescription,Boolean>() {
					@Override
					public Boolean apply(IEObjectDescription p) {
						String qName = p.getQualifiedName().toString(); 
						if (ignoreCase){ 
							if (endsOnly){
								return StringUtils.endsWithIgnoreCase(qName, partialQualifiedName);
							}else {
								return StringUtils.containsIgnoreCase(qName, partialQualifiedName);
							}
						} else {
							if (endsOnly){
								return StringUtils.endsWith(qName, partialQualifiedName);
							}else {
								return StringUtils.contains(qName, partialQualifiedName);
							}
						}
					}
				};
				
				IEObjectDescription eObjDesc = IterableExtensions.<IEObjectDescription>findFirst(visibleObjectDescr, _predicate);
				if (eObjDesc != null) {
					result.add((T)eObjDesc.getEObjectOrProxy());
				}
				return result.size() > 0;
			}

			@Override
			public boolean singleReturn() {
				/*
				 * We want it to return the first found.
				 */
				return true;
			}
		};

		return getNamedEObject(context, result, finder, resolveProxy, partialQualifiedName);
	}

	
	protected <T> T getNamedEObject(EObject context, List<T> result, IEObjectDescriptionFinder finder, boolean resolveProxy, String nameOrPatternJustForLog) {
		doGetNamedEObjects(context, finder);
		T sr = getSingleResult(result, nameOrPatternJustForLog);
		if (resolveProxy)
			sr = resolveProxy(sr, context);
		return sr;
	}
	
	protected <T> T getSingleResult(List<T> list, String nameOrPatternJustForLog) {
		if (list.isEmpty()){
			return null;
		}
		
		int minIdx = 0;
		
		if (list.size() > 1) {
			/*
			 * We need to find the smallest name as this will the one closer to the reality.
			 * Required since we are doing a contains (...) in the finder (@see findObjects)
			 * If we replace the contains (...) by Equals (...) this would solve the problem.
			 * Typically, we have the real scenario of 4 Enquiries :
			 * EB_foundation/%COLLATERAL.enquiry
			 * EB_foundation/%COLLATERAL.RIGHT.enquiry
			 * EB_foundation/%COLLATERAL.TYPE.enquiry
			 * SY_Unit/%COLLATERAL.CODE.enquiry
			 * If not returning the smallest, the return is wrong (%COLLATERAL.CODE.enquiry)
			 * 
			 */
			int minSize = Integer.MAX_VALUE;
			int idx = 0;
			for (T oneObject : list){
				if (oneObject instanceof InternalEObject){
					InternalEObject obj = (InternalEObject)oneObject;
					if (obj.eProxyURI() != null && obj.eProxyURI().segmentCount() > 0){
						int size = obj.eProxyURI().segment(obj.eProxyURI().segmentCount()-1).length();
						if (size < minSize){
							minIdx = idx;
							minSize = size;
						}
					}
				}
				idx++;
			}
			
			_logger.warn("Found more than 1 entries in the Xtext index, returning arbitrary smallest one, for the pattern [{}] = {}", nameOrPatternJustForLog, list.size());
		}
		return list.get(minIdx);
	}

	@SuppressWarnings("unchecked")
	protected <T> T resolveProxy(T o, EObject context) {
		context = Preconditions.checkNotNull(context, "context == null");
		if (o == null)
			return null;
		
		EObject eo = (EObject)o;
		if (eo.eIsProxy()) {
			eo = EcoreUtil.resolve(eo, context);
		}
		if (eo.eIsProxy()) {
			URI uri = ((BasicEObjectImpl)eo).eProxyURI();
			if (eo.eIsProxy()) {
				throw new IllegalStateException("Why is this STILL an EMF Proxy?! " + uri);
			}
			// In case you think you're smart and think something like below could be worth a last attempt..
			// .. think twice - that's actually completely wrong - the name will NOT (in the general case) 
			// refer to the root EObject of the resource! @see http://rd.oams.com/browse/DS-7185
			//   NO Resource resource = context.eResource().getResourceSet().getResource(uri, true);
			//   NO eo = getRootEObject(resource);
		}
		return (T) eo;
	}
	
	protected IResourceServiceProvider getResourceServiceProvider(Resource resource) {
		return registry.getResourceServiceProvider(resource.getURI());
	}
	
	protected List<IContainer> getVisibleContainers(EObject context) {

		Resource resource = context.eResource();
		if (resource == null) {
			throw new IllegalArgumentException("The context object ["+context.getClass().getName()+"] must be stored in a resource");
		}
			
		// we normalize the URI in order to properly retrieve its xtext resource description
		URI normalizedURI = resource.getResourceSet().getURIConverter().normalize(resource.getURI());
		
		IResourceServiceProvider rsp = getResourceServiceProvider(resource);
		IResourceDescriptions index = rsp.get(IResourceDescriptions.class);
		
		if (index instanceof ResourceSetBasedResourceDescriptions) {
			ResourceSetBasedResourceDescriptions resourceSetBasedResourceDescriptions = (ResourceSetBasedResourceDescriptions) index;
			if (resourceSetBasedResourceDescriptions.getResourceSet() == null) {
				// This seems to be needed in some cases - incl. from standalone (SE) tests
				resourceSetBasedResourceDescriptions.setContext(context);
			}
		}
		IResourceDescription descr = index.getResourceDescription(normalizedURI);
		if (descr == null) {
			throw new IllegalArgumentException("The resource ["+normalizedURI+"] has no resource description (Xtext)");
		}

		IContainer.Manager containerManager = rsp.getContainerManager();
		List<IContainer> visibleContainers = containerManager.getVisibleContainers(descr, index);
		
		return visibleContainers;
	}
	
	protected void doGetNamedEObjects(EObject context, IEObjectDescriptionFinder finder) {
		List<IContainer> visibleContainers = getVisibleContainers(context);
		for (IContainer visibleContainer : visibleContainers) {
			for (IResourceDescription visibleResourceDesc : visibleContainer.getResourceDescriptions()) {
				if (finder.findObjects(visibleResourceDesc) && finder.singleReturn()){
					return;
				}
			}
		}
	}

	// ---
	
	@Override
	public <T> T getNamedEObject(EObject context, QualifiedName qName, EClass eClass) {
		return getNamedEObject(context, qName, eClass, true);
	}
	
	@Override
	public <T> T getNamedEObjectOrProxy(EObject context, QualifiedName qName, EClass eClass) {
		return getNamedEObject(context, qName, eClass, false);
	}

	@Override
	public <T> T getNamedEObject(EObject context, String partialQualifiedName, EClass eClass) {
		return getNamedEObject(context, partialQualifiedName, eClass, false, false);
	}

	@Override
	public <T> T getNamedEObjectOrProxy(EObject context, String partialQualifiedName, EClass eClass) {
		return getNamedEObjectOrProxy(context, partialQualifiedName, eClass, false, false);
	}

	@Override
	public <T> T getNamedEObject(EObject context, String partialQualifiedName, EClass eClass, boolean ignoreCase, boolean endsOnly) {
		return getNamedEObject(context, partialQualifiedName, eClass, ignoreCase, endsOnly, true);
	}

	@Override
	public <T> T getNamedEObjectOrProxy(EObject context, String partialQualifiedName, EClass eClass, boolean ignoreCase, boolean endsOnly) {
		return getNamedEObject(context, partialQualifiedName, eClass, ignoreCase, endsOnly, false);
	}

}
