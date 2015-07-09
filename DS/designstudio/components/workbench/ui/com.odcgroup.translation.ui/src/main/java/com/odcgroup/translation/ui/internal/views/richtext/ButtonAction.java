package com.odcgroup.translation.ui.internal.views.richtext;

class ButtonAction extends RichTextAction {

	private ActionHandler handler;
	private String imageName;
	private boolean selection;
	private String tooltip;

	public final ActionHandler getHandler() {
		return this.handler;
	}

	public final String getImageName() {
		return this.imageName;
	}

	public final boolean getSelection() {
		return selection;
	}

	public final String getTooltip() {
		return this.tooltip;
	}

	public ButtonAction(int style, String imageName, String tooltip, boolean selection, ActionHandler handler) {
		super(style);
		this.imageName = imageName;
		this.tooltip = tooltip;
		this.handler = handler;
		this.selection = selection;
	}

}
