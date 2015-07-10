package com.tel.autosysframework.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.actions.RetargetAction;

import com.tel.autosysframework.AutosysPlugin;

/**
 * @author hudsonr
 * @since 2.1
 */
public class DecrementRetargetAction extends RetargetAction {

/**
 * Constructor for IncrementRetargetAction.
 * @param actionID
 * @param label
 */
public DecrementRetargetAction() {
	super(IncrementDecrementAction.DECREMENT,
		"Decrement");
	setToolTipText("Decrement EditPart");
	setImageDescriptor(ImageDescriptor
		.createFromFile(AutosysPlugin.class,"icons/minus.gif")); //$NON-NLS-1$
}

}
