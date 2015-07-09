package com.odcgroup.translation.ui.editor.model;

import java.util.Locale;

/**
 *
 * @author pkk
 *
 */
public interface ITranslationTableColumn {
	
	/**
	 * @return
	 */
	String getText();
	
	/**
	 * @return
	 */
	int getWeight();
	
	/**
	 * @return
	 */
	int getStyle();
	
	/**
	 * @return
	 */
	boolean isResizeable();
	
	/**
	 * @return
	 */
	Locale getLocale();

}
