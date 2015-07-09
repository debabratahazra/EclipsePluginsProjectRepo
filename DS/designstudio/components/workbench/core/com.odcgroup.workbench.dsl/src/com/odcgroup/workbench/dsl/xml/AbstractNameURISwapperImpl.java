package com.odcgroup.workbench.dsl.xml;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

public abstract class AbstractNameURISwapperImpl implements XtextToNameURISwapper {
	
	// intentionally protected - there should be no need for this prefix to be visible outside.
	// Please use the getNameFromProxy() helper to access the content of name:/ URI Proxies.
	protected static final String NAME_SCHEME = "name";
	
	public <T extends EObject> T cloneAndReplaceAllReferencesByNameURIProxies(T rootObject ,Resource originalResource) {
		// Note that EcoreUtil2.clone / ECoreUtil.copy (should) already resolve all proxies in the original
		T clonedRootObject = EcoreUtil.copy(rootObject);
		replaceReferences(originalResource, clonedRootObject, getXTextByNameURIReferenceReplacer());
		return clonedRootObject;
	}
	
	abstract ReferenceReplacer getXTextByNameURIReferenceReplacer();
	
	protected <T extends EObject> void replaceReferences(Resource originalResource, T clonedRootObject, ReferenceReplacer r) {
        Map<EObject, Collection<Setting>> map = EcoreUtil.CrossReferencer.find(Collections.singleton(clonedRootObject));
        for (Map.Entry<EObject, Collection<EStructuralFeature.Setting>> entry : map.entrySet()) {
            EObject crossReference = entry.getKey();
            for (Setting setting : entry.getValue()) {
                EReference eReference = (EReference)setting.getEStructuralFeature();
                
                if (eReference.isContainment())
                    continue;
                if (!eReference.isChangeable())
                    continue;

                final EObject object = setting.getEObject();
                if (!eReference.isMany()) {
                    EObject newProxyOrProxy = r.replaceReference(originalResource, object, eReference, crossReference);
                    setting.set(newProxyOrProxy);
                } else {
                    @SuppressWarnings("unchecked")
                    List<EObject> list = (List<EObject>) setting.get(false); // == object.eGet(eReference);
                    for (int i = 0; i < list.size(); i++) {
                        EObject crossReferenceInList = list.get(i);
                        EObject newProxyOrProxy = r.replaceReference(originalResource, object, eReference, crossReferenceInList);
                        list.set(i, newProxyOrProxy);
                    }
                }
            }
        }
    }
	
	protected interface ReferenceReplacer {
		EObject replaceReference(Resource originalResource, EObject eObject, EReference eReference, EObject crossReference);
	}
	
}
