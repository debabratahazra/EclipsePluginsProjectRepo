package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;

class JustifyHandler extends TextAlignmentHandler {

	@Override
	protected final boolean doIsStyleSelected(int line) {
		return getText().getLineJustify(line);
	}

	@Override
	protected final void doUpdateStyle(int line) {
		int alignment = getText().getLineAlignment(line);
		if ((alignment & SWT.LEFT) != 0) {
			getText().setLineJustify(line, 1, true);
			getText().setLineAlignment(line, 1, SWT.NORMAL);
		} else {
			getText().setLineAlignment(line, 1, SWT.LEFT);
			getText().setLineJustify(line, 1, false);
		}
	}

	public JustifyHandler(StyledText text) {
		super(text);
	}

}
