package com.odcgroup.translation.core.tests.provider;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.tests.model.SimpleObject;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public class SimpleTranslation extends BaseTranslation {

	private SimpleObject delegate;

	@Override
	public String delete(ITranslationKind kind, Locale locale) throws TranslationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final ITranslationKind[] getTranslationKinds() {
		return ITranslationKind.values();
	}

	@Override
	public Object getOwner() {
		return delegate;
	}
	
	@Override
	public String getText(ITranslationKind kind, Locale locale) throws TranslationException {
		String text = null;
		switch (kind) {
		case NAME:
			text = delegate.getLabel(locale);
			break;
		case TEXT:
			text = delegate.getToolTip(locale);
			break;
		default:
		}
		return text;
	}

	@Override
	public final boolean isReadOnly() throws TranslationException {
		return delegate.isEditable();
	}

	@Override
	public final boolean isProtected() throws TranslationException {
		return false;
	}

	@Override
	public final boolean isInheritable() {
		return false; 
	}	
	
	@Override
	public final boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException {
		return false;
	}
	
	@Override
	public final String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException {
		return null;
	}	
	
	@Override
	public String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException {
		String oldText = null;
		switch (kind) {
		case NAME:
			oldText = delegate.getLabel(locale);
			delegate.setLabel(locale, newText);
			break;
		case TEXT:
			oldText = delegate.getToolTip(locale);
			delegate.setToolTip(locale, newText);
			break;
		default:
		}
		fireChangeTranslation(kind, locale, oldText, newText);
		return oldText;
	}

	/**
	 * @param delegate
	 */
	public SimpleTranslation(ITranslationProvider provider, IProject project, SimpleObject delegate) {
		super(provider, project);
		this.delegate = delegate;
	}

}
