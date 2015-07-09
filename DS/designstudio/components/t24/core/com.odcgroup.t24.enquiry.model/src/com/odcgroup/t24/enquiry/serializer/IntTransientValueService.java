package com.odcgroup.t24.enquiry.serializer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

@SuppressWarnings("restriction")
public class IntTransientValueService extends Bug469806TransientValueService {

	@Override // @see DS-8617
	public ValueTransient isValueTransient(EObject semanticObject, EStructuralFeature feature) {
		if (feature instanceof EAttribute) {
			if (feature.getEType() == EcorePackage.eINSTANCE.getEInt())
				if (semanticObject.eGet(feature) == feature.getDefaultValue()) {
					return ValueTransient.NO;
				}
		}
		return super.isValueTransient(semanticObject, feature);
	}

}
