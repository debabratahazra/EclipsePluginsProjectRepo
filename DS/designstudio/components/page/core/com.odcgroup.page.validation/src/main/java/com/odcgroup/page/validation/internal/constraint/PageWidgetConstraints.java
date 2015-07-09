package com.odcgroup.page.validation.internal.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.tap.validation.AbstractDSModelConstraint;

/**
 * A Constraints Provider for all Widgets of the Page Designer.
 *
 * TODO : replace all this ugly hard coded rules, by scripts, and put them
 *        in the knowledge level (metamodel)
 * 
 * @author atr
 */
public class PageWidgetConstraints extends AbstractDSModelConstraint {

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
		@Override
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
            if (status.getSeverity()!= IStatus.OK) {
            	problems.add(
            			ConstraintStatus.createStatus(
            					ctx,
            					Collections.singleton(property), 
            					status.getSeverity(), 
            					0, 
            					status.getMessage(), 
            					(Object[]) null));
            }
            
		}

		/**
		 * @see com.odcgroup.page.validation.internal.constraint.PageValidationListener#onValidation(org.eclipse.core.runtime.IStatus, com.odcgroup.page.model.Event)
		 */
		@Override
		public void onValidation(IStatus status, Event event) {
            if (status.getSeverity()!= IStatus.OK) {
            	problems.add(
            			ConstraintStatus.createStatus(
            					ctx,
            					Collections.singleton(event), 
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

	protected IStatus doValidate(IValidationContext ctx) {
        StatusAggregator listener = new StatusAggregator(ctx);
        PageWidgetValidator validator = new PageWidgetValidator(listener);
       	validator.accept((Widget) ctx.getTarget());
        int nbProblems = listener.getProblems().size();
        if(nbProblems > 1) {
        	StringBuilder builder = new StringBuilder();
        	IStatus status = null;
        	int i = 0;
        	for (int px = 0; px < nbProblems; px++) {
        		status = listener.getProblems().get(px);
        		if (status.getSeverity() == IStatus.ERROR) {
	        		if (i > 0) {
	            		builder.append(" | ");
	        		}
	        		builder.append(status.getMessage());
	        		i++;
        		}
        	}
        	return ConstraintStatus.createMultiStatus(ctx, builder.toString(), null, listener.getProblems());
        } else if(nbProblems == 1) {
        	return listener.getProblems().get(0);
        } else {
        	return ctx.createSuccessStatus();
        }        
	}
	
	/**
	 * Constructor
	 */
	public PageWidgetConstraints() {
	}
	
	
}
