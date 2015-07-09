package com.odcgroup.mdf.ext.tangij;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.odcgroup.domain.annotations.MdfAnnotationsUtil;
import com.odcgroup.mdf.ext.AbstractAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.translation.MdfTranslation;


/**
 * MML Aspect used to store translations. This duplicates a part of the logic in the
 * translation layer (but which is ecore related)
 *  
 * @author Michael Vorburger (MVO)
 */
public class TANGIJTranslationAspect extends AbstractAspect {

    /** Namespace URI */
    private static final String NAMESPACE_URI = MdfTranslation.NAMESPACE_URI;
    private static final String TRANSLATION_ANNOTATION = MdfTranslation.TRANSLATION_LABEL;

    /**
     * Get Translations for a Class/Dataset/Attribute.
     * @return Map<String, String>, where the key is a T'A DictLanguage code, and the value the T'A Label/Denomination in that language 
     */
	public static Map<String, String> getTranslations(MdfModelElement model) {
        List<MdfAnnotationProperty> annotationPropertyNames = getAnnotationProperties(model, NAMESPACE_URI, TRANSLATION_ANNOTATION);
        Map<String, String> translations = new HashMap<String, String>(annotationPropertyNames.size());
        for (MdfAnnotationProperty annotationProperty : annotationPropertyNames) {
            translations.put(annotationProperty.getName(), annotationProperty.getValue());
        }
        return translations;
    }

    public static void addTranslation(MdfModelElement model, String taDictLanguageCode, String text) {
    	MdfAnnotationsUtil.setAnnotationProperty(model, NAMESPACE_URI, TRANSLATION_ANNOTATION, taDictLanguageCode, text, false);
    }
    
    // TODO: move to AbstractAspect
    @SuppressWarnings("unchecked")
	protected static List<MdfAnnotationProperty> getAnnotationProperties(MdfModelElement model, String namespace, String annotationName) {
        MdfAnnotation annotation = model.getAnnotation(namespace, annotationName);
        if (annotation != null) {
            return annotation.getProperties();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

}
