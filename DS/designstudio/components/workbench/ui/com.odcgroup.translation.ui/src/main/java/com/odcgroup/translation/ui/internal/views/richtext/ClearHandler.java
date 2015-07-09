package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

class ClearHandler extends StyledTextHandler {

	public ClearHandler(StyledText text) {
		super(text);
	}

	@Override
	public boolean isStyleSelected(boolean selected) {
		return false;
	}

	@Override
	public void updateStyle(boolean selected) {
		Point sel = getText().getSelectionRange();
		if ((sel != null) && (sel.y != 0)) {
			StyleRange style;
			style = new StyleRange(sel.x, sel.y, null, null, SWT.NORMAL);
			getText().setStyleRange(style);
		}
		getText().setSelectionRange(sel.x + sel.y, 0);
	}

}
