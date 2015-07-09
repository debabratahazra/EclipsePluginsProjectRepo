package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;

abstract class FontHandler extends StyledTextHandler {
	
	protected abstract Font getFont(int size);
	
	public abstract String getSelection(); 
	
	@Override
	public boolean isStyleSelected(boolean selected) {
		return false;
	}

	@Override
	public void updateStyle(boolean selected) {
		// do nothing
	}

	public FontHandler(StyledText text) {
		super(text);
	}

}
