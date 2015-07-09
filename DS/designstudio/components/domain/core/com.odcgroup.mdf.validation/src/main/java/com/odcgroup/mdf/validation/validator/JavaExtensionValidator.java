package com.odcgroup.mdf.validation.validator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;

import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.MdfValidationCore;


/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class JavaExtensionValidator implements ModelValidator {
    private ValidationListener listener = null;

    /**
     * Constructor for MdfModelValidator
     */
    public JavaExtensionValidator() {
        super();
    }

    public void setValidationListener(ValidationListener listener) {
        this.listener = listener;
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelVisitor#accept(com.odcgroup.mdf.metamodel.MdfModelElement)
     */
    public boolean accept(MdfModelElement model) {
        if (model instanceof MdfDomain) {
            IStatus status =
            	MdfValidationCore.newStatus("A package must be provided", IStatus.ERROR);

            MdfAnnotation annot =
                model.getAnnotation(
                    JavaAspect.NAMESPACE_URI, JavaAspect.PACKAGE);

            if (annot != null) {
                MdfAnnotationProperty p = annot.getProperty("value");

                if (p != null) {
                    status = validate(p.getValue());
                }
            }

            listener.onValidation(model, status);
            return false;
        }

        return true;
    }

    @SuppressWarnings("deprecation")
	public static IStatus validate(String packageName) {
        return JavaConventions.validatePackageName(packageName);
    }
    
    /**
     * DS-370
     * @param model
     * @param newPack
     * @return
     */
    public static IStatus validatePackage(MdfModelElement model, String newPack) {
    	String uri = ((MdfModelElementImpl)model).eResource().getURI().toString();
    	String defPack = getDefaultPackage(uri);
    	if (!newPack.equals(defPack)) {
    		return MdfValidationCore.newStatus("The package name ["+newPack+"] declared in the mml"+
    				" does not match the package name ["+defPack+"] containing the mml file", 
    				IStatus.ERROR);
    	}
    	return MdfValidationCore.STATUS_OK;
    }
    
    /**
     * @param uri
     * @return
     */
    private static String getDefaultPackage(String uri) {
    	String domainFolder  = "resource:/";
    	int ii = uri.indexOf(domainFolder);
    	return uri.substring(ii+domainFolder.length(), uri.lastIndexOf("/")).replaceAll("/", ".");
    }    
    
}
