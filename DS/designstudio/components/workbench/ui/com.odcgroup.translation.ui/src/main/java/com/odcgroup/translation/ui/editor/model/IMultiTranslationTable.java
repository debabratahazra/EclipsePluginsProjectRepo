package com.odcgroup.translation.ui.editor.model;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.translation.core.ITranslationPreferences;


/**
 *
 * @author pkk
 */
public interface IMultiTranslationTable {

	/**
	 * @param tableModel
	 */
	void setTranslations(ITranslationTableModel tableModel);
	
	/**
	 * @param body
	 * @param preferences
	 */
	void createControls(Composite body, ITranslationPreferences preferences);

	/**
	 * dispose the table
	 */
	void dispose();
	
	/**
	 * @return
	 */
	TableViewer getTableViewer();
	
	/**
	 * @return
	 */
	List<ITranslationTableColumn> getTableColumns();
	
	
	/**
	 * @param labelProvider
	 */
	void setModelLabelProvider(LabelProvider labelProvider);

}
