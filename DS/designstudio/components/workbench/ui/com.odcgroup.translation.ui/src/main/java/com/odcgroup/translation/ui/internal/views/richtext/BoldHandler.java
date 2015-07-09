package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

class BoldHandler extends TextStyleHandler {
	
	private final boolean isBold(StyleRange range) {
		return (range.fontStyle & SWT.BOLD) != 0;
	}
	
	@Override
	public final boolean doIsStyleSelected(StyleRange range) {
		return (range == null) ? isSelected() : isBold(range);
	}

	@Override
	public final void doUpdateStyle(StyleRange range) {
		if (isSelected()) {
			// set bold
			range.fontStyle |= SWT.BOLD;
		} else {
			// clear bold
			range.fontStyle &= ~SWT.BOLD;
		}
	}

	public BoldHandler(StyledText text) {
		super(text);
	}

}
