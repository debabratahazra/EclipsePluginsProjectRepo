package com.odcgroup.mdf.validation.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.MdfValidationCore;
import com.odcgroup.otf.utils.inet.MalformedUriException;
import com.odcgroup.otf.utils.inet.URI;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfAnnotationsValidator implements ModelValidator {
    private ValidationListener listener = null;

    /**
     * Constructor for MdfDocValidator
     */
    public MdfAnnotationsValidator() {
        super();
    }

    public void setValidationListener(ValidationListener listener) {
        this.listener = listener;
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelVisitor#accept(com.odcgroup.mdf.metamodel.MdfModelElement)
     */
    public boolean accept(MdfModelElement model) {
        return listener.onValidation(model, validate(model));
    }

    @SuppressWarnings("unchecked")
	public static IStatus validate(MdfModelElement model) {
        List statuses = new ArrayList();
        Iterator it = model.getAnnotations().iterator();
        while (it.hasNext()) {
            MdfAnnotation annot = (MdfAnnotation) it.next();
            statuses.add(validate(annot));

            Iterator it2 = annot.getProperties().iterator();
            while (it2.hasNext()) {
                MdfAnnotationProperty p = (MdfAnnotationProperty) it2.next();
                statuses.add(validate(p));
            }
        }

        return new MultiStatus(
                MdfValidationCore.PLUGIN_ID, -1, (IStatus[]) statuses
                        .toArray(new IStatus[statuses.size()]),
                "Annotations status for element " + model.toString(), null);
    }

    public static IStatus validate(MdfAnnotation annot) {
        String namespace = annot.getNamespace();
        if (MdfValidatorUtil.isNullOrEmpty(namespace)) {
            return MdfValidationCore.newStatus("Annotation namespace must be specified",
                    IStatus.ERROR);
        }

        try {
            URI.parse(namespace);
        } catch (MalformedUriException e) {
            return MdfValidationCore.newStatus("'" + namespace
                    + "' is not a valid namespace", IStatus.ERROR);
        }

        String name = annot.getName();
        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("Annotation name must be specified",
                    IStatus.ERROR);
        }

        return MdfValidationCore.STATUS_OK;
    }

    public static IStatus validate(MdfAnnotationProperty p) {
        String name = p.getName();
        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("Annotation name must be specified",
                    IStatus.ERROR);
        }

        return MdfValidationCore.STATUS_OK;
    }
    
  
}
