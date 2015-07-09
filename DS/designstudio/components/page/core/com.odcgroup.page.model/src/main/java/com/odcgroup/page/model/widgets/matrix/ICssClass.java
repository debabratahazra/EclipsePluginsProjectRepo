package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.snippets.ISnippetAdapter;

/**
 *
 * @author pkk
 *
 */
public interface ICssClass extends ISnippetAdapter {
	
	/**
	 * @return the cssclass type
	 */
	Property getCssClassType();
	
	/**
	 * @return the specific cssClass if type is specific or null
	 */
	Property getSpecificCssClass();
	
	/**
	 * @return the list of conditional classes
	 */
	List<IConditionalCssClass> getConditionalCssClasses();
	
	/**
	 * 
	 */
	void removeConditionalCssClass(IConditionalCssClass condition);
	
	/**
	 * @return
	 */
	boolean isCorporate();
	
	/**
	 * @return
	 */
	boolean isConditional();
	
	/**
	 * @return
	 */
	boolean isSpecific();

}
