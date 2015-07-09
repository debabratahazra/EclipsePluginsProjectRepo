package com.odcgroup.pageflow.validation.internal;

import org.eclipse.emf.validation.IValidationContext;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.ViewState;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public class PageflowStateConstraints extends AbstractPageflowConstraints {

	/**
	 * @see com.odcgroup.pageflow.validation.internal.AbstractPageflowConstraints#validate(org.eclipse.emf.validation.IValidationContext, com.odcgroup.pageflow.validation.internal.AbstractPageflowConstraints.StatusAggregator)
	 */
	protected void validate(IValidationContext ctx, PageflowValidationListener listener) {

		State state = (State) ctx.getTarget();
		new PageflowStateValidator(listener).accept(state);
		
		if (state instanceof InitState) {
			new PageflowInitStateValidator(listener).accept((InitState)state);
		
		} else if (state instanceof EndState) {
			new PageflowEndStateValidator(listener).accept((EndState)state);
		
		} else if (state instanceof ViewState) {
			new PageflowViewStateValidator(listener).accept((ViewState)state);

		} else if (state instanceof DecisionState) {
			new PageflowDecisionStateValidator(listener).accept((DecisionState)state);
		
		} else if (state instanceof SubPageflowState) {
			new PageflowSubPageflowStateValidator(listener).accept((SubPageflowState)state);
		}
	}

}
