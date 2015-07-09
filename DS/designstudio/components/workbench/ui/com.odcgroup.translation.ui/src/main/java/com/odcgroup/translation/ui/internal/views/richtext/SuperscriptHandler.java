package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.FontData;

class SuperscriptHandler extends TextStyleHandler {

	@Override
	public final boolean doIsStyleSelected(StyleRange range) {
		return range != null && range.rise > 0;
	}

	@Override
	public final void doUpdateStyle(StyleRange range) {
		FontData data = getText().getFont().getFontData()[0];
		range.rise = data.getHeight() / 3;
	}

	public SuperscriptHandler(StyledText text) {
		super(text);
	}

}
