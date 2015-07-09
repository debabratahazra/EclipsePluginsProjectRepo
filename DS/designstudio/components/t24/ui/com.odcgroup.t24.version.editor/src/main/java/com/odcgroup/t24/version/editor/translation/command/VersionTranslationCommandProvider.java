package com.odcgroup.t24.version.editor.translation.command;

import com.odcgroup.translation.ui.command.BaseTranslationCommandProvider;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * The Translation Commands Provider related to the Version Designer.
 * @author atr
 */
public class VersionTranslationCommandProvider extends BaseTranslationCommandProvider {

	@Override
	protected ITranslationCommand doGetEditCommand(ITranslationModel model) {
		return new VersionTranslationEditCommand(model);
	}

	@Override
	protected ITranslationCommand doGetRemoveCommand(ITranslationModel model) {
		return new VersionTranslationRemoveCommand(model);
	}

	/**
	 * Constructs a new TranslationCommandProvider
	 */
	public VersionTranslationCommandProvider() {
		super();
	}

}
