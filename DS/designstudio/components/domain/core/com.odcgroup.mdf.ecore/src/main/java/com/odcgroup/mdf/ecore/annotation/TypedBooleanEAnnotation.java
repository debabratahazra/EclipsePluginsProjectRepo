package com.odcgroup.mdf.ecore.annotation;

import org.eclipse.emf.ecore.EModelElement;

public class TypedBooleanEAnnotation<ModelElementType extends EModelElement>
		extends TypedEAnnotation<ModelElementType, Boolean> {

	public TypedBooleanEAnnotation(String namespace, String name) {
		super(namespace, name, Boolean.class);
	}

	@Override
	protected String toString(Boolean annotationValue) {
		return annotationValue.toString();
	}

	@Override
	protected Boolean toAnnotation(String annotationAsString) {
		return Boolean.valueOf(annotationAsString);
	}

}
