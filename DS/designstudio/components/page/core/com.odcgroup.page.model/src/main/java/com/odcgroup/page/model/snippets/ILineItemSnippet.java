package com.odcgroup.page.model.snippets;

/**
 * @author pkk
 *
 */
public interface ILineItemSnippet extends ISnippetAdapter {
	
	/**
	 * @return
	 */
	String getAttributeName();
	
	/**
	 * @return
	 */
	boolean isTranslation();
	
	/**
	 * @return
	 */
	String getCSSClass();
	
	/**
	 * @param attribute
	 */
	void setAttributeName(String attribute);
	
	/**
	 * @param translation
	 */
	void setTranslation(boolean translation);
	
	/**
	 * @param css
	 */
	void setCSSClass(String css);
	
	/**
	 * @return
	 */
	ILineSnippet getParent();

}
