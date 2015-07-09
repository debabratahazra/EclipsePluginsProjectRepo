package com.odcgroup.menu.model.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.menu.model.Translation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.translation.BaseTranslation;

public class MenuItemTranslation extends BaseTranslation {

	/** The MenuItem */
	private MenuItem menuItem;

	public MenuItemTranslation(
			MenuItemTranslationProvider menuItemTranslationProvider,
			IProject project, MenuItem menuItem) {
		super(menuItemTranslationProvider, project);
		if (menuItem == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.menuItem = menuItem;
	}

	@Override
	public ITranslationKind[] getTranslationKinds() {
		ITranslationKind[] kinds = { ITranslationKind.NAME };
		return kinds;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#isReadOnly()
	 */
	public boolean isReadOnly() throws TranslationException {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#isProtected()
	 */
	public boolean isProtected() throws TranslationException {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#isInheritable()
	 */
	public boolean isInheritable() {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#isInherited(com.odcgroup.translation.core.ITranslationKind, java.util.Locale)
	 */
	public boolean isInherited(ITranslationKind kind, Locale locale)
			throws TranslationException {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#getInheritedText(com.odcgroup.translation.core.ITranslationKind, java.util.Locale)
	 */
	public String getInheritedText(ITranslationKind kind, Locale locale)
			throws TranslationException {
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#getText(com.odcgroup.translation.core.ITranslationKind, java.util.Locale)
	 */
	public String getText(ITranslationKind kind, Locale locale)
			throws TranslationException {
		String text = null;

		Translation translation = findLabel(menuItem, locale);
		if (translation != null) {
			text = translation.getMessage();
		}
		return text;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#setText(com.odcgroup.translation.core.ITranslationKind, java.util.Locale, java.lang.String)
	 */
	public String setText(ITranslationKind kind, Locale locale, String newText)
			throws TranslationException {
		if (null == newText || null == kind || null == locale) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		String oldText = null;
		switch (kind) {
		case NAME: {
			oldText = setLabel(locale, newText);
			fireChangeTranslation(kind, locale, oldText, newText);
			break;
		}
		default:
			break;

		}
		return oldText;
	}

	/**
	 * @param locale
	 * @param newValue
	 * @return the old value of the label
	 */
	private final String setLabel(Locale locale, String newValue) {
		String oldValue = null;
		Translation label = findLabel(menuItem, locale);
		if (label != null) {
			oldValue = label.getMessage();
			label.setMessage(newValue);
		} else {
			String language = locale.toString();
			label = MenuPackage.eINSTANCE.getMenuFactory().createTranslation();
			label.setLanguage(language);
			label.setMessage(newValue);
			menuItem.getLabels().add(label);
		}
		return oldValue;
	}

	/**
	 * @param widget
	 * @param locale
	 * @return Translation
	 */
	private final Translation findLabel(MenuItem item, Locale locale) {
		Translation result = null;
		for (Translation label : item.getLabels()) {
			if (locale.toString().equals(label.getLanguage())) {
				result = label;
				break;
			}
		}
		return result;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#getOwner()
	 */
	public Object getOwner() {
		return this.menuItem;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.core.ITranslation#delete(com.odcgroup.translation.core.ITranslationKind, java.util.Locale)
	 */
	public String delete(ITranslationKind kind, Locale locale)
			throws TranslationException {
		String oldText = null;
		Translation translation = findLabel(menuItem, locale);
		if (translation != null) {
			oldText = translation.getMessage();
			menuItem.getLabels().remove(translation);
			fireChangeTranslation(kind, locale, oldText, null);
		}
		return oldText;
	}

}
