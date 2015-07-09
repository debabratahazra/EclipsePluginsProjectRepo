package com.odcgroup.translation.ui.editor.controls;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.ui.editor.model.ITranslationTableItem;

/**
 *
 * @author pkk
 *
 */
public class MultiTranslationTableLabelProvider extends LabelProvider implements ITableLabelProvider {
	
	/**
	 * 
	 */
	private ITranslationPreferences preferences;
	private LabelProvider modelLabelProvider;

	private Table table;
	private Map<URL, Image> images;

	/**
	 * @param preferences
	 */
	public MultiTranslationTableLabelProvider(ITranslationPreferences preferences, Table table) {
		this.preferences = preferences;
		this.table = table;
		this.images = new HashMap<URL, Image>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
	 * .lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		ITranslationTableItem model = (ITranslationTableItem) element;
		if (model.getTranslation() == null) {
			return null;
		}
		if (columnIndex == 0) {
			Object obj = AdapterUtils.getItemProviderImage((EObject) model.getTranslation().getOwner());
			if (obj instanceof Image) {
				return (Image) AdapterUtils.getItemProviderImage((EObject) model.getTranslation().getOwner());
			} else if (obj instanceof URL) {
				URL url = (URL)obj;
				Image image = images.get(url);
				if (null == image) {
					image = ImageDescriptor.createFromURL(url).createImage();
					images.put(url, image);
				}
				return image;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		for (Image image : images.values()) {
			if (image != null) {
				image.dispose();
			}
		}
		images.clear();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.
	 * lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		ITranslationTableItem model = (ITranslationTableItem) element;
		ITranslation translation = model.getTranslation();
		if (columnIndex == 0) {
			EObject eObj = (EObject) translation.getOwner();
			if (getModelLabelProvider() != null) {
				return getModelLabelProvider().getText(new StructuredSelection(eObj));
			}
			String mdfString = AdapterUtils.getItemProviderText(eObj);
			//DS-4442
			if(mdfString.contains("Mdf ")) mdfString = mdfString.replace("Mdf ", "");
				return mdfString;
		} else if (columnIndex == 1) {
			return translation.getDisplayName(model.getTranslationKind());
		} else if (columnIndex == 2) {
			try {
				return model.getTranslation().getText(model.getTranslationKind(), preferences.getDefaultLocale());
			} catch (TranslationException e) {
			}
		}
		for (Locale locale : preferences.getAdditionalLocales()) {
			TableColumn column = table.getColumn(columnIndex);
			if (column.getText().equals(LanguageUtils.getShortDisplayString(locale, Locale.ENGLISH))) {
				try {
					return model.getTranslation().getText(model.getTranslationKind(), locale);
				} catch (TranslationException e) {
				}
			}
		}
		return null;
	}
	
	/**
	 * @return the preferences
	 */
	public ITranslationPreferences getPreferences() {
		return preferences;
	}
	
	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @return the modelLabelProvider
	 */
	public LabelProvider getModelLabelProvider() {
		return modelLabelProvider;
	}

	/**
	 * @param modelLabelProvider the modelLabelProvider to set
	 */
	public void setModelLabelProvider(LabelProvider modelLabelProvider) {
		this.modelLabelProvider = modelLabelProvider;
	}


}
