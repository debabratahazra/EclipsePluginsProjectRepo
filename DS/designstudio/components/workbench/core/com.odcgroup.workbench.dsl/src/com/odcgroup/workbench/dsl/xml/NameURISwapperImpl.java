/*******************************************************************************
 * Copyright (c) 2012 Michael Vorburger (http://www.vorburger.ch).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.odcgroup.workbench.dsl.xml;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.inject.Inject;

/**
 * Default Implementation of NameURISwapper.
 *
 * @author Michael Vorburger
 */
public class NameURISwapperImpl extends AbstractNameURISwapperImpl implements NameURISwapper {

	private @Inject IQualifiedNameProvider nameProvider;
    private @Inject IQualifiedNameConverter nameConverter;
    private @Inject IScopeProvider scopeProvider;

    @Override
	public void replaceAllNameURIProxiesByReferences(EObject rootObject) {
        replaceReferences(rootObject.eResource(), rootObject, new NameURIByXTextReferenceReplacer());
    }
    
    private class XTextByNameURIReferenceReplacer implements ReferenceReplacer {
        @Override
        public EObject replaceReference(Resource originalResource, EObject eObject, EReference eReference, EObject crossReference) {
            //System.out.println("Going to replace: " + eReference + "\n  in: " + eObject + "\n  from currently " + crossReference);
            if (crossReference.eIsProxy()) {
                URI proxyURI = ((InternalEObject)crossReference).eProxyURI();
                if (NAME_SCHEME.equals(proxyURI.scheme()))
                    // Sometimes CrossReferencer gives us duplicates, which we already processed..
                    return crossReference;
                        
                String nodeText = "";
                List<INode> nodes = NodeModelUtils.findNodesForFeature(eObject, eReference);
                if (nodes.size() > 0)
                    nodeText = NodeModelUtils.getTokenText(nodes.get(0));
                throw new IllegalArgumentException(NameURISwapperImpl.class + " found a Proxy when an non-Proxy EObject was expected - why did it not get resolved before reaching this point? URI=" + crossReference.toString() + ", eReference=" + eReference + ", NodeText=" + nodeText);
            }
            
//            final IScope scope = scopeProvider.getScope(setting.getEObject(), eReference);
//            final IEObjectDescription objectDescription = scope.getSingleElement(crossReference);
//            final QualifiedName qName = objectDescription.getQualifiedName();
            final QualifiedName qName = nameProvider.getFullyQualifiedName(crossReference);

            if (qName == null)
                throw new IllegalArgumentException(NameURISwapperImpl.class + " IQualifiedNameProvider returned null for " + crossReference.toString());
            final String name = nameConverter.toString(qName);
            if (name == null)
                throw new IllegalArgumentException(NameURISwapperImpl.class + " IQualifiedNameConverter returned null for " + qName);
            
            // Note the URI's '#' suffix... this is needed because otherwise the
            // org.eclipse.emf.ecore.xmi.impl.XMLHandler's setValueFromId()
            // does not create Proxy objects but just ignores such href from XML.
            final URI uri = URI.createURI(NAME_SCHEME + ":/" + name + "#"); 
            final EObject newProxy = EcoreUtil3.createProxy(uri, eReference.getEReferenceType());

            return newProxy;
       }
	}

    private class NameURIByXTextReferenceReplacer implements ReferenceReplacer {
        @Override
        public EObject replaceReference(Resource originalResource, EObject eObject, EReference eReference, EObject crossReference) {
            final URI uri = EcoreUtil.getURI(crossReference);
            if (NAME_SCHEME.equals(uri.scheme())) {
                // path() ignores the '#' which we added above
                String crossRefString = uri.path().substring(1);
                
                if(crossRefString.contains("/")) {
                	crossRefString = crossRefString.replaceAll("/", "_");
                	final URI newUri = URI.createURI(NAME_SCHEME + ":/" + crossRefString + "#"); 
                    ((InternalEObject)crossReference).eSetProxyURI(newUri);
                }
                
                final QualifiedName name = nameConverter.toQualifiedName(crossRefString);
                
                final IScope scope = scopeProvider.getScope(eObject, eReference);
                final IEObjectDescription objectDescription = scope.getSingleElement(name);
                
                if (objectDescription != null) {
                    return objectDescription.getEObjectOrProxy();
                } else {
					// If we can't find Name in Scope then the referenced Object
					// may not exist in the Workspace yet (or has not yet been
					// indexed by the Builder).  We leave the name:/ URI Proxy
                	// as is - and have the NameURISupportingCrossReferenceSerializer
                	// deal with it!
                	return crossReference;
                }
                
            } else {
                return crossReference;
            }
        }
    }
    
    static public String getNameFromProxy(EObject proxy) {
    	if (proxy.eIsProxy()) {
    		final URI proxyURI = EcoreUtil.getURI(proxy);
    		if (NAME_SCHEME.equals(proxyURI.scheme())) {
    			return proxyURI.path().substring(1); 
    		}
    	}
        return null;
    }

	@Override
	ReferenceReplacer getXTextByNameURIReferenceReplacer() {
		return new XTextByNameURIReferenceReplacer();
	}
}