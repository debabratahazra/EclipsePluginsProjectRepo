package com.odcgroup.page.transformmodel.snippet;

import org.w3c.dom.Element;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 *
 * @author pkk
 *
 */
public class SnippetTransformer {
	
	private Widget widget;
	
	/**
	 * 
	 */
	public SnippetTransformer() {		
	}
	
	/**
	 * @param context
	 * @param snippet
	 * @param functionElement
	 * @param widget
	 * @param autocomplete
	 */
	public void transform(WidgetTransformerContext context, Snippet snippet, Element functionElement, Widget widget, boolean autocomplete)  {
		if (snippet == null) {
			return;
		}
		this.widget = widget;
		ISnippetFactory factory = getSnippetFactory();
		if (snippet.getTypeName().equals(ISnippetFactory.FILTERSET_SNIPPETTYPE)) {
			// transform filterset
			FilterSetSnippetTransformer transformer = new FilterSetSnippetTransformer(functionElement);
			transformer.transform(context, factory.adaptFilterSetSnippet(snippet), widget);
		} else if (snippet.getTypeName().equals(ISnippetFactory.QUERY_SNIPPETTYPE)) {
			// transform query
			QuerySnippetTransformer transformer = new QuerySnippetTransformer(functionElement);
			transformer.transform(context, factory.adaptQuerySnippet(snippet), autocomplete);
		}
	}
	
	/**
	 * @return
	 */
	public Widget getWidget() {
		return  this.widget;
	}
	
	/**
	 * @return factory
	 */
	private ISnippetFactory getSnippetFactory() {
		return SnippetUtil.getSnippetFactory();
	}

}
