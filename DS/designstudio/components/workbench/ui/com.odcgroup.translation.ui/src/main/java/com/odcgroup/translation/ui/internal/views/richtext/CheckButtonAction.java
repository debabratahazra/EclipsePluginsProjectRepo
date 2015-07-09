package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;

public class CheckButtonAction extends ButtonAction {

	public CheckButtonAction(String imageName, String tooltip,
			ActionHandler handler) {
		super(SWT.CHECK, imageName, tooltip, false, handler);
	}

	public CheckButtonAction(String imageName, String tooltip,
			boolean selection, ActionHandler handler) {
		super(SWT.CHECK, imageName, tooltip, selection, handler);
	}

}
