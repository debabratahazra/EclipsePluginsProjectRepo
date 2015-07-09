package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;

class AlignRightHandler extends TextAlignmentHandler {

	@Override
	protected final boolean doIsStyleSelected(int line) {
		return (getText().getLineAlignment(line) & SWT.RIGHT) != 0;
	}

	@Override
	protected final void doUpdateStyle(int line) {
		if ((getText().getLineAlignment(line) & SWT.RIGHT) != 0) {
			getText().setLineAlignment(line, 1, SWT.LEFT);
		} else {
			getText().setLineAlignment(line, 1, SWT.RIGHT);
		}
		getText().setLineJustify(line, 1, false);
	}

	public AlignRightHandler(StyledText text) {
		super(text);
	}
}
