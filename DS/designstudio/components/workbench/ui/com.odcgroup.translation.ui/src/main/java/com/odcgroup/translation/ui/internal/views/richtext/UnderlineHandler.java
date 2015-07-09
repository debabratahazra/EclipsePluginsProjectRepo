package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

class UnderlineHandler extends TextStyleHandler {
	
	@Override
	public final boolean doIsStyleSelected(StyleRange range) {
		return (range == null) ? isSelected() : range.underline;
	}

	@Override
	public final void doUpdateStyle(StyleRange range) {
		range.underline = isSelected();
	}

	public UnderlineHandler(StyledText text) {
		super(text);
	}
}
