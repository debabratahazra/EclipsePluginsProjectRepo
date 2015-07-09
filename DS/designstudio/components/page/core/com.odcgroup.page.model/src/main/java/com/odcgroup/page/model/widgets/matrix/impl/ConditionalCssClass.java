package com.odcgroup.page.model.widgets.matrix.impl;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.impl.SnippetAdapter;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;

/**
 *
 * @author pkk
 *
 */
public class ConditionalCssClass extends SnippetAdapter implements IConditionalCssClass {
	
	private static final String PROP_CONDITION = "condition";
	private static final String PROP_NAME = "name";
	private static final String PROP_RESULT = "result";

	/**
	 * @param snippet
	 */
	public ConditionalCssClass(Snippet snippet) {
		super(snippet);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IConditionalCssClass#getCondition()
	 */
	@Override
	public Property getCondition() {
		return getProperty(PROP_CONDITION);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IConditionalCssClass#getName()
	 */
	@Override
	public Property getName() {
		return getProperty(PROP_NAME);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IConditionalCssClass#getResult()
	 */
	@Override
	public Property getResult() {
		return getProperty(PROP_RESULT);
	}

}
