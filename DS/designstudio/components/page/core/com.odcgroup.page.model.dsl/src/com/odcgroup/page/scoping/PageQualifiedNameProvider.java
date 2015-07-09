package com.odcgroup.page.scoping;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.page.internal.PageResourceUtil;
import com.odcgroup.page.model.Model;

/**
 * This is a custom qualified name provider for page models.
 * As page models are only referenced as a whole, we only return a result
 * for Model instances. The qualified name is then simply its URI 
 * (without any query or fragment part).
 * 
 * @author Kai Kreuzer
 *
 */
public class PageQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {
	
	@Override
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		if(obj instanceof Model) {
		    URI proxyURI = ((InternalEObject)obj).eProxyURI();
		    if (proxyURI != null) {
		    	return QualifiedName.create(proxyURI.trimQuery().segments());
		    } else {
		    	Resource resource = PageResourceUtil.getResource(obj);
		    	if(resource!=null)
		    		return QualifiedName.create(resource.getURI().trimQuery().segments());
		    }
		}
		return null;
	}
}
