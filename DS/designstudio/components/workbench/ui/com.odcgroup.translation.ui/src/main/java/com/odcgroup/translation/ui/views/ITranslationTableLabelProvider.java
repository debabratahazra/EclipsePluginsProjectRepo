package com.odcgroup.translation.ui.views;

import org.eclipse.jface.viewers.ITableLabelProvider;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public interface ITranslationTableLabelProvider extends ITableLabelProvider {

	/**
	 * @param model
	 */
	void setTranslationModel(ITranslationModel model);

}
