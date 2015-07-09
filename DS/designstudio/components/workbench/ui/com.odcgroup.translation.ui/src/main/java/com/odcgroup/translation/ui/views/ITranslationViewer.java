package com.odcgroup.translation.ui.views;

import org.eclipse.swt.widgets.Control;

import com.odcgroup.translation.core.ITranslationKind;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public interface ITranslationViewer {
	
	/**
	 * show the text
	 */
	void show();
	
	/**
	 * Hide the text
	 */
	void hide();
	
	/**
	 * show the text
	 */
	void showTable();
	
	/**
	 * Hide the text
	 */
	void hideTable();
	
	/**
	 * show the translation kinds selector
	 */
	void hideKindSelector();
	
	/**
	 * Hide the buttons
	 */
	void hideButtons();
	
	/**
	 * Hide the translation's origin
	 */
	void hideOrigin();

	/**
	 * show the translation kinds selector
	 */
	void showKindSelector();
	
	/**
	 * show the buttons
	 */
	void showButtons();

	/**
	 * Show the translation's origin
	 */
	void showOrigin();
	
	/**
	 * Sets the translation model, and automatically update the viewer
	 * @param model the new model
	 */
	void setTranslationModel(ITranslationModel model);
	
	/**
	 * @return the SWT Control
	 */
	Control getControl();
	
	/**
	 * dispose the viewer
	 */
	void dispose();
	
	/**
	 * hide the TranslationKind
	 */
	void hideTranslationKind(ITranslationKind kind);
}
