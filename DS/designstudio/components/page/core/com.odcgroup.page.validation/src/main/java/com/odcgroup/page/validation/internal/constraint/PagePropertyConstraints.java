package com.odcgroup.page.validation.internal.constraint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.workbench.tap.validation.AbstractDSModelConstraint;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public class PagePropertyConstraints extends AbstractDSModelConstraint {

    private static class StatusAggregator implements PageValidationListener {
    	
        private final List<IStatus> problems;
		private IValidationContext ctx;

        /**
         * @param ctx
         */
        public StatusAggregator(IValidationContext ctx) {
        	this.ctx = ctx;
            problems = new ArrayList<IStatus>();
        }

        /**
         * @see com.odcgroup.page.validation.internal.constraint.PageValidationListener#onValidation(com.odcgroup.page.model.Widget, org.eclipse.core.runtime.IStatus)
         */
        public void onValidation(IStatus status) {
            if (status.getSeverity()!= IStatus.OK) {
            	problems.add(
            			ConstraintStatus.createStatus(
            					ctx,
            					ctx.getResultLocus(), 
            					status.getSeverity(), 
            					0, 
            					status.getMessage(), 
            					(Object[]) null));
            }
        }

		/**
		 * @see com.odcgroup.page.validation.internal.constraint.PageValidationListener#onValidation(org.eclipse.core.runtime.IStatus, com.odcgroup.page.model.Property)
		 */
		@Override
		public void onValidation(IStatus status, Property property) {
			// nothing to do
		}

		/**
		 * @see com.odcgroup.page.validation.internal.constraint.PageValidationListener#onValidation(org.eclipse.core.runtime.IStatus, com.odcgroup.page.model.Event)
		 */
		@Override
		public void onValidation(IStatus status, Event event) {
			// nothing to do, property has no event
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

	protected IStatus doValidate(IValidationContext ctx) {
        StatusAggregator listener = new StatusAggregator(ctx);
        PagePropertyValidator validator = new PagePropertyValidator(listener);
        if(ctx.getTarget().eIsProxy()) return Status.OK_STATUS;
       	validator.accept((Property) ctx.getTarget());
        int nbProblems = listener.getProblems().size();
        if(nbProblems > 1) {
        	StringBuilder builder = new StringBuilder();
        	for (int px = 0; px < nbProblems; px++) {
        		if (px > 0) {
            		builder.append(" | ");
        		}
        		builder.append(listener.getProblems().get(px).getMessage());
        	}
        	return ConstraintStatus.createMultiStatus(ctx, builder.toString(), null, listener.getProblems());
        } else if(nbProblems == 1) {
        	return listener.getProblems().get(0);
        } else {
        	return ctx.createSuccessStatus();
        }        
	}
	
	public PagePropertyConstraints() {
	}
	
	
}
