package com.odcgroup.page.model.snippets.impl;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.widgets.impl.AutoCompleteDesign;

/**
 * @author pkk
 *
 */
public class LineSnippet extends SnippetAdapter implements ILineSnippet {
	
	private AutoCompleteDesign autoDesignWidget;

	/**
	 * @param snippet
	 */
	public LineSnippet(Snippet snippet, AutoCompleteDesign autoDesignWidget) {
		super(snippet);
		this.autoDesignWidget = autoDesignWidget;
	}
	
	/**
	 * @param index
	 * @return
	 */
	private ILineItemSnippet getLineItem(int index) {
		List<Snippet> items = getSnippet().getContents();
		if (!items.isEmpty() && items.size()>index) {
			return SnippetUtil.getSnippetFactory().adaptLineItemSnippet(items.get(index), this);
		}
		return null;
	}


	@Override
	public ILineItemSnippet getFirstLineItem() {		
		return getLineItem(0);
	}


	@Override
	public ILineItemSnippet getSecondLineItem() {		
		return getLineItem(1);
	}


	@Override
	public ILineItemSnippet getThirdLineItem() {		
		return getLineItem(2);
	}

	@Override
	public List<ILineItemSnippet> getLineItems() {
		List<Snippet> snips = getSnippet().getContents();
		List<ILineItemSnippet> items = new ArrayList<ILineItemSnippet>();
		for (Snippet snippet : snips) {
			items.add(SnippetUtil.getSnippetFactory().adaptLineItemSnippet(snippet, this));
		}
		return items;
	}

	@Override
	public AutoCompleteDesign getParentWidget() {
		return autoDesignWidget;
	}

}
