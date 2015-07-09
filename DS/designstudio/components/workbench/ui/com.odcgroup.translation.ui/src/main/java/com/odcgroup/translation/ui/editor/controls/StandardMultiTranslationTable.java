package com.odcgroup.translation.ui.editor.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;

import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.translation.ui.editor.model.ITranslationTableColumn;
import com.odcgroup.translation.ui.editor.model.TranslationTableColumn;

/**
 *
 * @author pkk
 *
 */
public class StandardMultiTranslationTable extends MultiTranslationTable {
	

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.controls.MultiTranslationTable#getTranslationTableContentProvider()
	 */
	@Override
	protected MultiTranslationTableContentProvider getTranslationTableContentProvider() {
		return new MultiTranslationTableContentProvider();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.controls.MultiTranslationTable#getTranslationTableLabelProvider(com.odcgroup.translation.core.ITranslationPreferences, org.eclipse.swt.widgets.Table)
	 */
	@Override
	protected ITableLabelProvider getTranslationTableLabelProvider(ITranslationPreferences preferences, Table table) {
		return new MultiTranslationTableLabelProvider(preferences, table);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.translation.ui.editor.model.IMultiTranslationTable#getTableColumns()
	 */
	@Override
	public List<ITranslationTableColumn> getTableColumns() {		
		List<ITranslationTableColumn> columns = new ArrayList<ITranslationTableColumn>();
		columns.add(new TranslationTableColumn("Translation of", SWT.LEFT, 15, true));
		columns.add(new TranslationTableColumn("Kind", SWT.LEFT, 10, true));
		
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(getPreferences().getDefaultLocale());
		locales.addAll(getPreferences().getAdditionalLocales());
		
		int weight = Math.round(85/locales.size());
		TranslationTableColumn column = null;
		for (Locale locale : locales) {
			column = new TranslationTableColumn(LanguageUtils.getShortDisplayString(locale, Locale.ENGLISH), SWT.LEFT, weight, true);
			column.setLocale(locale);
			columns.add(column);
		}
		
		return columns;
	}

}
