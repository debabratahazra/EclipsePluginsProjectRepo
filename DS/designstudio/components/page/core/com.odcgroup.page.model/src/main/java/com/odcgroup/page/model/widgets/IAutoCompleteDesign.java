package com.odcgroup.page.model.widgets;

import java.util.List;

import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 * @author pkk
 *
 */
public interface IAutoCompleteDesign extends IWidgetAdapter {
	
	/**
	 * @return
	 */
	ILineSnippet getFirstLine();
	
	/**
	 * @return
	 */
	ILineSnippet getSecondLine();
	
	/**
	 * @return
	 */
	ILineSnippet getThirdLine();
	
	/**
	 * @return
	 */
	String getTitleAttribute();
	
	/**
	 * @return
	 */
	String getSortAttribute();	
	
	/**
	 * @return
	 */
	List<ILineSnippet> getLines();
	
	/**
	 * @return
	 */
	int getMaxReturnedRows();

}
