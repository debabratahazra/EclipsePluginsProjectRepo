package com.odcgroup.translation.ui.editor.model;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 *
 * @author pkk
 *
 */
public class TranslationTableItem implements ITranslationTableItem {
	
	private ITranslation translation;
	private Locale defaultLocale;
	private ITranslationKind translationKind;
	private String resourceUri;
	private String fragment = null;
	private String project = null;
	private List<Locale> locales;
	
	private Map<Locale, String> messagemap = new WeakHashMap<Locale, String>();

	/**
	 * @param translation
	 * @param translationKind
	 * @param defaultLocale
	 */
	public TranslationTableItem(ITranslation translation, ITranslationKind translationKind, Locale defaultLocale) {
		this.translation = translation;
		this.translationKind = translationKind;
		this.defaultLocale = defaultLocale;
		setResourceUri(translation);
	}
	
	/**
	 * @param translation
	 */
	private void setResourceUri(ITranslation translation) {
		Object obj = translation.getOwner();
		if (obj instanceof EObject) {
			Resource resource = ((EObject) obj).eResource();
			if(resource == null) {
				return;
			}
			if (resource instanceof XMLResource) {
				XMLResource xRes = (XMLResource) resource ;
				this.fragment = xRes.getID((EObject) obj);
			}
			this.resourceUri = resource.getURI().toString();
			this.project = OfsResourceHelper.getOfsProject(resource).getName();
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getText()
	 */
	@Override
	public String getText() {
		return getText(defaultLocale);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getText(java.util.Locale)
	 */
	@Override
	public String getText(Locale locale) {
		if (messagemap.containsKey(locale)) {
			return messagemap.get(locale);
		} else {
			String msg = null;
			try {
				msg = translation.getText(getTranslationKind(), locale);
			} catch (TranslationException e) {
			}
			msg = (msg != null) ? msg : "";
			messagemap.put(locale, msg);
			return msg;
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getTranslation()
	 */
	@Override
	public ITranslation getTranslation() {
		return translation;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getTranslationKind()
	 */
	@Override
	public ITranslationKind getTranslationKind() {
		return translationKind;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		boolean readOnly = true;
		try {
			readOnly = translation.isReadOnly();
		} catch (TranslationException e) {
			// ignore
		}
		return readOnly;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#setTranslation(com.odcgroup.translation.core.ITranslation)
	 */
	@Override
	public void setTranslation(ITranslation translation) {
		this.translation = translation;		
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getResourceURI()
	 */
	@Override
	public String getResourceURI() {
		return resourceUri;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getResourceFragment()
	 */
	@Override
	public String getResourceFragment() {
		return fragment;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.ITranslationTableItem#getProject()
	 */
	@Override
	public String getProject() {
		return project;
	}
	
	/**
	 * @return the locales
	 */
	public List<Locale> getLocales() {
		return locales;
	}

	/**
	 * @param locales the locales to set
	 */
	public void setLocales(List<Locale> locales) {
		this.locales = locales;
		for (Locale locale : locales) {
			String msg = null;
			try {
				msg = translation.getText(getTranslationKind(), locale);
			} catch (TranslationException e) {
			}
			msg = (msg != null) ? msg : "";
			messagemap.put(locale, msg);
		}
	}

}
