package com.tel.autosysframework.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.actions.RetargetAction;

import com.tel.autosysframework.AutosysPlugin;

/**
 * @author hudsonr
 * @since 2.1
 */
public class IncrementRetargetAction extends RetargetAction {

/**
 * Constructor for IncrementRetargetAction.
 * @param actionID
 * @param label
 */
public IncrementRetargetAction() {
	super(IncrementDecrementAction.INCREMENT,
		"Increment");
	setToolTipText("Increment EditPart");
	setImageDescriptor(
	ImageDescriptor.createFromFile(AutosysPlugin.class,"icons/plus.gif")); //$NON-NLS-1$
}

}
