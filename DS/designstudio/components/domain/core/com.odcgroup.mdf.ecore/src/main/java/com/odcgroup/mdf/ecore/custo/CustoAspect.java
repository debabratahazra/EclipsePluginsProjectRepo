package com.odcgroup.mdf.ecore.custo;

import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * moved back from mdf-editor to make it custo-free
 * DS-1349
 *
 */
@SuppressWarnings("unchecked")
public class CustoAspect {

    /** The namespace for declaring custom fields. */
    private static final String NAMESPACE = "http://www.odcgroup.com/mdf/customization";

    /** The name of the annotation for declaring a custom element. */
    private static final String CUSTOM_ANNOTATION = "Custom";

    /** The name of the annotation for declaring a customized element (a modified standard element). */
    private static final String CUSTOMIZED_ANNOTATION = "Customized";

    public static void setCustomElement(MdfModelElement element) {
        MdfAnnotationImpl annot = (MdfAnnotationImpl) MdfFactory.eINSTANCE.createMdfAnnotation();
        annot.setNamespace(NAMESPACE);
        annot.setName(CUSTOM_ANNOTATION);
        element.getAnnotations().add(annot);
    }

    public static void setCustomizedElement(MdfModelElement element) {
        if (!isCustomElement(element) && !isCustomizedElement(element)) {
            MdfAnnotationImpl annot = (MdfAnnotationImpl) MdfFactory.eINSTANCE.createMdfAnnotation();
            annot.setNamespace(NAMESPACE);
            annot.setName(CUSTOMIZED_ANNOTATION);
            element.getAnnotations().add(annot);
        }
    }

    public static boolean isCustomElement(MdfModelElement element) {
        MdfAnnotation annot = element.getAnnotation(NAMESPACE, CUSTOM_ANNOTATION);
        return (annot != null);
    }

    public static boolean isCustomizedElement(MdfModelElement element) {
        MdfAnnotation annot = element.getAnnotation(NAMESPACE, CUSTOMIZED_ANNOTATION);
        return (annot != null);
    }

    private CustoAspect() {
    }

}
