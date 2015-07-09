package com.odcgroup.mdf.ecore.annotation;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EcoreFactory;

/**
 * Helper for strongly typing EMF eCore EAnnotations.
 * 
 * @param <ModelElementType> EModelElement sub-typed to be annotated (e.g. EClass etc.)
 * @param <AnnotationType> Type of Annotation (e.g. Boolean, Integer, String)
 * 
 * @author Michael Vorburger
 */
public abstract class TypedEAnnotation<ModelElementType extends EModelElement, AnnotationType> {
	
	protected final String annotationName;
	protected final String annotationDetailName;
	protected final Class<AnnotationType> annotationClass;

	public TypedEAnnotation(String namespace, String name, Class<AnnotationType> annotationClass) {
		this.annotationName = namespace;
		this.annotationDetailName = name;
		this.annotationClass = annotationClass;
	}

	public void set(ModelElementType modelElement, AnnotationType annotationValue) {
		EAnnotation eAnnotation = modelElement.getEAnnotation(annotationName);
		if (eAnnotation == null) {
			eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(annotationName);
			modelElement.getEAnnotations().add(eAnnotation);
		}
		final EMap<String, String> details = eAnnotation.getDetails();
		details.put(annotationDetailName, toString(annotationValue));
		
	}

	public AnnotationType get(ModelElementType modelElement) {
		EAnnotation eAnnotation = modelElement.getEAnnotation(annotationName);
		if (eAnnotation == null)
			return null;
		EMap<String, String> details = eAnnotation.getDetails();
		if (details == null)
			return null;
		String annotationAsString = details.get(annotationDetailName);
		return toAnnotation(annotationAsString);
	}

	protected abstract String toString(AnnotationType annotationValue);

	protected abstract AnnotationType toAnnotation(String annotationAsString);
}
