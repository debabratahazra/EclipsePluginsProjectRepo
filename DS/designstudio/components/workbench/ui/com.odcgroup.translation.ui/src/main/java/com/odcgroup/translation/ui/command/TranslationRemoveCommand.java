package com.odcgroup.translation.ui.command;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * @author atr
 */
public abstract class TranslationRemoveCommand extends TranslationCommand {

	
	@Override
	protected void updateCommandState(ITranslationModel model) {

		ITranslationKind kind = model.getSelectedKind();
		Locale locale = model.getSelectedLocale();
		
		ITranslation translation = model.getTranslation();
		boolean inheritable = translation.isInheritable();
		if (inheritable) {
			try {
				boolean inherited = translation.isInherited(kind, locale);
				if (inherited) {
					model.setInformation(getInheritedInformation());
					toggleRemoveButton();
					return;
				} else {
					String text = translation.getText(kind, locale);
					if (text != null) {
						setVisible(true);
						setEnabled(true);
						setName(getNotInheritedName());
						setToolTipText(getNotInheritedToolTip());
						model.setInformation(getNotInheritedInformation());
					} else {
						// the inherited object has no translation 
						model.setInformation(getInheritedInformation());
						toggleRemoveButton();
						return;
					}
				}
			} catch (TranslationException ex) {
				// the inherited text cannot be determined.
				try {
					String text = translation.getText(kind, locale);
					if (text != null) {
						setVisible(true);
						setEnabled(true);
						setName(getNotInheritedName());
						setToolTipText(getNotInheritedToolTip());
						model.setInformation(getNotInheritedInformation());
					} else {
						// the inherited object has no translation 
						model.setInformation(getInheritedInformation());
					}
				} catch (TranslationException e) {
					// the inherited object has no translation 
					model.setInformation(getInheritedInformation());
				}
			}
		} else {
			if (model.getText() != null) {
				setVisible(true);
				setEnabled(true);
				setName(getStandardName());
				setToolTipText(getStandardToolTip());
			} else {
				toggleRemoveButton();
				return;
			}
		}
		
		setEnabled(!model.isReadOnly());

	}

	/**
	 * 
	 */
	private void toggleRemoveButton() {
		setVisible(true);
		setEnabled(false);
		setName(getStandardName());
		setToolTipText(getStandardToolTip());
	}

	/**
	 * Constructs a new TranslationEditCommand given the model
	 * @param model
	 */
	public TranslationRemoveCommand(ITranslationModel model) {
		super(model);
	}

}
