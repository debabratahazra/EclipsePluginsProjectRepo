package com.odcgroup.translation.ui.internal.views.richtext;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

class DecreaseWrappedLinesIndent extends LineIndentHandler {

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
		
		// collect all shared bullets in the selection
		Map<Bullet, Integer> visitedBullets = new HashMap<Bullet, Integer>();
		for (int line = firstLine; line <= lastLine; line++) {
			Bullet bullet = getText().getLineBullet(line);
			if (bullet == null) {
				int indent = getText().getLineWrapIndent(line);
				if (indent >= getIncrement()) {
					indent -= getIncrement();
					getText().setLineWrapIndent(line, 1, indent);
				}
			}
		}
		
	}	

	public DecreaseWrappedLinesIndent(StyledText text) {
		super(text);
	}
}
