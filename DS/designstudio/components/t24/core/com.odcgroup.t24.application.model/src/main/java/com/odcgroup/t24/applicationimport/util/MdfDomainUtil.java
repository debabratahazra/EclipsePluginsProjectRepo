package com.odcgroup.t24.applicationimport.util;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.util.Triple;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odcgroup.domain.resource.DomainURIEncoder;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.t24.applicationimport.mapper.MappingException;

/**
 * This class provides existing domains for the given name.
 *
 * TODO DS-7289 Integrate this class with the DomainRepository/ModelLoader/ILangRepository/etc.
 * 
 * @see http://rd.oams.com/browse/DS-7424
 *
 * @author kosyakov
 */
@Singleton
public class MdfDomainUtil extends AdapterImpl implements Adapter {
	
	// Implementation note: This implements Adapter not so that it can @Override
	// notifyChanged(), but in order to be able to mdfDomain.eAdapters().add(this)
	// and then if (mdfDomain.eAdapters().contains(this)), to avoid iterating
	// over already loaded resource again as a domain could be loaded several
	// times.
	
	@Inject
	private LinkingHelper linkingHelper;
	
	@Inject
	private LazyURIEncoder lazyURIEncoder;
	
	@Inject
	private DomainURIEncoder domainURIEncoder;

	public MdfDomainImpl getExisitingDomain(IResourceDescriptions index, String name, ResourceSet context)
			throws MappingException {
		for (IEObjectDescription objectDescription : index.getExportedObjects(MdfPackage.Literals.MDF_DOMAIN,
				QualifiedName.create(name), false)) {
			EObject object = context.getEObject(objectDescription.getEObjectURI(), true);
			if (object != null) {
				return resolveAll((MdfDomainImpl) object);
			} else {
				throw new MappingException(
						"Huh, how can we find an IEObjectDescription, but then not be able to getEObject for its URI from the ResourceSet - corrupt or stale index?! "
								+ objectDescription.toString());
			}
		}
		return null;
	}

	public MdfDomainImpl resolveAll(MdfDomainImpl mdfDomain) {
		if (mdfDomain.eAdapters().contains(this)) {
			return mdfDomain;
		}
		mdfDomain.eAdapters().add(this);
		mdfDomain.eContainer();
		resolveCrossReferences(mdfDomain);
		for (Iterator<EObject> i = mdfDomain.eAllContents(); i.hasNext();) {
			EObject childEObject = i.next();
			resolveCrossReferences(childEObject);
		}
		return mdfDomain;
	}

	private void resolveCrossReferences(EObject eObject) {
		Iterator<EObject> i = eObject.eCrossReferences().iterator();
		while (i.hasNext()) {
			EObject next = i.next();
			if (next.eIsProxy()) {
				InternalEObject internalEObject = (InternalEObject) next;
				URI eProxyURI = internalEObject.eProxyURI();
				Resource resource = eObject.eResource();
				String uriFragment = eProxyURI.fragment();
				if (domainURIEncoder.isBrokenCrossLinkFragment(resource, uriFragment)) {
					continue;
				} else if (lazyURIEncoder.isCrossLinkFragment(resource, uriFragment)) {
					Triple<EObject, EReference, INode> triple = lazyURIEncoder.decode(resource, uriFragment);
					String crossRefString = linkingHelper.getCrossRefNodeAsString(triple.getThird(), true);
					URI newProxyURI = eProxyURI.appendFragment(domainURIEncoder.encodeBrokenLink(crossRefString));
					internalEObject.eSetProxyURI(newProxyURI);
				} else {
					throw new IllegalStateException("Unresolved proxy with unknown schema: " + next);
				}
			}
		}
	}

}
