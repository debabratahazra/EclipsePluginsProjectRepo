package com.odcgroup.translation.ui.internal.views.richtext;

import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Point;

class UnorderedListHandler extends StyledTextHandler {

	@Override
	public boolean isStyleSelected(boolean selected) {
		int offset = getText().getCaretOffset();
		if (offset < 0 || offset >= getText().getCharCount()) {
			return false; // empty content
		}
		int line = getText().getLineAtOffset(offset);
		Bullet bullet = getText().getLineBullet(line);
		return bullet != null && (bullet.type & ST.BULLET_DOT) != 0;
	}

	@Override
	public void updateStyle(boolean selected) {

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

		// prepare the bullet.
		Bullet bullet = null;
		if (selected) {
			StyleRange range = new StyleRange();
			int indent = 0; 
			Bullet oldBullet = text.getLineBullet(firstLine);
			if (oldBullet != null) {
				indent = oldBullet.style.metrics.width;
			} else {
				indent = text.getLineIndent(firstLine);
			}
			if (indent < 20) indent = 20;
			range.metrics = new GlyphMetrics(0, 0, indent);
			bullet = new Bullet(range);
			bullet.text = "";
		}
		
		// apply the bullet to all selected lines
		int nbLines = lastLine - firstLine + 1;
		if (bullet != null) {
			// Apply the bullet on all selected lines
			text.setLineBullet(firstLine, nbLines, null);
			text.setLineBullet(firstLine, nbLines, bullet);
			text.setLineIndent(firstLine, nbLines, 0);
			text.setLineWrapIndent(firstLine, nbLines, bullet.style.metrics.width);
		} else {
			// Remove the bullet on selected lines
			bullet = text.getLineBullet(firstLine);
			int indent = bullet.style.metrics.width;
			text.setLineBullet(firstLine, nbLines, null);
			text.setLineIndent(firstLine, nbLines, indent);
			text.setLineWrapIndent(firstLine, nbLines, indent);
		}
		
	}

	public UnorderedListHandler(StyledText text) {
		super(text);
	}

}
