package com.odcgroup.visualrules.integration.ui.translation.command;

import com.odcgroup.translation.ui.command.BaseTranslationCommandProvider;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * The Translation Commands Provider related to visual rule.
 * @author atr
 */
public class RuleTranslationCommandProvider extends BaseTranslationCommandProvider {

	@Override
	protected ITranslationCommand doGetEditCommand(ITranslationModel model) {
		return new RuleTranslationEditCommand(model);
	}

	@Override
	protected ITranslationCommand doGetRemoveCommand(ITranslationModel model) {
		return new RuleTranslationRemoveCommand(model);
	}

	/**
	 * Constructs a new TranslationCommandProvider
	 */
	public RuleTranslationCommandProvider() {
		super();
	}

}
