package com.zealcore.se.iw.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;

import com.swtdesigner.SWTResourceManager;
import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericTextImportData;
import com.zealcore.se.iw.types.internal.IgnoreType;
import com.zealcore.se.iw.wizard.internal.AbstractTextTreeVisitor;
import com.zealcore.se.iw.wizard.internal.Field;
import com.zealcore.se.iw.wizard.internal.Header;
import com.zealcore.se.iw.wizard.internal.Message;
import com.zealcore.se.iw.wizard.internal.MessageTree;
import com.zealcore.se.iw.wizard.internal.TextParser;

class DecoratingVisitor extends AbstractTextTreeVisitor {

	private final StyledText preview;

	private FieldDescriptor selected;

	private StyleRange messageRange;

	public DecoratingVisitor(final StyledText preview) {
		this.preview = preview;
	}

	public void decorate(final GenericTextImportData importData,
			final FieldDescriptor selected) {
		this.selected = selected;
		final StyleRange rangeOfDelimiter = new StyleRange();
		rangeOfDelimiter.start = 0;
		rangeOfDelimiter.length = this.preview.getText().length();
		this.preview.setStyleRange(rangeOfDelimiter);
		final GenericTextImportData data = importData;

		if (importData.getDescriptors().size() >= 0
				&& this.preview.getText().length() > 0) {
			final TextParser parser = new TextParser();
			MessageTree tree = parser.parse(this.preview.getText(), data);
			tree.accept(this);
		}
	}

	@Override
	public void visitHeader(final Header header) {
		final StyleRange range = new StyleRange(header.start(),
				header.length(), SWTResourceManager.getColor(210, 210, 210),
				null);
		range.strikeout = false;
		this.preview.setStyleRange(range);
	}

	@Override
	public void visitMessage(final Message message) {
		final Color firstMatchInARowBGColor = SWTResourceManager.getColor(190,
				190, 190);
		this.messageRange = new StyleRange(message.start(), 0, null,
				firstMatchInARowBGColor);
		message.accept(new AbstractTextTreeVisitor() {
			@Override
			public void visitField(final Field field) {
				if (field.isMatchedInFieldProposal()) {
					if (field.start() <= DecoratingVisitor.this.messageRange.start) {
						DecoratingVisitor.this.messageRange.start = field
								.start();
						DecoratingVisitor.this.messageRange.length = field
								.length();
					}
				}
			}
		});
		this.preview.setStyleRange(this.messageRange);
	}

	@Override
	public void visitField(final Field field) {

		StyleRange range = null;
		if (!field.isMatchedInFieldProposal()) {
			return;
		}
		if (field.getDescriptor().equals(this.selected)) {
			range = getActiveDecoration(field);
		} else if (field.getDescriptor().getType().equals(IgnoreType.INSTANCE)) {
			return;
		} else {
			final Color matchedTextBGColor = SWTResourceManager.getColor(190,
					190, 190);
			range = new StyleRange(field.start(), field.length(), null,
					matchedTextBGColor);
		}

		if (range.start != this.messageRange.start
				|| field.getDescriptor().equals(this.selected)) {
			this.preview.setStyleRange(range);
		}
	}

	private StyleRange getActiveDecoration(final Field field) {
		StyleRange range;
		final Color selectedTextFGColor = new Color(null, 83, 106, 177);
		final Color selectedTextBGColor = SWTResourceManager
				.getColor(SWT.COLOR_WHITE);
		range = new StyleRange(field.start(), field.length(),
				selectedTextBGColor, selectedTextFGColor);
		return range;
	}
}
