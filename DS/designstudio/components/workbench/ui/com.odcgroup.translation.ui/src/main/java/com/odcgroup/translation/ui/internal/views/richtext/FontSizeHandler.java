package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;

import com.odcgroup.translation.core.richtext.RichTextUtils;

abstract class FontSizeHandler extends FontHandler {
	
	private int size;
	
	public final int getSize() {
		return size;
	}
	
	@Override
	public String getSelection() {
		int height = RichTextUtils.DEFAULT_FONT_HEIGHT;
		StyleRange style = null;
		int offset = getText().getCaretOffset();
		if (offset < getText().getCharCount()) {
			style = getText().getStyleRangeAtOffset(offset);
		}
		if (style != null) {
			if (style.font != null) {
				height = style.font.getFontData()[0].getHeight();
			}
		}
		return height+"";
	}

	protected final void setFontSize(int fontSize) {
		
		size = fontSize;
		
		Point sel = getText().getSelectionRange();
		if ((sel == null) || (sel.y == 0)) {
			return;
		}
		Font font = getFont(fontSize);
		StyleRange style, range;
		for (int i = sel.x; i < sel.x + sel.y; i++) {
			range = getText().getStyleRangeAtOffset(i);
			if (range != null) {
				style = (StyleRange) range.clone();
				style.start = i;
				style.length = 1;
				style.font = font;
			} else {
				style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				style.font = font;
			}
			getText().setStyleRange(style);
		}
		getText().setSelectionRange(sel.x + sel.y, 0);
	}

	@Override
	public void updateStyle(String selection) {
		setFontSize(Integer.valueOf(selection));
	}
	
	public FontSizeHandler(StyledText text) {
		super(text);
	}

}
