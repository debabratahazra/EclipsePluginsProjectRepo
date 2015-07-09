package com.odcgroup.translation.ui.internal.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public class TranslationLanguageContentProvider implements ITreeContentProvider {

	private List<Locale> locales;
	
	@Override
	public void dispose() {
		if (locales != null) {
			locales.clear();
		}
	}

	@Override
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

	@Override
	public Object[] getElements(Object inputElement) {
		return locales.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		ITranslationModel model = (ITranslationModel) parentElement;
		return new String[] {model.getText(Locale.US) };
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
}
