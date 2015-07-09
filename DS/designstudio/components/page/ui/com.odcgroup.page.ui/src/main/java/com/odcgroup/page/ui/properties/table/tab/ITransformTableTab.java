package com.odcgroup.page.ui.properties.table.tab;

import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.model.widgets.table.ITable;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public interface ITransformTableTab {
	
	/**
	 * @param input
	 * @param part
	 */
	void setInput(ITable input, IWorkbenchPart part);
	
	/**
	 * 
	 */
	void refresh();
	
	/**
	 * @param enabled
	 */
	void setEnabled(boolean enabled);

}
