package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

abstract class TextAlignmentHandler extends StyledTextHandler {

	protected abstract boolean doIsStyleSelected(int line);

	protected abstract void doUpdateStyle(int line);

	@Override
	public final boolean isStyleSelected(boolean selected) {
		int offset = getText().getCaretOffset();
		if (offset < 0 || offset >= getText().getCharCount()) {
			return selected; // empty content
		}
		return doIsStyleSelected(getText().getLineAtOffset(offset));
	}

	@Override
	public final void updateStyle(boolean selected) {
		
		StyledText text = getText();
		
		// first line in the selection
		int firstLine = -1;
		
		Point sel = text.getSelectionRange();
		if (sel != null) {
			firstLine = text.getLineAtOffset(sel.x);
		} else {
			int offset = text.getCaretOffset();
			firstLine = text.getLineAtOffset(offset);
		}
		
		// count the number of line in the selection
		int lastLine = firstLine;
		for (int i = sel.x; i <= sel.x + sel.y - 1; i++) {
			int ln = text.getLineAtOffset(i);
			if (ln != lastLine) {
				lastLine = ln;
			}
		}
		
		for (int line = firstLine; line <= lastLine; line++) {
			doUpdateStyle(line);
		}

	}

	public TextAlignmentHandler(StyledText text) {
		super(text);
	}
}
