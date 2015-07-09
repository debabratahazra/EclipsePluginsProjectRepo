package com.odcgroup.page.linking;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IFragmentProvider;

import com.google.inject.Inject;

/**
 * This is a custom fragment provider for page models.
 * It makes use of the qualified name provider to return a fragment for an object.
 * In order to retrieve the object for a fragment, it simply iterates over all 
 * content of the model.
 * 
 * @author Kai Kreuzer
 *
 */
public class PageFragmentProvider implements IFragmentProvider {

	  @Inject
	  private IQualifiedNameProvider qualifiedNameProvider;

	  public String getFragment(EObject obj, Fallback fallback) {
	    QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(obj);
	    return qualifiedName != null ? convertToString(qualifiedName) : fallback.getFragment(obj);
	  }

	  private String convertToString(QualifiedName qualifiedName) {
			StringBuilder builder = new StringBuilder();
			boolean isFirst = true;
			for (String segment : qualifiedName.getSegments()) {
				if (!isFirst)
					builder.append("/");
				isFirst = false;
				builder.append(segment);
			}
			return builder.toString();
	}

	public EObject getEObject(Resource resource, 
	                            String fragment, 
	                            Fallback fallback) {
	    if (fragment != null) {
	      Iterator<EObject> i = EcoreUtil.getAllContents(resource, false);
	      while(i.hasNext()) {
	        EObject eObject = i.next();
	        String candidateFragment = (eObject.eIsProxy()) 
	            ? ((InternalEObject) eObject).eProxyURI().fragment()
	            : getFragment(eObject, fallback);
	        if (fragment.equals(candidateFragment)) 
	          return eObject;
	      }
	    }
	    return fallback.getEObject(fragment);
	  }
}
