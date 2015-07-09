package com.odcgroup.translation.ui.command;

import java.util.Locale;

import org.eclipse.gef.commands.Command;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public abstract class GEFTranslationCommand extends Command {

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

	/**
	 * @param translation
	 */
	protected GEFTranslationCommand(ITranslation translation, ITranslationKind kind, Locale locale) {
		if (null == translation || null == kind || locale == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		this.translation = translation;
		this.kind = kind;
		this.locale = locale;
	}

	@Override
	public void redo() {
		execute();
	}

}
