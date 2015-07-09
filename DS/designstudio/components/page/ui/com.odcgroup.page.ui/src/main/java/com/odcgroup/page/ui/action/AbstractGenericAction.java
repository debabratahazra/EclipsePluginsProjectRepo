package com.odcgroup.page.ui.action;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.Action;

import com.odcgroup.page.model.Widget;

/**
 * The <code>AbstractGenericAction</code> is the base class for all concrete
 * generic.<p>
 * 
 * Generic action are generally defined in the page designer metamodel.<p>
 * 
 * @author Alain Tripod
 * @author Gary Hayes
 */
public abstract class AbstractGenericAction extends Action implements UpdateAction {

	/** The parameters of this action */
	private ActionParameters parameters;

	/**
	 * Sets the set of parameters for the concrete action
	 * 
	 * @param parameters the set of parameters 
	 */
	protected final void setParameters(ActionParameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the set of parameters for the concrete action
	 */
	protected final ActionParameters getParameters() {
		return this.parameters;
	}

	/**
	 * @return the command stack
	 */
	protected final CommandStack getCommandStack() {
		return getParameters().getCommandStack();
	}

	/**
	 * @return The name of the widget template
	 */
	protected final String getWidgetTemplateName() {
		return getParameters().getWidgetTemplateName();
	}

	/**
	 * Executes a concrete command
	 * @param command
	 */
	protected void execute(Command command) {
		if (command != null) {
			getCommandStack().execute(command);
		}
	}

	/**
	 * 
	 * @return Widget The selected Widget
	 */
	protected Widget getSelectedWidget() {
		Widget widget = null;
		List<Widget> list = getParameters().getWidgetList();
		if (list.size() > 0) {
			widget = list.get(0);
		}
		return widget;
	}

	/**
	 * Abstract Constructor. 
	 *  
	 * @param parameters the set of parameters for the concrete action
	 */
	protected AbstractGenericAction(ActionParameters parameters) {

		// keep a reference to the set of parameters
		setParameters(parameters);

		// set basic action's properties
		setId(parameters.getId());
		setText(parameters.getDisplayName());
		setToolTipText(parameters.getTooltip());
		
		// parse the accelerator text
		String acceleratorText = parameters.getAccelerator();
		if (acceleratorText != null) {
			acceleratorText = acceleratorText.trim();
			if (acceleratorText.length() > 0) {
				int keyCode = convertAccelerator(acceleratorText);
				if (keyCode != 0) {
					setAccelerator(keyCode);
				}
			}
		}

	}
	
	/**
	 * @return <code>true</code> if the concrete action is allowed to run, otherwise it
	 *         returns <code>false</code>.
	 */
	protected boolean calculateEnabled() {
		return true;
	}

	/**
	 * Updates the Action.
	 */
	public void update() {
		setEnabled(calculateEnabled());
	}
}
