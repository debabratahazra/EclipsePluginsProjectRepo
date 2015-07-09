package com.odcgroup.page.model.snippets;

import java.util.List;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.widgets.impl.AutoCompleteDesign;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;


/**
 *
 * @author pkk
 *
 */
public interface ISnippetFactory {
	

	/** */
	String FILTERSET_SNIPPETTYPE = "FilterSet";
	/** */
	String FILTER_SNIPPETTYPE = "Filter";
	/** */
	String QUERY_SNIPPETTYPE = "Query";
	/** */
	String QUERYTABDISPLAY_SNIPPETTYPE = "QueryTabDisplay";
	/** */
	String CSSCLASS_SNIPPETTYPE = "CssClass";
	/** */
	String PARAMETER_SNIPPETTYPE = "Parameter";
	/** */
	String LINE_SNIPPETTYPE = "Line";
	/** */
	String LINEITEM_SNIPPETTYPE = "LineItem";
	/** */
	String CONDCSSCLASS_SNIPPETTYPE = "ConditionalCssClass";
	/** */
	String MATRIXSORT_SNIPPETTYPE = "MatrixSort";
	
	/**
	 * @param filterSet 
	 * @return filter
	 */
	IFilter createFilter(IFilterSet filterSet);
	
	/**
	 * @return filterSet
	 */
	IFilterSet createFilterSet();
	
	/**
	 * @return query
	 */
	IQuery createQuery();
	
	/**
	 * @param query 
	 * @return tabDisplay
	 */
	IQueryTabDisplay createQueryTabDisplay(IQuery query);
	
	/**
	 * @param parent
	 * @param snippet 
	 * @return filter
	 */
	IFilter adaptFilterSnippet(Snippet parent, Snippet snippet);
	
	/**
	 * @param snippet
	 * @return filterSet
	 */
	IFilterSet adaptFilterSetSnippet(Snippet snippet);
	
	/**
	 * @param snippet
	 * @return
	 */
	IParameterSnippet adaptParameterSnippet(Snippet snippet);
	
	/**
	 * @param snippet
	 * @return
	 */
	ILineSnippet adaptLineSnippet(Snippet snippet, AutoCompleteDesign autoDesignWidget);
	
	/**
	 * @param snippet
	 * @return
	 */
	ILineItemSnippet adaptLineItemSnippet(Snippet snippet, ILineSnippet parent);
	

	/**
	 * @param snippets
	 * @param index
	 * @return filterSet
	 */
	IFilterSet adaptFilterSetSnippet(List<Snippet> snippets, int index);
	
	/**
	 * @param snippets
	 * @return list
	 */
	List<IFilterSet> adaptFilterSets(List<Snippet> snippets);

	/**
	 * @param snippets
	 * @return filterSet
	 */
	IFilterSet adaptFilterSetSnippet(List<Snippet> snippets);

	/**
	 * @param snippets
	 * @return query
	 */
	IQuery adaptQuerySnippet(List<Snippet> snippets);
	
	/**
	 * @param snippet
	 * @param query 
	 * @return tabDisplay
	 */
	IQueryTabDisplay adaptTapDisplay(Snippet snippet, IQuery query);
	
	/**
	 * @param snippet
	 * @return query
	 */
	IQuery adaptQuerySnippet(Snippet snippet);
	
	
	/**
	 * @return
	 */
	ICssClass createCssClass();
	
	/**
	 * @return
	 */
	IConditionalCssClass createConditionalCssClass();
	
	
	/**
	 * @return
	 */
	IParameterSnippet createParameter();
	
	/**
	 * @return
	 */
	ILineSnippet createLine(AutoCompleteDesign autoDesignWidget);
	
	/**
	 * @return
	 */
	ILineItemSnippet createLineItem(ILineSnippet parent);
	
	/**
	 * @param snippet
	 * @return
	 */
	ICssClass adaptCssClass(Snippet snippet);
	
	/**
	 * @param snippet
	 * @return
	 */
	IConditionalCssClass adaptConditionalCssClass(Snippet snippet);
	
	/**
	 * @return id
	 */
	String generateFilterSetID();
	

}
