package com.odcgroup.page.model.widgets.impl;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.widgets.IAutoCompleteDesign;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

/**
 * @author pkk
 *
 */
public class AutoCompleteDesign extends WidgetAdapter implements
		IAutoCompleteDesign {
	
	private static final String TITLEATTRIBUTE_PROPERTYTYPE = "titleAttribute";
	private static final String SORTATTRIBUTE_PROPERTYTYPE = "sortAttribute";
	private static final String MAXRETURNED_PROPERTYTYPE = "max-returned";

	/**
	 * @param widget
	 */
	public AutoCompleteDesign(Widget widget) {
		super(widget);
	}
	
	/**
	 * @return
	 */
	private ILineSnippet getLineSnippet(int index) {
		List<Snippet> snippets = getWidget().getSnippets();
		if (!snippets.isEmpty() && snippets.size()>index) {
			return SnippetUtil.getSnippetFactory().adaptLineSnippet(snippets.get(index), this);
		} else {
			return SnippetUtil.getSnippetFactory().createLine(this);
		}
	}

	@Override
	public ILineSnippet getFirstLine() {
		return getLineSnippet(0);
	}

	@Override
	public ILineSnippet getSecondLine() {
		return getLineSnippet(1);
	}

	@Override
	public ILineSnippet getThirdLine() {
		return getLineSnippet(2);
	}

	@Override
	public String getTitleAttribute() {
		return getPropertyValue(TITLEATTRIBUTE_PROPERTYTYPE);
	}

	@Override
	public String getSortAttribute() {
		return getPropertyValue(SORTATTRIBUTE_PROPERTYTYPE);
	}

	@Override
	public List<ILineSnippet> getLines() {
		List<ILineSnippet> lines = new ArrayList<ILineSnippet>();
		List<Snippet> snippets = getWidget().getSnippets();
		for (Snippet snippet : snippets) {
			lines.add(SnippetUtil.getSnippetFactory().adaptLineSnippet(snippet, this));
		}
		return lines;
	}

	@Override
	public int getMaxReturnedRows() {
		Property property = getWidget().findProperty(MAXRETURNED_PROPERTYTYPE);
		if (property != null) {
			return property.getIntValue();
		}
		return 0;
	}

}
