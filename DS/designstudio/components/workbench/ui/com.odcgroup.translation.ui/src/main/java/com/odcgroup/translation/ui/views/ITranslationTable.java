package com.odcgroup.translation.ui.views;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public interface ITranslationTable {

	/**
	 * show the text
	 */
	void showTable();
	
	/**
	 * Hide the text
	 */
	void hideTable();
	
	/**
	 * Hide all the buttons
	 */
	void hideButtons();

	/**
	 * Hide the translation's origin
	 */
	void hideOrigin();

	/**
	 * Show all the buttons.
	 */
	void showButtons();

	/**
	 * Show the translation's origin
	 */
	void showOrigin();
	
	/**
	 * Sets the new presentation model and internal controls are
	 * refreshed
	 * 
	 * @param model
	 *            the new presentation model
	 */
	void setTranslationModel(ITranslationModel model);

	/**
	 * dispose the table
	 */
	void dispose();

}
