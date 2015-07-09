package com.odcgroup.page.ui.command.translation;

import com.odcgroup.translation.ui.command.BaseTranslationCommandProvider;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * The Translation Commands Provider related to the page designer.
 * @author atr
 */
public class PageTranslationCommandProvider extends BaseTranslationCommandProvider {

	@Override
	protected ITranslationCommand doGetEditCommand(ITranslationModel model) {
		return new PageTranslationEditCommand(model);
	}

	@Override
	protected ITranslationCommand doGetRemoveCommand(ITranslationModel model) {
		return new PageTranslationRemoveCommand(model);
	}

	/**
	 * Constructs a new TranslationCommandProvider
	 */
	public PageTranslationCommandProvider() {
		super();
	}

}
