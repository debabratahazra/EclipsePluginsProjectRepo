package com.odcgroup.translation.core.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.internal.events.TranslationChangeSupport;
import com.odcgroup.translation.core.richtext.RichTextUtils;

/**
 * This is the base class for all implementation of interface ITranslation
 * 
 * @author atr
 */
public abstract class BaseTranslation implements ITranslation {

	/** */
	private ITranslationProvider provider;

	/** */
	private IProject project;

	/** */
	private List<Locale> locales = new ArrayList<Locale>();
	
	/**
	 * All the supported translation kinds. 
	 */
	private ITranslationKind[] allKinds;

	/** */
	private TranslationChangeSupport queue = new TranslationChangeSupport(this);
	
	/**
	 * This method must be implemented by all concrete translation wrapper. 
	 * It must build the collection of all supported translation kind.
	 * 
	 * @return
	 */
	protected abstract ITranslationKind[] getTranslationKinds();

	/**
	 * @return the project
	 */
	protected final IProject getProject() {
		return project;
	}
	
	/**
	 * @return the translation provider
	 */
	protected final ITranslationProvider getTranslationProvider() {
		return this.provider;
	}

	/**
	 * Propagate a change event to the listeners
	 * 
	 * @param event
	 */
	protected void propagateTranslationChangeEvent(ITranslationChangeEvent event) {
		queue.propagateTranslationChangeEvent(event);
	}
	
	/**
	 * Notifies listener of a change in the translation
	 * 
	 * @param kind
	 */
	protected void fireChangeTranslation(ITranslationKind kind) {
		queue.fireTranslationChange(kind);
	}
	
	/**
	 * Notifies listener of a change in the translation
	 * 
	 * @param kind
	 * @param locale
	 * @param oldText
	 * @param newText
	 */
	protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
		queue.fireTranslationChange(kind, locale, oldText, newText);
	}

	@Override
	public void addTranslationChangeListener(ITranslationChangeListener listener) {
		queue.addTranslationChangeListener(listener);
	}

	@Override
	public void addTranslationChangeListener(ITranslationKind kind, ITranslationChangeListener listener) {
		queue.addTranslationChangeListener(kind, listener);
	}

	@Override
	public void removeTranslationChangeListener(ITranslationChangeListener listener) {
		queue.removeTranslationChangeListener(listener);
	}

	@Override
	public void removeTranslationChangeListener(ITranslationKind kind, ITranslationChangeListener listener) {
		queue.removeChangeListener(kind, listener);
	}
	
	@Override
	public final ITranslationKind[] getAllKinds() {
		if (allKinds == null) {
			allKinds = getTranslationKinds();
		}
		return allKinds;
	}
	
	@Override
	public boolean acceptRichText(ITranslationKind kind) {
		return false;
	}
	
	@Override
	public String getDisplayName(ITranslationKind kind) {
		if (null == kind) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		return provider.getDisplayName(kind);
	}

	@Override
	public boolean messagesFound(ITranslationKind kind) {
		for (Locale locale : locales) {
			try {
				String message = getText(kind, locale);
				if (!StringUtils.isEmpty(message)) {
					return true;
				}
			} catch (Exception e) {
			}
		} 
		return false;
	}
	
	@Override
	public boolean messagesFound(ITranslationKind kind, boolean richtext) {
		if (!richtext) return messagesFound(kind);
		for (Locale locale : locales) {
			try {
				String message = getText(kind, locale);
				if (!StringUtils.isEmpty(message)) {
					return RichTextUtils.isRichRext(message);
				}  
			} catch (Exception e) {
			}
		} 
		return false;
	}

	/**
	 * @param project
	 * @return
	 */
	private List<Locale> getLocales(IProject project) {
		List<Locale> locales = new ArrayList<Locale>();
		ITranslationPreferences preferences = TranslationCore.getTranslationManager(project).getPreferences();
		locales.add(preferences.getDefaultLocale());
		locales.addAll(preferences.getAdditionalLocales());
		return locales;
	}
	
	/**
	 * Constructor
	 * 
	 * @param provider
	 *            the provider of this translation
	 * @param project
	 *            the project
	 */
	protected BaseTranslation(ITranslationProvider provider, IProject project) {
		if (null == provider || null == project) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		this.provider = provider;
		this.project = project;
		this.locales.addAll(getLocales(project));
	}

}
