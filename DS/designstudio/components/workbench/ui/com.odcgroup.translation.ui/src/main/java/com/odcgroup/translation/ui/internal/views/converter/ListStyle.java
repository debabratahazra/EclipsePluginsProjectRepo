package com.odcgroup.translation.ui.internal.views.converter;

import com.odcgroup.translation.core.richtext.IListStyle;

public class ListStyle implements IListStyle {
	
	private boolean ordered;
	private String startNumber;
	private int indent;
	private int wrapIndent;

	@Override
	public String getCSSClass() {
		return "";
	}

	@Override
	public String getID() {
		return "";
	}

	@Override
	public String getStartNumber() {
		return startNumber;
	}
	
	@Override
	public final boolean isOrderedList() {
		return ordered;
	}
	
	public final void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}

	@Override
	public final int getIndent() {
		return indent;
	}
	
	public final void setIndent(int indent) {
		this.indent = indent;
	}
	
	@Override
	public final int getWrapIndent() {
		return wrapIndent;
	}
	
	public final void setWrapIndent(int indent) {
		this.wrapIndent = indent;
	}

	public ListStyle(boolean ordered) {
		this.ordered = ordered;
	}

}
