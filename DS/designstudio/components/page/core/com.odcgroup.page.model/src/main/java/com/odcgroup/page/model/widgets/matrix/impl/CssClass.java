package com.odcgroup.page.model.widgets.matrix.impl;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.snippets.impl.SnippetAdapter;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;

/**
 *
 * @author pkk
 *
 */
public class CssClass extends SnippetAdapter implements ICssClass {
	
	private static final String PROP_CSSCLASSTYPE = "cssClassType";	
	private static final String PROP_SPECIFIC = "specificClass";

	/**
	 * @param snippet
	 */
	public CssClass(Snippet snippet) {
		super(snippet);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#getConditionalCssClass()
	 */
	@Override
	public List<IConditionalCssClass> getConditionalCssClasses() {
		List<Snippet> contents = getSnippet().getContents();
		List<IConditionalCssClass> conditions = new ArrayList<IConditionalCssClass>();
		for (Snippet snippet : contents) {
			if(snippet.getTypeName().equals(ISnippetFactory.CONDCSSCLASS_SNIPPETTYPE)) {
				conditions.add(SnippetUtil.getSnippetFactory().adaptConditionalCssClass(snippet));
			}
		}
		return conditions;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#getCssClassType()
	 */
	@Override
	public Property getCssClassType() {
		return getProperty(PROP_CSSCLASSTYPE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#getSpecificCssClass()
	 */
	@Override
	public Property getSpecificCssClass() {
		return getProperty(PROP_SPECIFIC);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#isConditional()
	 */
	@Override
	public boolean isConditional() {
		String type = getCssClassType().getValue();
		if (type.equals("conditional")) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#isCorporate()
	 */
	@Override
	public boolean isCorporate() {
		String type = getCssClassType().getValue();
		if (type.equals("corporate")) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#isSpecific()
	 */
	@Override
	public boolean isSpecific() {
		String type = getCssClassType().getValue();
		if (type.equals("specific")) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.ICssClass#removeConditionalCssClass()
	 */
	@Override
	public void removeConditionalCssClass(IConditionalCssClass condition) {
		if (condition != null) {
			getSnippet().getContents().remove(condition.getSnippet());
		}
		
	}

}
