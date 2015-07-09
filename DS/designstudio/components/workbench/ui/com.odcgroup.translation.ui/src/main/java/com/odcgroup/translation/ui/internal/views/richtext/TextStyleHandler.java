package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

abstract class TextStyleHandler extends StyledTextHandler {
	
	private boolean selected = false;

	public final void setSelected(boolean selected) {
		this.selected = selected; 
	}

	public final boolean isSelected() {
		return selected;
	}
	
	protected abstract boolean doIsStyleSelected(StyleRange range);

	protected abstract void doUpdateStyle(StyleRange range);
	
	@Override
	public final boolean isStyleSelected(boolean selected) {
		int offset = getText().getCaretOffset();
		StyleRange range = null;
		if (offset < getText().getCharCount()) {
			range = getText().getStyleRangeAtOffset(offset);
		}
		return doIsStyleSelected(range);
	}

	private boolean isSpaceChar(char ch) {
		return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
	}
	
	@Override
	public void updateStyle(boolean selected) {
		
		setSelected(selected);
		
		StyledText text = getText();
		Point sel = getText().getSelectionRange();
		if ((sel == null) || (sel.y == 0)) {
			
			// no text is selected. if the caret is inside a word 
			// then change the style of the whole word.

			// current caret position
			int offset = text.getCaretOffset();
			if (offset == 0 || offset == text.getCharCount()) {
				// the carret is either at the beginning of the buffer or at the end.
				// nothing to do.
				return;
			}
			
			// find the beginning of the word
			String str = text.getText();
			int start = offset;
			while (start > 0 && !isSpaceChar(str.charAt(start))) {
				start--;
			}
			if (start > 0) start++;
			if (start == offset) {
				// The caret is at the beginning of the word
				// nothing to change.
				return;
			}
			
			// find the end of the word
			int end = offset;
			int len = text.getCharCount();
			while (end < len && !isSpaceChar(str.charAt(end))) {
				end++;
			}
			
			if (start >= end) {
				// the caret is between words, nothing to do
				return;
			}
			
			str = text.getText(start, end-1);
			//System.out.println("start:"+start+" end:"+end+"text:["+str+"]");

			StyleRange style = null;
			for (int i = start; i < end; i++) {
				StyleRange range = getText().getStyleRangeAtOffset(i);
				if (range != null) {
					style = (StyleRange) range.clone();
					style.start = i;
					style.length = 1;
				} else {
					style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				doUpdateStyle(style);
				getText().setStyleRange(style);
			}
			return;
			
		}
		StyleRange style;
		for (int i = sel.x; i < sel.x + sel.y; i++) {
			StyleRange range = getText().getStyleRangeAtOffset(i);
			if (range != null) {
				style = (StyleRange) range.clone();
				style.start = i;
				style.length = 1;
			} else {
				style = new StyleRange(i, 1, null, null, SWT.NORMAL);
			}
			doUpdateStyle(style);
			getText().setStyleRange(style);
		}
		//getText().setSelectionRange(sel.x + sel.y, 0);
	}

	public TextStyleHandler(StyledText text) {
		super(text);
	}
}