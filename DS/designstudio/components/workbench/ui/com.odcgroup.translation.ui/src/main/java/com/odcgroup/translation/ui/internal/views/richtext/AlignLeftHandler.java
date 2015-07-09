package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;

class AlignLeftHandler extends TextAlignmentHandler {

	@Override
	protected final boolean doIsStyleSelected(int line) {
		return (getText().getLineAlignment(line) & SWT.LEFT) != 0;
	}

	@Override
	protected final void doUpdateStyle(int line) {
		if (getText().getLineJustify(line)) {
			getText().setLineJustify(line, 1, false);
			getText().setLineAlignment(line, 1, SWT.LEFT);
		} else {
			getText().setLineJustify(line, 1, true);
			getText().setLineAlignment(line, 1, SWT.NORMAL);
		}
	}

	public AlignLeftHandler(StyledText text) {
		super(text);
	}
}