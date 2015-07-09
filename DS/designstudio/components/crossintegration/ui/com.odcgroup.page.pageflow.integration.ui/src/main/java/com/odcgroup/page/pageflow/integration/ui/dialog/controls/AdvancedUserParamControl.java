package com.odcgroup.page.pageflow.integration.ui.dialog.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.ui.dialog.UserParameterControl;

public class AdvancedUserParamControl extends UserParameterControl {
	/** */
	private boolean simplifiedEvent = true;
	
	/**
	 * @param parent
	 * @param style
	 * @param enableCellEdit
	 */
	public AdvancedUserParamControl(Composite parent, int style, boolean enableCellEdit) {
		super(parent, style, enableCellEdit);
	}

	/**
	 * @return
	 */
	public boolean isSimplifiedEvent() {
		return simplifiedEvent;
	}

	/**
	 * @param simplifiedEvent
	 */
	public void setSimplifiedEvent(boolean simplifiedEvent) {
		this.simplifiedEvent = simplifiedEvent;
	}

	/**
	 * @see com.odcgroup.page.ui.dialog.UserParameterControl#getTableInput(com.odcgroup.page.model.Event)
	 */
	@Override
	public List<Parameter> getTableInput(Event input) {
		simplifiedEvent = input.isSimplifiedEvent();
		List<Parameter> userParam = new ArrayList<Parameter>();
		if (input != null) {
			for (Parameter parameter : input.getParameters()) {
				if (parameter.isUserDefined()) {
					boolean isFlowActionParam = ParameterTypeConstants.FLOW_ACTION_PARAMETER.equals(parameter.getName());
					if (simplifiedEvent && !isFlowActionParam) {
						userParam.add(parameter);
					} else if (!simplifiedEvent) {
						if (!FunctionTypeConstants.SUBMIT_FUNCTION.equals(input.getFunctionName()) || !isFlowActionParam) {
							userParam.add(parameter);
						}
					}
				}
			}
		}
		return userParam;
	}
	

}
