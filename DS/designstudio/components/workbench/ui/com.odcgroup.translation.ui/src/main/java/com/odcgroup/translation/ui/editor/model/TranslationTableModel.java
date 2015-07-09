package com.odcgroup.translation.ui.editor.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.viewers.deferred.SetModel;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationPreferences;

/**
 *
 * @author pkk
 *
 */
public class TranslationTableModel extends SetModel implements ITranslationTableModel {
	
	private ITranslationPreferences preferences;
	
	
	/** all translations */
	private List<ITranslationTableItem> translationItems = new ArrayList<ITranslationTableItem>();
	private Set<ITranslation> transset = new HashSet<ITranslation>();
	
	/**
	 * @param preferences
	 * @param translations
	 */
	public TranslationTableModel(ITranslationPreferences preferences, List<ITranslation> translations) {
		this.preferences = preferences;
		this.transset.addAll(translations);
		processTranslations(translations);
		set(getTranslationItems());
	}
	
	/**
	 * @param translations
	 */
	private void processTranslations(List<ITranslation> translations) {
		for (ITranslation translation : translations) {
			translationItems.addAll(fetchTableItems(translation));
		}
	}
	
	/**
	 * @param translations
	 */
	public void addTranslations(List<ITranslation> translations) {
		List<ITranslationTableItem> items = new ArrayList<ITranslationTableItem>();
		for (ITranslation translation : translations) {
			if (!transset.contains(translation)) {
				items.addAll(fetchTableItems(translation));
			}
		}
		transset.addAll(translations);
		translationItems.addAll(items);
		addAll(items);
	}
	
	/**
	 * @param translation
	 * @return
	 */
	private List<ITranslationTableItem> fetchTableItems(ITranslation translation) {
		List<ITranslationTableItem> list = new ArrayList<ITranslationTableItem>();
		ITranslationKind[] kinds = translation.getAllKinds();
		ITranslationTableItem item = null;
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(getDefaultLocale());
		locales.addAll(getAdditionalLocales());
		for (ITranslationKind kind : kinds) {
			item = new TranslationTableItem(translation, kind, getDefaultLocale());
			item.setLocales(locales);
			list.add(item);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableModel#getAdditionalLocales()
	 */
	@Override
	public List<Locale> getAdditionalLocales() {
		return preferences.getAdditionalLocales();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableModel#getDefaultLocale()
	 */
	@Override
	public Locale getDefaultLocale() {
		return preferences.getDefaultLocale();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableModel#getTranslationItems()
	 */
	@Override
	public ITranslationTableItem[] getTranslationItems() {
		return translationItems.toArray(new ITranslationTableItem[0]);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableModel#addTranslationTableItems(java.util.List)
	 */
	@Override
	public void addTranslationTableItems(List<ITranslationTableItem> items) {
		translationItems.addAll(items);		
	}
	
}
