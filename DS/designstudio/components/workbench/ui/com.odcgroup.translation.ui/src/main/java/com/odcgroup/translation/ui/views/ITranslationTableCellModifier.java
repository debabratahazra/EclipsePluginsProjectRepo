package com.odcgroup.translation.ui.views;

import org.eclipse.jface.viewers.ICellModifier;

import com.odcgroup.translation.ui.command.ITranslationCommand;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public interface ITranslationTableCellModifier extends ICellModifier {

	/**
	 * Sets the command to change the translation
	 * 
	 * @param command
	 */
	void setCommand(ITranslationCommand command);

	/**
	 * Sets the translation model
	 * 
	 * @param model
	 */
	void setTranslationModel(ITranslationModel model);

}
