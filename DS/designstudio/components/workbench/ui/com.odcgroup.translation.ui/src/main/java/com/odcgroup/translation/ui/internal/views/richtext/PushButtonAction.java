package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;

public class PushButtonAction extends ButtonAction {

	public PushButtonAction(String imageName, String tooltip, ActionHandler handler) {
		super(SWT.PUSH, imageName, tooltip, false, handler);
	}

	public PushButtonAction(String imageName, String tooltip, boolean selection, ActionHandler handler) {
		super(SWT.PUSH, imageName, tooltip, selection, handler);
	}
}
