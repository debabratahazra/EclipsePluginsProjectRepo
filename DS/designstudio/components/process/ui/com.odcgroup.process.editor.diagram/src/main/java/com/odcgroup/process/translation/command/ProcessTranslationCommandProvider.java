package com.odcgroup.process.translation.command;

import com.odcgroup.translation.ui.command.BaseTranslationCommandProvider;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * The Translation Commands Provider related to the Workflow Designer.
 * @author atr
 */
public class ProcessTranslationCommandProvider extends BaseTranslationCommandProvider {

	@Override
	protected ITranslationCommand doGetEditCommand(ITranslationModel model) {
		return new ProcessTranslationEditCommand(model);
	}

	@Override
	protected ITranslationCommand doGetRemoveCommand(ITranslationModel model) {
		return new ProcessTranslationRemoveCommand(model);
	}

	/**
	 * Constructs a new TranslationCommandProvider
	 */
	public ProcessTranslationCommandProvider() {
		super();
	}

}
