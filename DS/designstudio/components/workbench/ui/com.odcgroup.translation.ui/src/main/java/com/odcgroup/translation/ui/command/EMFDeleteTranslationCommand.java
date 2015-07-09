package com.odcgroup.translation.ui.command;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public class EMFDeleteTranslationCommand extends EMFTranslationCommand {

	private String oldText;

	@Override
	public void execute() {
		try {
			oldText = getTranslation().delete(getKind(), getLocale());
		} catch (TranslationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void undo() {
		try {
			if (oldText != null) {
				getTranslation().setText(getKind(), getLocale(), oldText);
			}
		} catch (TranslationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param translation
	 * @param kind
	 * @param locale
	 * @param text
	 */
	public EMFDeleteTranslationCommand(ITranslation translation, ITranslationKind kind, Locale locale) {
		super(translation, locale, kind);
	}

}
