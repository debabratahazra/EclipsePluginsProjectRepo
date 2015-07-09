package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

class ItalicHandler extends TextStyleHandler {
	
	private final boolean isItalic(StyleRange range) {
		return (range.fontStyle & SWT.ITALIC) != 0;
	}
	
	@Override
	public final boolean doIsStyleSelected(StyleRange range) {
		return (range == null) ? isSelected() : isItalic(range);
	}

	@Override
	public final void doUpdateStyle(StyleRange range) {
		if (isSelected()) {
			// set italic
			range.fontStyle |= SWT.ITALIC;
		} else {
			// clear italic
			range.fontStyle &= ~SWT.ITALIC;
		}
	}

	public ItalicHandler(StyledText text) {
		super(text);
	}

}
