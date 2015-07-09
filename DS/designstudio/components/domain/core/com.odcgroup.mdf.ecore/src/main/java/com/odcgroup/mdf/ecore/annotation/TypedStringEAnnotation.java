package com.odcgroup.mdf.ecore.annotation;

import org.eclipse.emf.ecore.EModelElement;

public class TypedStringEAnnotation<ModelElementType extends EModelElement>
		extends TypedEAnnotation<ModelElementType, String> {

	public TypedStringEAnnotation(String namespace, String name) {
		super(namespace, name, String.class);
	}

	@Override
	protected String toString(String annotationValue) {
		return annotationValue;
	}

	@Override
	protected String toAnnotation(String annotationAsString) {
		return annotationAsString;
	}

}
