package com.odcgroup.pageflow.validation.internal;

import org.eclipse.emf.validation.IValidationContext;

import com.odcgroup.pageflow.model.Action;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public class PageflowActionConstraints extends AbstractPageflowConstraints {

	/**
	 * @see com.odcgroup.pageflow.validation.internal.AbstractPageflowConstraints#validate(org.eclipse.emf.validation.IValidationContext, com.odcgroup.pageflow.validation.internal.AbstractPageflowConstraints.StatusAggregator)
	 */
	protected void validate(IValidationContext ctx, PageflowValidationListener listener) {
		new PageflowActionValidator(listener).accept((Action) ctx.getTarget());
	}

}
