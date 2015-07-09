package com.odcgroup.translation.core.richtext;

public interface IRichTextHandler {

	void start();

	void end();

	void startParagraph(IParagraphStyle style);

	void endParagraph();

	void startList(IListStyle style, String prevTextChunk);

	void endList(IListStyle style);

	void startListItem();

	void endListItem();

	void text(ITextStyle style, String text);

	void text(String text);
	
	void newLine();

}
