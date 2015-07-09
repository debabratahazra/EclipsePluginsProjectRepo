package com.odcgroup.translation.core.richtext;

public class RichTextFilterHandler implements IRichTextHandler {
	
	private StringBuilder buffer = new StringBuilder();

	@Override
	public void end() {
	}

	@Override
	public void endList(IListStyle style) {
	}

	@Override
	public void endListItem() {
	}

	@Override
	public void endParagraph() {
		buffer.append('\n');
	}

	public String getResult() {
		return buffer.toString();
	}

	@Override
	public void newLine() {
		buffer.append('\n');
	}

	@Override
	public void start() {
		buffer.delete(0, buffer.length());
	}

	@Override
	public void startList(IListStyle style, String prevTextChunk) {
	}

	@Override
	public void startListItem() {
	}

	@Override
	public void startParagraph(IParagraphStyle style) {
	}

	@Override
	public void text(ITextStyle style, String text) {
		buffer.append(text);
	}
	
	@Override
	public void text(String text) {
		buffer.append(text);
	}
	

}
