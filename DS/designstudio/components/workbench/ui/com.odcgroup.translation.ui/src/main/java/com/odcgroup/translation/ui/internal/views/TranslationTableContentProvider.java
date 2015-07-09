package com.odcgroup.translation.ui.internal.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationTableContentProvider;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public class TranslationTableContentProvider implements ITranslationTableContentProvider {
	
	private List<Locale> locales;
	
	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		return locales.toArray();
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		if (locales != null) {
			locales.clear();
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		locales = new ArrayList<Locale>();
		if (newInput != null) {
			ITranslationModel model = (ITranslationModel)newInput;
			Locale dl = model.getDefaultLocale();
			if (dl != null) {
				locales.add(dl);
			}
			locales.addAll(model.getAdditionalLocales());
		}
	}

}
