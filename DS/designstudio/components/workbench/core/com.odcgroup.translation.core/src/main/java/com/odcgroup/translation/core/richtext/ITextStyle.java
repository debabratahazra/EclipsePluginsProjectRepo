package com.odcgroup.translation.core.richtext;

public interface ITextStyle extends IStyle {
	
	IColor getBackgroundColor();
	
	IColor getForegroundColor();

	int getFontSize();

	boolean isDefaultFontSize();
	
	boolean isBold();
	
	boolean isItalic();
	
	boolean isUnderline();
	
	boolean isDecorated();

}
