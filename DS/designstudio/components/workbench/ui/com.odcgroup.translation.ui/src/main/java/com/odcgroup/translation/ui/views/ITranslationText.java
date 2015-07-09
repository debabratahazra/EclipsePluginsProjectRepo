package com.odcgroup.translation.ui.views;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public interface ITranslationText extends ITranslationTable{

	/**
	 * Hide 
	 */
	void hide();

	/**
	 * Show
	 */
	void show();

	/**
	 * dispose the table
	 */
	void dispose();
	
	/**
	 * Sets the new presentation model and internal controls are
	 * refreshed
	 * 
	 * @param model
	 *            the new presentation model
	 */
	void setTranslationModel(ITranslationModel model);
}
