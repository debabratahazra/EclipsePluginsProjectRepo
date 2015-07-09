package com.odcgroup.t24.enquiry.editor.translation.command;

import com.odcgroup.translation.ui.command.BaseTranslationCommandProvider;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryTranslationCommandProvider extends BaseTranslationCommandProvider {

	@Override
	protected ITranslationCommand doGetEditCommand(ITranslationModel model) {
		// TODO Auto-generated method stub
		return new EnquiryTranslationEditCommand(model);
	}

	@Override
	protected ITranslationCommand doGetRemoveCommand(ITranslationModel model) {
		// TODO Auto-generated method stub
		return new EnquiryTranslationRemoveCommand(model);
	}

	/**
	 * Constructs a new TranslationCommandProvider
	 */
	public EnquiryTranslationCommandProvider() {
		super();
	}

}
