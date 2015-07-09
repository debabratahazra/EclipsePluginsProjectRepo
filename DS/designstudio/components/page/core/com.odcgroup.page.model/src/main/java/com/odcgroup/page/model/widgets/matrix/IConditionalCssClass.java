package com.odcgroup.page.model.widgets.matrix;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.snippets.ISnippetAdapter;

/**
 *
 * @author pkk
 *
 */
public interface IConditionalCssClass extends ISnippetAdapter {
	
	/**
	 * @return name of the conditional css class
	 */
	Property getName();
	
	/**
	 * @return the conditional code
	 */
	Property getCondition();
	
	/**
	 * @return the result if the condition is true
	 */
	Property getResult();

}
