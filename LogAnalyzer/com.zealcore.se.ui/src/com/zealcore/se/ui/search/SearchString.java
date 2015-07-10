package com.zealcore.se.ui.search;

/**
 * Get the values of each attribute from Assertion Filter.
 * 
 * @author sagu
 */
public class SearchString {

	private String text1;

	private String text2;

	private boolean checked;

	public SearchString() {
		text1 = new String();
		text2 = new String();
		checked = false;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean isChecked) {
		this.checked = isChecked;
	}
}
