package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;

public class DropDownAction extends RichTextAction {

	private String[] values;
	private String selection;
	private String toolTip;
	private ActionHandler handler;

	public String[] getValues() {
		return values;
	}

	public String getSelection() {
		return selection;
	}

	public final String getToolTip() {
		return toolTip;
	}

	public final ActionHandler getHandler() {
		return handler;
	}

	public DropDownAction(String toolTip, String[] values, String selection, ActionHandler handler) {
		super(SWT.DROP_DOWN);
		this.values = values;
		this.selection = selection;
		this.handler = handler;
		this.toolTip = toolTip;
	}

}
