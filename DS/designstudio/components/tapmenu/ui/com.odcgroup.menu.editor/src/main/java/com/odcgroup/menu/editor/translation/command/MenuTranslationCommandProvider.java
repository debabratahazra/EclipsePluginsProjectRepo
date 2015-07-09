package com.odcgroup.menu.editor.translation.command;

import com.odcgroup.translation.ui.command.BaseTranslationCommandProvider;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

public class MenuTranslationCommandProvider extends BaseTranslationCommandProvider {

	public MenuTranslationCommandProvider() {
		super();
	}

	@Override
	protected ITranslationCommand doGetEditCommand(ITranslationModel model) {
		return new MenuTranslationEditCommand(model);
	}

	@Override
	protected ITranslationCommand doGetRemoveCommand(ITranslationModel model) {
		return new MenuTranslationRemoveCommand(model);
	}

}
