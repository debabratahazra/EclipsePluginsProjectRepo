package com.odcgroup.translation.ui.command;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public abstract class TranslationEditCommand extends TranslationCommand {

	@Override
	protected void updateCommandState(ITranslationModel model) {
		setVisible(true);
		setEnabled(!model.isReadOnly());
		ITranslation translation = model.getTranslation();
		if (translation.isInheritable()) {
			try {
				boolean inherited = translation.isInherited(model.getSelectedKind(), model.getSelectedLocale());
				if (inherited) {
					setName(getInheritedName());
					setToolTipText(getInheritedToolTip());
				} else {
					String text = model.getText();
					if (text != null) {
						setName(getNotInheritedName());
						setToolTipText(getNotInheritedToolTip());
					} else {
						setName(getInheritedName());
						setToolTipText(getInheritedToolTip());
					}
				}
			} catch (TranslationException ex) {
				setName(getNotInheritedName());
				setToolTipText(getNotInheritedToolTip());
			}
		} else {
			setName(getStandardName());
			setToolTipText(getStandardToolTip());
		}
	}

	/**
	 * Constructs a new TranslationEditCommand given the model
	 * 
	 * @param model
	 */
	public TranslationEditCommand(ITranslationModel model) {
		super(model);
	}

}
