package com.odcgroup.cdm.generation.util;

import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * utility methods for MDF.
 * 
 * @author Gary Hayes
 */
public class MdfUtils {

	/**
	 * Creates an annotation.
	 * 
	 * @param name
	 *            The name of the annotation and the property
	 * @param namespace
	 *            The namespace
	 * @return MdfAnnotation The newly created MdfAnnotation
	 */
	public static MdfAnnotation createMdfAnnotation(String namespace, String name) {
		MdfFactory factory = MdfFactory.eINSTANCE;

		MdfAnnotationImpl a = (MdfAnnotationImpl) factory.createMdfAnnotation();
		a.setNamespace(namespace);
		a.setName(name);

		return a;
	}

	/**
	 * Gets the value of an MdfAnnotationProperty.
	 * 
	 * @param element
	 *            The MdfModelElement to get the annotation from
	 * @param namespace
	 *            The namespace
	 * @param name
	 *            The name of the annotation
	 * @param propertyName
	 *            The property name
	 * @return String The value
	 */
	public static String getAnnotationPropertyValue(MdfModelElement element, String namespace, String name,
			String propertyName) {
		MdfAnnotation a = element.getAnnotation(namespace, name);
		if (a == null) {
			return "";
		}
		MdfAnnotationProperty ap = a.getProperty(propertyName);
		if (ap == null) {
			return "";
		}
		return ap.getValue();
	}
}