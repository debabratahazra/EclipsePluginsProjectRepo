package com.odcgroup.mdf.validation.validator;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EObjectValidator;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.mdf.validation.MdfValidationCore;


@SuppressWarnings("unchecked")
public class MdfValidatorAdapter extends EObjectValidator {

    public MdfValidatorAdapter() {
        super();
    }

	public boolean validate(EObject eObject, DiagnosticChain diagnostics,
            Map context) {
        return validate(eObject.eClass(), eObject, diagnostics, context);
    }

    public boolean validate(EClass eClass, EObject eObject,
            final DiagnosticChain diagnostics, final Map context) {
        super.validate(eClass, eObject, diagnostics, context);

        final MultiStatus mergedStatus =
                new MultiStatus(MdfValidationCore.PLUGIN_ID, -1, "", null);
        if(!(eObject instanceof MdfModelElement)) {
        	return false;
        }
        MdfModelElement model = (MdfModelElement) eObject ;

        if (diagnostics != null ) {
            if (!hasProcessed(model, context)) {
                ModelVisitor validator =
                        ValidatorsFactory.newInstance(new ValidationListener() {

                            public boolean onValidation(MdfModelElement model,
                                    IStatus status) {
                                mergedStatus.merge(status);
                                processed(model, context, status);
                                appendDiagnostics(status, diagnostics, model);
                                return true;
                            }
                        });

                new ModelWalker(validator).visit(model);
            }
        }

        return mergedStatus.isOK();
    }

    private void processed(MdfModelElement model, Map context, IStatus status) {
        if (context != null) {
            context.put(model, status);
        }
    }

    private boolean hasProcessed(MdfModelElement model, Map context) {
        return context.containsKey(model);
    }

    private void appendDiagnostics(IStatus status, DiagnosticChain diagnostics,
            MdfModelElement model) {
        if (!status.isOK()) {
            if (status.isMultiStatus()) {
                IStatus[] children = status.getChildren();
    
                for (int i = 0; i < children.length; i++) {
                    appendDiagnostics(children[i], diagnostics, model);
                }
            } else {
                diagnostics.add(new BasicDiagnostic(status.getSeverity(), status
                        .getPlugin(), status.getCode(), status.getMessage(),
                        new Object[] { model }));
            }
        }
    }

}
