package com.odcgroup.translation.ui.internal.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class TranslationLanguageLabelProvider implements ILabelProvider, PropertyChangeListener {
	
	private final String TRANSLATION_TEXT_PROPERTY = "com.odcgroup.translation.text"; 
	
	/** */
	private ITranslationModel model;
	
	/** listeners of this label providers */
	private List<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(model.getDefaultLocale());
		locales.addAll(model.getAdditionalLocales());
		LabelProviderChangedEvent labelEvent = new LabelProviderChangedEvent(this, locales.toArray());
		for (ILabelProviderListener listener : listeners) {
			listener.labelProviderChanged(labelEvent);
		}
	}
	
	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		int pos = listeners.indexOf(listener);
		if (pos == -1) {
			listeners.add(listener);
		}

	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		listeners.clear();
		if (model != null) {
			model.removePropertyChangeListener(this);
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * @param model
	 */
	public void setTranslationModel(ITranslationModel newModel) {
		if (newModel == null) {
			throw new IllegalArgumentException("model cannot be null");
		}
		if (model != null) {
			model.removePropertyChangeListener(TRANSLATION_TEXT_PROPERTY, this);
		}
		model = newModel;
		model.addPropertyChangeListener(TRANSLATION_TEXT_PROPERTY, this);
	}
	
	/**
	 * @param model
	 */
	public TranslationLanguageLabelProvider() {
	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		String text = "";
		Locale locale = (Locale)element;
		text = LanguageUtils.getShortDisplayString(locale, Locale.ENGLISH);
		return text;
	}

}
