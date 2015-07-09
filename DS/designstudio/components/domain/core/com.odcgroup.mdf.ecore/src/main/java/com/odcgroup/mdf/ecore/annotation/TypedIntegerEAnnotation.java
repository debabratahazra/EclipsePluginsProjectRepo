package com.odcgroup.mdf.ecore.annotation;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;

public class TypedIntegerEAnnotation<ModelElementType extends EModelElement> extends
		TypedEAnnotation<EPackage, Integer> {

	public TypedIntegerEAnnotation(String namespace, String name) {
		super(namespace, name, Integer.class);
	}

	@Override
	protected String toString(Integer annotationValue) {
		return annotationValue.toString();
	}

	@Override
	protected Integer toAnnotation(String annotationAsString) {
		return Integer.valueOf(annotationAsString);
	}

}
