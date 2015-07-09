package com.odcgroup.page.ui.properties.autocomplete.tab;

import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.model.snippets.ILineSnippet;

/**
 * @author pkk
 *
 */
public interface IAutoCompleteLineTab {
	
	/**
	 * @param input
	 * @param part
	 */
	void setInput(ILineSnippet input, IWorkbenchPart part);
	
	/**
	 * 
	 */
	void refresh();
	
	/**
	 * @param enabled
	 */
	void setEnabled(boolean enabled);

}
