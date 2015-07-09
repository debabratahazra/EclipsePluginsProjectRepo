package com.odcgroup.t24.version.editor.ui.providers;

import java.util.Locale;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;

public class LanguageViewerLableProvider extends ObservableMapLabelProvider {

	public LanguageViewerLableProvider(IObservableMap[] attributeMap) {
		super(attributeMap);
		
	}

	@Override
    public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Locale) {
			return ((Locale) element).getDisplayName();
		}
		return super.getColumnText(element, columnIndex);
    }
  
}
