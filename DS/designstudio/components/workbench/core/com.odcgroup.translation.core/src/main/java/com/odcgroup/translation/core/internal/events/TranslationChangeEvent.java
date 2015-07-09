package com.odcgroup.translation.core.internal.events;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class TranslationChangeEvent implements ITranslationChangeEvent {

	private ITranslation source;
	private ITranslationKind kind;
	private Locale locale;
	private String newText;
	private String oldText;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.translation.core.ITranslationChangeEvent#getSource()
	 */
	@Override
	public final ITranslation getSource() {
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.translation.core.ITranslationChangeEvent#getKind()
	 */
	@Override
	public final ITranslationKind getKind() {
		return kind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.translation.core.ITranslationChangeEvent#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.translation.core.ITranslationChangeEvent#getNewText()
	 */
	@Override
	public final String getNewText() {
		return newText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.translation.core.ITranslationChangeEvent#getOldText()
	 */
	@Override
	public String getOldText() {
		return oldText;
	}

	/**
	 * @param source
	 * @param kind
	 * @param newText
	 * @param oldText
	 */
	public TranslationChangeEvent(ITranslation source, ITranslationKind kind, Locale locale, String oldText,
			String newText) {
		this.source = source;
		this.kind = kind;
		this.locale = locale;
		this.oldText = oldText;
		this.newText = newText;
	}

}
