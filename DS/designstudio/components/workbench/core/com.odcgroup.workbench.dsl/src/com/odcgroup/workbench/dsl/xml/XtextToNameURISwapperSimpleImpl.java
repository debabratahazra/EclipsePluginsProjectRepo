package com.odcgroup.workbench.dsl.xml;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.linking.lazy.LazyURIEncoder;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.Triple;

import com.google.common.base.Preconditions;

/**
 * Alternative XtextToNameURISwapper implementation, which contrary to
 * NameURISwapperImpl can deal with unresolved Proxies.
 * 
 * @author Michael Vorburger
 */
public class XtextToNameURISwapperSimpleImpl extends AbstractNameURISwapperImpl {

    // TODO re-merge with NameURISwapperImpl? Initially this code was quite
    // different from the original NameURISwapperImpl, but now that it handles
    // non-proxy scenario also, it doesn't make sense to have two different
    // impls anymore?

    /* @Inject */IResourceServiceProvider.Registry reg = IResourceServiceProvider.Registry.INSTANCE;

    // should @Inject, but works like this
    private static final LazyURIEncoder encoder = new LazyURIEncoder();

    @Override
    ReferenceReplacer getXTextByNameURIReferenceReplacer() {
	return new XtextToNameSimpleReferenceReplacer();
    }

    private class XtextToNameSimpleReferenceReplacer implements
	    ReferenceReplacer {
		@Override
		public EObject replaceReference(Resource originalResource, EObject eObject, EReference eReference,
				EObject crossReference) {
			boolean isXText = false;
			if (crossReference.eIsProxy()) {
				URI proxyURI = ((InternalEObject) crossReference).eProxyURI();
				String fragment = proxyURI.fragment();
				if (fragment != null && fragment.startsWith(LazyURIEncoder.XTEXT_LINK)) {
					isXText = true;
				}
			}
			if (!crossReference.eIsProxy() || isXText) {
				String name = getName(originalResource, crossReference);
				final URI uri = URI.createURI(NAME_SCHEME + ":/" + name + "#");
				final EObject newProxy = EcoreUtil3.createProxy(uri, eReference.getEReferenceType());
				return newProxy;
			}
			return crossReference;
		}

		private String getName(Resource originalResource, EObject crossReference) {
			if (!crossReference.eIsProxy()) {
				Resource crossResource = crossReference.eResource();
				Resource resource;
				// check if the resource exists & also check the resource is
				// valid
				if (crossResource != null && crossResource.getURI().fileExtension() != null) {
					resource = crossResource;
				} else {
					resource = originalResource;
				}
				
				
				
				QualifiedName fqn = getNameProvider(resource).getFullyQualifiedName(crossReference);
				return getNameConverter(resource).toString(fqn); // DS-7001
			} else {
				URI proxyURI = ((InternalEObject) crossReference).eProxyURI();
				String uriFragment = proxyURI.fragment();

				Triple<EObject, EReference, INode> triple = encoder.decode(originalResource, uriFragment);
				INode node = triple.getThird();

				String text = NodeModelUtils.getTokenText(node);
				return text;
			}
		}
    }

    private IQualifiedNameProvider getNameProvider(Resource resource) {
    	return getResourceServiceProvider(resource).get(IQualifiedNameProvider.class);
    }

    private IQualifiedNameConverter getNameConverter(Resource resource) {
    	return getResourceServiceProvider(resource).get(IQualifiedNameConverter.class);
    }

	private IResourceServiceProvider getResourceServiceProvider(Resource resource) {
		Preconditions.checkNotNull(resource, "resource == null");
		IResourceServiceProvider rsp = reg.getResourceServiceProvider(resource.getURI());
		Preconditions.checkNotNull(rsp, "No reg. ResourceServiceProvider for URI: %s", resource.getURI());
		return rsp;
	}
    

}
