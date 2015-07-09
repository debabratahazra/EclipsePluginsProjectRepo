package com.odcgroup.mdf.ecore.annotation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EcorePlusAspect {

	private static final String NAMESPACE = "ecoreplus";
	
	public static final TypedBooleanEAnnotation<EStructuralFeature> BusinessKey = new TypedBooleanEAnnotation<EStructuralFeature>(NAMESPACE, "businessKey");
	public static final TypedStringEAnnotation<EEnum> EnumBaseType = new TypedStringEAnnotation<EEnum>(NAMESPACE, "baseType");
	public static final TypedStringEAnnotation<EEnumLiteral> EnumLiteralValue = new TypedStringEAnnotation<EEnumLiteral>(NAMESPACE, "value");

}
