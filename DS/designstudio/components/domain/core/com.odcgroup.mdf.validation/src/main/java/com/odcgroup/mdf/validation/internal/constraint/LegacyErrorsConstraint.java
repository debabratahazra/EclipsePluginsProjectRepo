package com.odcgroup.mdf.validation.internal.constraint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.validation.MdfValidationCore;
import com.odcgroup.mdf.validation.validator.ValidationListener;
import com.odcgroup.mdf.validation.validator.ValidatorsFactory;

@Deprecated
public class LegacyErrorsConstraint /*extends AbstractDSModelConstraint*/ {

	protected IStatus doValidate(IValidationContext ctx) {
        StatusAggregator listener = new StatusAggregator(ctx);
        ModelVisitor validator =
                ValidatorsFactory.newInstance(listener);
        if(ctx.getTarget() instanceof MdfModelElement) {
        	validator.accept((MdfModelElement) ctx.getTarget());
        }
        if(listener.getProblems().size()>1) {
        	// Avoid aggregation (as it is now well displayed in the problem view)
        	List<String> errorMessages = new LinkedList<String>();
        	for (IStatus status:listener.getProblems()) {
        		errorMessages.add(status.getMessage());
        	}
        	return MdfValidationCore.newStatus(
					StringUtils.join(errorMessages, " // "), 
					IStatus.ERROR);
        } else if(listener.getProblems().size()==1) {
        	return listener.getProblems().get(0);
        } else {
        	return ctx.createSuccessStatus();
        }
	}

    private static class StatusAggregator implements ValidationListener {
        private final List<IStatus> problems;
		private IValidationContext ctx;

        public StatusAggregator(IValidationContext ctx) {
        	this.ctx = ctx;
            problems = new ArrayList<IStatus>();
        }

        /**
         * @see com.odcgroup.mdf.validation.editor.ValidationListener#accept(com.odcgroup.mdf.metamodel.MdfModelElement,
         *      org.eclipse.core.runtime.IStatus)
         */
        public boolean onValidation(MdfModelElement model, IStatus status) {
            if (status.getSeverity()==IStatus.ERROR) {
            	problems.add(ConstraintStatus.createStatus(ctx, ctx.getResultLocus(), status.getMessage(), (Object[]) null));
            }
            return true;
        }

        /**
         * Returns the aggregated list of validation problems
         * 
         * @return list of problems during validation
         */
        public List<IStatus> getProblems() {
            return problems;
        }
    }

}
