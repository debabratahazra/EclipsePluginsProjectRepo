package com.odcgroup.domain.annotations;

import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class MdfAnnotationsUtil {

    public static void setAnnotationProperty(MdfModelElement model, String namespace, String annotationName, String propertyName, String propertyValue, boolean isCDATA) {
        MdfAnnotationPropertyImpl p = (MdfAnnotationPropertyImpl)MdfFactory.eINSTANCE.createMdfAnnotationProperty();
        p.setName(propertyName);
        p.setValue(propertyValue);
        p.setCDATA(isCDATA);
        
        MdfAnnotationImpl annotation = (MdfAnnotationImpl)model.getAnnotation(namespace, annotationName);
        if (annotation == null) {
            annotation = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
            annotation.setNamespace(namespace);
            annotation.setName(annotationName);
            model.getAnnotations().add(annotation);
        }

        MdfAnnotationProperty existingAnnotation = annotation.getProperty(propertyName);
		if (existingAnnotation != null) {
			annotation.getProperties().remove(existingAnnotation);
		}
		if (propertyValue != null) {
			annotation.getProperties().add(p);
		}
    }

    public static void setAnnotationValue(MdfModelElement model, String namespace, String annotationName, String value) {

    	MdfAnnotationPropertyImpl p = (MdfAnnotationPropertyImpl)MdfFactory.eINSTANCE.createMdfAnnotationProperty();
    	p.setName("value");
    	p.setValue(value);
    	p.setCDATA(false);

    	MdfAnnotationImpl annotation = (MdfAnnotationImpl)model.getAnnotation(namespace, annotationName);
    	if (annotation == null) {
    		annotation = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
    		annotation.setNamespace(namespace);
    		annotation.setName(annotationName);
    		model.getAnnotations().add(annotation);
    	}

    	MdfAnnotationProperty existingAnnotation = annotation.getProperty(p.getName());
    	if (existingAnnotation != null) {
    		annotation.getProperties().remove(existingAnnotation);
    	}
    	if (value != null) {
    		annotation.getProperties().add(p);
    	}
    }

    public static void removeAnnotation(MdfModelElement model, String namespace, String annotationName) {
    	MdfAnnotation annotation = model.getAnnotation(namespace, annotationName);
    	model.getAnnotations().remove(annotation);
    }
}
