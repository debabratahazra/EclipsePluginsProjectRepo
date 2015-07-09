package com.odcgroup.translation.ui.command;

import java.util.Locale;

import org.eclipse.emf.common.command.AbstractCommand;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public abstract class EMFTranslationCommand extends AbstractCommand {

	private ITranslation translation;
	private ITranslationKind kind;
	private Locale locale;

	protected final ITranslation getTranslation() {
		return translation;
	}

	protected final ITranslationKind getKind() {
		return kind;
	}

	protected final Locale getLocale() {
		return locale;
	}

	@Override
	protected boolean prepare() {
		return true;
	}

	/**
	 * @param translation
	 */
	protected EMFTranslationCommand(ITranslation translation, Locale locale, ITranslationKind kind) {
		this.translation = translation;
		this.locale = locale;
		this.kind = kind;
	}

	@Override
	public void redo() {
		execute();
	}

}
