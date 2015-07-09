package com.odcgroup.page.model.snippets.impl;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.SnippetModel;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.snippets.IParameterSnippet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.IQueryTabDisplay;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.widgets.impl.AutoCompleteDesign;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.model.widgets.matrix.impl.ConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.impl.CssClass;

/**
 *
 * @author pkk
 *
 */
public class SnippetFactory implements ISnippetFactory {
	
	
	/**
	 * Create a new snippet instance given its snippet type
	 * @param snippetName 
	 * @return snippet
	 */
	protected Snippet createSnippet(String snippetName) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		SnippetModel snippetmodel = metamodel.getSnippetModel();
		SnippetType sType = findSnippetType(snippetmodel, snippetName);
		Snippet snippet = createEmptySnippet(sType);
		snippet.getProperties().addAll(createProperties(sType));
		return snippet;
	}
	
	/**
	 * Gets the ModelFactory instance.
	 * 
	 * @return ModelFactory
	 */
	private static ModelFactory getModelFactory() {
		return ModelFactory.eINSTANCE;
	}
	
	/**
	 * @param snippetmodel 
	 * @param snippetTypeName
	 * @return snippetType
	 */
	private SnippetType findSnippetType(SnippetModel snippetmodel, String snippetTypeName) {
		List<SnippetType> snippets = snippetmodel.getSnippets();
		for (SnippetType snippetType : snippets) {
			if (snippetTypeName.equals(snippetType.getName())) {
				return snippetType;
			}
		}
		return null;
	}
	
	/**
	 * @param type
	 * @return list
	 */
	private List<Property> createProperties(SnippetType type) {
		List<Property> allProperties = new ArrayList<Property>();
		for (PropertyType pt : type.getPropertyTypes()) {
			Property p = createProperty(pt);
			allProperties.add(p);
		}
		return allProperties;
	}
	
	/**
	 * @param propertyType
	 * @return property
	 */
	private Property createProperty(PropertyType propertyType) {
		Property p = getModelFactory().createProperty();		
		p.setTypeName(propertyType.getName());
		String libraryName = propertyType.getLibrary() == null ? "xgui" : propertyType.getLibrary().getName();
		p.setLibraryName(libraryName);
		p.setValue(propertyType.getDefaultValue());
		return p;
	}
	
	/**
	 * @param type
	 * @return snippet
	 */
	private Snippet createEmptySnippet(SnippetType type) {
		Snippet snippet = getModelFactory().createSnippet();
		snippet.setTypeName(type.getName());
		return snippet;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptFilterSetSnippet(com.odcgroup.page.model.Snippet)
	 */
	public IFilterSet adaptFilterSetSnippet(Snippet snippet) {
		if (!FILTERSET_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a FilterSet");
		}
		return new FilterSet(snippet);	
	}	
	
	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptFilterSnippet(com.odcgroup.page.model.Snippet, com.odcgroup.page.model.Snippet)
	 */
	public IFilter adaptFilterSnippet(Snippet parent, Snippet snippet) {
		Snippet filterSnippet = findSnippet(parent.getContents(), snippet);
		if (!FILTER_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a Filter");
		}
		if (filterSnippet != null) {
			return new Filter(snippet, parent);
		}	
		return null;
	}
	
	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptFilterSetSnippet(java.util.List, int)
	 */
	public IFilterSet adaptFilterSetSnippet(List<Snippet> snippets, int index) {		
		List<Snippet> tSnippets = findSnippet(snippets, FILTERSET_SNIPPETTYPE);
		if (!tSnippets.isEmpty() && (index >= 0 && index < tSnippets.size()) ) {
			return new FilterSet(tSnippets.get(index));	
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptFilterSetSnippet(java.util.List)
	 */
	public IFilterSet adaptFilterSetSnippet(List<Snippet> snippets) {
		return adaptFilterSetSnippet(snippets, 0);
	}	

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#createFilter(com.odcgroup.page.model.snippets.IFilterSet)
	 */
	public IFilter createFilter(IFilterSet filterSet) {
		return new Filter(createSnippet(FILTER_SNIPPETTYPE), filterSet.getSnippet());
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#createFilterSet()
	 */
	public IFilterSet createFilterSet() {
		FilterSet fs =  new FilterSet(createSnippet(FILTERSET_SNIPPETTYPE));
		fs.setId(generateFilterSetID());
		return fs;
	}
	
	/**
	 * @param snippets
	 * @param required
	 * @return snippet
	 */
	private Snippet findSnippet(List<Snippet> snippets, Snippet required) {
		for (Snippet snippet : snippets) {
			if (required.equals(snippet)) {
				return snippet;
			}
		}
		return null;
	}
	
	/**
	 * @param snippets
	 * @param snippetTypeName
	 * @return snippet
	 */
	private List<Snippet> findSnippet(List<Snippet> snippets, String snippetTypeName) {
		List<Snippet> tSnippets = new ArrayList<Snippet>();
		for (Snippet snippet : snippets) {
			if (snippetTypeName.equals(snippet.getTypeName())) {
				tSnippets.add(snippet);
			}
		}
		return tSnippets;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptQuerySnippet(com.odcgroup.page.model.Snippet)
	 */
	public IQuery adaptQuerySnippet(Snippet snippet) {
		if (!QUERY_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a Query snippet");
		}
		return new Query(snippet);	
	}

	
	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptTapDisplay(com.odcgroup.page.model.Snippet, com.odcgroup.page.model.snippets.IQuery)
	 */
	public IQueryTabDisplay adaptTapDisplay(Snippet snippet, IQuery query) {
		if (!QUERYTABDISPLAY_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a QueryTabDisplay snippet");
		}
		return new QueryTabDisplay(snippet, query);	
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#createQuery()
	 */
	public IQuery createQuery() {
		return new Query(createSnippet(QUERY_SNIPPETTYPE));
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#createQueryTabDisplay(com.odcgroup.page.model.snippets.IQuery)
	 */
	public IQueryTabDisplay createQueryTabDisplay(IQuery query) {
		return new QueryTabDisplay(createSnippet(QUERYTABDISPLAY_SNIPPETTYPE), query);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptQuerySnippet(java.util.List)
	 */
	public IQuery adaptQuerySnippet(List<Snippet> snippets) {		
		List<Snippet> tSnippets = findSnippet(snippets, QUERY_SNIPPETTYPE);
		if (!tSnippets.isEmpty()) {
			return new Query(tSnippets.get(0));	
		}
		return null;
	}
	

	/**
	 * @return id
	 */
	public String generateFilterSetID() {
		char[] id = new char[3];
		int chr = 'A';
		int ref = 0;
		for (int i = 0; i < 3; i++) {
			ref = (int) (Math.random() * 6);
			switch (ref) {
			case 0:
				chr = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				chr = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				chr = 'A' + (int) (Math.random() * 26);
				break;
			case 4:
				chr = '0' + (int) (Math.random() * 10);
				break;
			case 3:
				chr = 'a' + (int) (Math.random() * 26);
				break;
			case 5:
				chr = 'A' + (int) (Math.random() * 26);
				break;
			}
			id[i] = (char) chr;
		}
		return "fs_"+new String(id);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptFilterSets(java.util.List)
	 */
	public List<IFilterSet> adaptFilterSets(List<Snippet> snippets) {		
		List<Snippet> tSnippets = findSnippet(snippets, FILTERSET_SNIPPETTYPE);
		List<IFilterSet> filterSets = new ArrayList<IFilterSet>();
		for (Snippet snippet : tSnippets) {
			filterSets.add(adaptFilterSetSnippet(snippet));
		}
		return filterSets;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptConditionalCssClass(com.odcgroup.page.model.Snippet)
	 */
	@Override
	public IConditionalCssClass adaptConditionalCssClass(Snippet snippet) {
		if (!CONDCSSCLASS_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a ConditionalCssClass snippet");
		}
		return new ConditionalCssClass(snippet);	
	
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#adaptCssClass(com.odcgroup.page.model.Snippet)
	 */
	@Override
	public ICssClass adaptCssClass(Snippet snippet) {
		if (!CSSCLASS_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a CssClass snippet");
		}
		return new CssClass(snippet);	
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#createConditionalCssClass()
	 */
	@Override
	public IConditionalCssClass createConditionalCssClass() {
		return new ConditionalCssClass(createSnippet(CONDCSSCLASS_SNIPPETTYPE));
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.snippets.ISnippetFactory#createCssClass()
	 */
	@Override
	public ICssClass createCssClass() {
		return new CssClass(createSnippet(CSSCLASS_SNIPPETTYPE));
	}

	@Override
	public IParameterSnippet adaptParameterSnippet(Snippet snippet) {
		if (!PARAMETER_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a Parameter snippet");
		}
		return new ParameterSnippet(snippet);	
	}

	@Override
	public IParameterSnippet createParameter() {
		return new ParameterSnippet(createSnippet(PARAMETER_SNIPPETTYPE));
	}

	@Override
	public ILineSnippet adaptLineSnippet(Snippet snippet, AutoCompleteDesign autoDesignWidget) {
		if (!LINE_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a Line snippet");
		}
		return new LineSnippet(snippet, autoDesignWidget);	
	}

	@Override
	public ILineItemSnippet adaptLineItemSnippet(Snippet snippet, ILineSnippet parent) {
		if (!LINEITEM_SNIPPETTYPE.equalsIgnoreCase(snippet.getTypeName())) {
			throw new IllegalArgumentException("This is not a LineItem snippet");
		}
		return new LineItemSnippet(snippet, parent);	
	}

	@Override
	public ILineSnippet createLine(AutoCompleteDesign autoDesignWidget) {
		return new LineSnippet(createSnippet(LINE_SNIPPETTYPE), autoDesignWidget);
	}

	@Override
	public ILineItemSnippet createLineItem(ILineSnippet parent) {
		return new LineItemSnippet(createSnippet(LINEITEM_SNIPPETTYPE), parent);
	}

}
