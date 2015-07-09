package com.odcgroup.translation.core.richtext;

public interface IListStyle extends IStyle {
	
	boolean isOrderedList();
	
	String getStartNumber();
	
	int getIndent();
	
	int getWrapIndent();

}
