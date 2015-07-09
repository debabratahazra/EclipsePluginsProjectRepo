package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.StyledText;

abstract class LineIndentHandler extends StyledTextHandler {

	private final static int DEFAULT_INDENT_INCREMENT = 20;

	protected final int getIncrement() {
		return LineIndentHandler.DEFAULT_INDENT_INCREMENT; 
	}

	@Override
	public final boolean isStyleSelected(boolean selected) {
		return false;
	}

	public LineIndentHandler(StyledText text) {
		super(text);
	}
}
