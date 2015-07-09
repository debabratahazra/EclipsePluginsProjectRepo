package com.odcgroup.translation.ui.views.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.internal.views.model.BaseTranslationModel;

/**
 * The TranslationModel 
 * 
 * @author atr
 */
public class TranslationModel extends BaseTranslationModel implements ITranslationChangeListener {

	private ITranslationPreferences preferences;
	private ITranslation translation;

	/** The current selected translation kind */
	private ITranslationKind kind;

	/* all available translation kind */
	private Map<ITranslationKind,Boolean> translationKinds;
	
	/** The current selected locale */
	private Locale locale;
	
	/** Provide a small message regarding the current selected message */
	private String information = "";
	private Map<ITranslationKind,Boolean> multiLineEditorMapper;
	
	@Override
	public boolean acceptRichText() {
		return translation.acceptRichText(kind);
	}
	
	@Override
	public void notifyChange(ITranslationChangeEvent event) {
		String oldText = event.getOldText();
		String newText = event.getNewText();
		if (!StringUtils.equals(oldText, newText)) {
			firePropertyChange(TRANSLATION_TEXT_PROPERTY, oldText, newText);
		}
	}
	
	@Override
	public void release() {
		translation.removeTranslationChangeListener(this);
		super.release();
	}

	@Override
	public final List<Locale> getAdditionalLocales() {
		return preferences.getAdditionalLocales();
	}

	@Override
	public final Locale getDefaultLocale() {
		return preferences.getDefaultLocale();
	}

	@Override
	public ITranslationKind getDefaultKind() {
		ITranslationKind[] kinds = getTranslationKinds();
		if (kinds.length > 0) {
			return kinds[0];
		}
		return null;
	}
	
	@Override 
	public final String getInformation() {
		return this.information;
	}
	
	@Override
	public final String getOrigin(Locale locale) {
		
		String origin = "Local";
		
		if (translation.isInheritable()) {
			try {
				boolean inherited = translation.isInherited(this.kind, locale);
				if (inherited) {
					origin = "Inherited";
				}
			} catch (TranslationException e) {
				origin = "";
			}
		} 

		// The origin is empty if no translation is defined.
		try {
			String text = translation.getText(kind, locale);
			if (text == null) {
				origin = "";
			}
		} catch (TranslationException ex) {
			origin = "";
		}
		
		return origin;
	}

	@Override 
	public final void setInformation(String newInformation) {
		String oldInformation = this.information;
		this.information = newInformation;
		firePropertyChange(TRANSLATION_INFO_PROPERTY, oldInformation, newInformation);
	}
	
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

	@Override
	public final String getDisplayName(ITranslationKind kind) {
		return translation.getDisplayName(kind);
	}	
	
	@Override
	public final ITranslationKind[] getTranslationKinds() {
		List<ITranslationKind> list  =  new ArrayList<ITranslationKind>();
		for (ITranslationKind kind : translationKinds.keySet()) {
			if(translationKinds.get(kind)) {
				list.add(kind);
			}
		}
		return list.toArray(new ITranslationKind[]{});
	}

	@Override
	public final void selectKind(ITranslationKind newKind) {
		if (newKind == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		ITranslationKind oldKind = this.kind;
		this.kind = newKind;
		if (!newKind.equals(oldKind)) {
			firePropertyChange(TRANSLATION_KIND_PROPERTY, oldKind, newKind);
		}
	}
	
	@Override
	public final ITranslationKind getSelectedKind() {
		return this.kind;
	}
	
	@Override
	public final void selectLocale(Locale newLocale) {
		if (newLocale == null) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		Locale oldLocale = this.locale;
		this.locale = newLocale;
		if (!newLocale.equals(oldLocale)) {
			firePropertyChange(TRANSLATION_LOCALE_PROPERTY, oldLocale, newLocale);
		}
	}

	@Override
	public final Locale getSelectedLocale() {
		return locale;
	}

	@Override
	public final void setText(String newText) {
		setText(locale, newText);
	}
	@Override
	public final String getText() {
		return getText(locale);
	}

	@Override
	public void setText(Locale locale, String newText) {
		try {
			translation.setText(kind, locale, newText);
		} catch (TranslationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getText(Locale locale) {
		String result = "";
		try {
			result = translation.getText(kind, locale);
		} catch (TranslationException ex) {
			// TODO Auto-generated catch block
			//ex.printStackTrace();
		}
		return result;
	}

	@Override
	public final ITranslation getTranslation() {
		return translation;
	}
	
	/**
	 * @param manager
	 */
	public TranslationModel(ITranslationPreferences preferences, ITranslation translation) {
		if (preferences == null || translation == null) {
			throw new IllegalArgumentException();
		}
		this.preferences = preferences;
		this.translation = translation;
		this.translation.addTranslationChangeListener(this);

		translationKinds = new LinkedHashMap<ITranslationKind, Boolean>();
		accept(this.translation.getAllKinds());
		disableMultiLines();
		selectKind(getDefaultKind());
		selectLocale(preferences.getDefaultLocale());
	}

	private void disableMultiLines() {
		multiLineEditorMapper = new LinkedHashMap<ITranslationKind,Boolean>();
		for (ITranslationKind iTransKind : ITranslationKind.values()) {
			multiLineEditorMapper.put(iTransKind, Boolean.FALSE);
		}
	}

	@Override
	public void accept(ITranslationKind[] kinds) {
		for(ITranslationKind kind: kinds) {
			translationKinds.put(kind, Boolean.TRUE);
		} 
	}

	@Override
	public void reject(ITranslationKind[] kinds) {
		for(ITranslationKind kind: kinds) {
			translationKinds.put(kind, Boolean.FALSE);
		} 
	}

	@Override
	public void setMultiLinesEditor(ITranslationKind kind, boolean activate) {
		if(null == kind) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		if(activate) {
			multiLineEditorMapper.put(kind, Boolean.TRUE);
		} else {
			multiLineEditorMapper.put(kind, Boolean.FALSE);
		}  
	}

	@Override
	public boolean hasMultiLinesEditor(ITranslationKind kind) {
		return multiLineEditorMapper.get(kind);
	}

}
