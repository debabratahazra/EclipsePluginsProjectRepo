package com.odcgroup.translation.core.richtext;

public interface IStyle {

	/**
	 * @return a unique identifier. It plays the same role as the HTML id.
	 */
	String getID();

	/**
	 * @return a string that contains zero or more CSS class name separated by
	 *         at least one space character.
	 */
	String getCSSClass();

}
