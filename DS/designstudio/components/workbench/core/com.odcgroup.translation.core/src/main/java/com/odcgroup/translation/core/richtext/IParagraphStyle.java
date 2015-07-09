package com.odcgroup.translation.core.richtext;

/**
 * The styles of a paragraph
 * 
 * @author atr
 * @version 1.0
 */
public interface IParagraphStyle extends IStyle {

	/**
	 * Returns the indentation of the first line in pixels. The default value is
	 * equals to 0.
	 * 
	 * @return the indentation of the first line in pixels.
	 */
	int getFirstLineIndent();

	/**
	 * Returns the indentation of the wrapped lines. The default indentation is
	 * equals to 0.
	 * 
	 * @return the indentation of the wrapped lines
	 */
	int getWrappedLineIndent();

	/**
	 * Returns <code>"true"</code> if the paragraph is justified, otherwise it
	 * returns <code>"false"</code>. The default is set to <code>"false"</code>
	 * 
	 * @return <code>"true"</code> if the paragraph is justified.
	 */
	boolean isJustify();

	/**
	 * Returns <code>"true"</code> if the paragraph is centered, otherwise it
	 * returns <code>"false"</code>. The default is set to <code>"false"</code>
	 * 
	 * @return <code>"true"</code> if the paragraph is centered
	 */
	boolean isCenter();

	/**
	 * Returns <code>"true"</code> if the text of the paragraph is aligned to
	 * the left, otherwise it returns <code>"false"</code>.The default is set to
	 * <code>"false"</code>
	 * 
	 * @return <code>"true"</code> if the text of the paragraph is aligned to
	 *         the left
	 */
	boolean isLeft();

	/**
	 * Returns <code>"true"</code> if the text of the paragraph is aligned to
	 * the right, otherwise it returns <code>"false"</code>.The default is set
	 * to <code>"false"</code>
	 * 
	 * @return <code>"true"</code> if the text of the paragraph is aligned to
	 *         the right
	 */
	boolean isRight();

	/**
	 * Returns <code>"true"</code> if at least one decorator is defined, i.e.
	 * its value is different that the default value, otherwise it returns
	 * <code>"false"</code>.
	 * 
	 * @return <code>"true"</code> if at least one decorator is defined
	 */
	boolean isDecorated();

}
