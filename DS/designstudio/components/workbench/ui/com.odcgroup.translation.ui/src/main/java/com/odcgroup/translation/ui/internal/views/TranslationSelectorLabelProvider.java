package com.odcgroup.translation.ui.internal.views;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class TranslationSelectorLabelProvider extends ColumnLabelProvider {

	/** */
	private ITranslationModel model;
	
	/**
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		ITranslationKind kind = (ITranslationKind) element;
		return model.getDisplayName(kind);
	}
	
	public void setModel(ITranslationModel model) {
		this.model = model;
	}
	
	/**
	 * @param model
	 */
	public TranslationSelectorLabelProvider() {
	}

}
