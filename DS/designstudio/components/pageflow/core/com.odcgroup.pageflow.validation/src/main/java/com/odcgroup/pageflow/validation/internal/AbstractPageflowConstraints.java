package com.odcgroup.pageflow.validation.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import com.odcgroup.workbench.tap.validation.AbstractDSModelConstraint;

/**
 * Abstract Validation Constraints Provider for Pageflow
 *
 * @author atr
 */
public abstract class AbstractPageflowConstraints extends AbstractDSModelConstraint {

    static private class StatusAggregator implements PageflowValidationListener {
    	
        private final List<IStatus> problems;
		private IValidationContext ctx;

        public StatusAggregator(IValidationContext ctx) {
        	this.ctx = ctx;
            problems = new ArrayList<IStatus>();
        }

        /**
         * @see com.odcgroup.PageflowValidationListener.validation.internal.constraint.PageValidationListener#onValidation(com.odcgroup.page.model.Widget, org.eclipse.core.runtime.IStatus)
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
         * Returns the aggregated list of validation problems
         * 
         * @return list of problems during validation
         */
        public List<IStatus> getProblems() {
            return problems;
        }


    }
    
    /**
     * @param ctx
     * @param listener
     */
    protected abstract void validate(IValidationContext ctx, PageflowValidationListener listener);

	protected IStatus doValidate(IValidationContext ctx) {
        StatusAggregator listener = new StatusAggregator(ctx);
        validate(ctx, listener);
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
	
	/**
	 * 
	 */
	public AbstractPageflowConstraints() {
	}
	
}
