package com.odcgroup.translation.ui.command;

import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public interface ITranslationCommandProvider {
	
	/**
	 * @return
	 */
	String getId();

	/**
	 * @return
	 */
	String getName();

	/**
	 * @return
	 */
	String getNature();

	/**
	 * @return
	 */
	int getPriority();

	/**
	 * @return
	 */
	Class<?>[] getInputTypes();

	/**
	 * @param model
	 * @return
	 */
	ITranslationCommand getEditCommand(ITranslationModel model);

	/**
	 * @param model
	 * @return
	 */
	ITranslationCommand getRemoveCommand(ITranslationModel model);
}
