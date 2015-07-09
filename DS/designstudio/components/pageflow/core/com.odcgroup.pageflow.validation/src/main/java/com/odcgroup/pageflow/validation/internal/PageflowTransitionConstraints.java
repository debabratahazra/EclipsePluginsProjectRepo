package com.odcgroup.pageflow.validation.internal;

import org.eclipse.emf.validation.IValidationContext;

import com.odcgroup.pageflow.model.Transition;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public class PageflowTransitionConstraints extends AbstractPageflowConstraints {

	/**
	 * @see com.odcgroup.pageflow.validation.internal.AbstractPageflowConstraints#validate(org.eclipse.emf.validation.IValidationContext, com.odcgroup.pageflow.validation.internal.AbstractPageflowConstraints.StatusAggregator)
	 */
	protected void validate(IValidationContext ctx, PageflowValidationListener listener) {
		new PageflowTransitionValidator(listener).accept((Transition) ctx.getTarget());
	}

}
