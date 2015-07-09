package com.odcgroup.translation.ui.views;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public interface ITranslationKindSelector {
	
	/**
	 * Sets the new presentation model and automatically update the selector
	 * @param newModel the new presentation model
	 */
	void setTranslationModel(ITranslationModel newModel);

	/**
	 * Dispose the selector
	 */
	public void dispose();

}
