package com.odcgroup.page.model.snippets;

import java.util.List;

import com.odcgroup.page.model.widgets.IAutoCompleteDesign;



public interface ILineSnippet extends ISnippetAdapter {
	
	/**
	 * @return
	 */
	ILineItemSnippet getFirstLineItem();
	/**
	 * @return
	 */
	ILineItemSnippet getSecondLineItem();
	/**
	 * @return
	 */
	ILineItemSnippet getThirdLineItem();
	
	/**
	 * @return
	 */
	List<ILineItemSnippet> getLineItems();
	
	/**
	 * @return
	 */
	IAutoCompleteDesign getParentWidget();

}
