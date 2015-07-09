package com.odcgroup.page.model.snippets.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.PageModelCore;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.IQueryTabDisplay;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.util.SearchModuleUtils;

/**
 *
 * @author pkk
 *
 */
public class Query extends SnippetAdapter implements IQuery {
	
	private static final String MAXROWS_PROPERTYTYPE = "max-rows";

	/**
	 * @param snippet
	 */
	public Query(Snippet snippet) {
		super(snippet);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getAttributes()
	 */
	public String[] getAttributes() {
		String value =  getPropertyValue(PropertyTypeConstants.QUERY_ATTRIBUTES);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return value.split(",");
	}
	
	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getAttributesAsString()
	 */
	public String getAttributesAsString() {
		if (getAttributes() == null) {
			return null;
		}
		return joinByDelimiter(getAttributes(), ",");
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getMappings()
	 */
	public String[] getMappings() {
		String value =  getPropertyValue(PropertyTypeConstants.QUERY_MAPPINGS);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return value.split(",");
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getSearchCriteriaModule()
	 */
	public String getSearchCriteriaModule() {
		Model model = getProperty(PropertyTypeConstants.QUERY_OUTPUTMODULE).getModel();
    	String uri = "";
    	if (model != null) {
    		Resource res = model.eResource();
    		if (res != null) {
    			uri = res.getURI().toString();
    		} else {
    			uri = EcoreUtil.getURI(model).toString();
    		}
    	}
    	return uri;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getSearchCriteriaModuleModel()
	 */
	public Model getSearchCriteriaModuleModel() {
		return getProperty(PropertyTypeConstants.QUERY_OUTPUTMODULE).getModel();
	}


	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#setAttributes(java.lang.String[])
	 */
	public void setAttributes(String[] attributes) {
		if (attributes != null) {
			String value = joinByDelimiter(attributes, ",");
			setPropertyValue(PropertyTypeConstants.QUERY_ATTRIBUTES, value);
		}
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#setMappings(java.lang.String[])
	 */
	public void setMappings(String[] mappings) {
		if (mappings != null) {
			String value = joinByDelimiter(mappings, ",");
			setPropertyValue(PropertyTypeConstants.QUERY_MAPPINGS, value);
		}
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#setSearchCriteriaModule(java.lang.Object)
	 */
	public void setSearchCriteriaModule(Object module) {    	
    	if (module instanceof Model) {
    		// set the model
    		Model newModel = (Model) module;
	    	Property p = getProperty(PropertyTypeConstants.QUERY_OUTPUTMODULE);
	    	if (newModel != p.getModel()) {
	        	p.setModel(newModel);
	    	}
    	} else if (module instanceof String ){
    		throw new IllegalArgumentException("The new value must be a model instance");
    	}    	
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getTabDisplay()
	 */
	public IQueryTabDisplay getTabDisplay() {
		List<Snippet> contents = getSnippet().getContents();
		if (!contents.isEmpty()) {
			return getSnippetFactory().adaptTapDisplay(contents.get(0), this);
		}
		return null;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#setTabDisplay(com.odcgroup.page.model.snippets.IQueryTabDisplay)
	 */
	public void setTabDisplay(IQueryTabDisplay tabDisplay) {
		if (tabDisplay == null) {
			return;
		}
		List<Snippet> contents = getSnippet().getContents();
		if (!contents.isEmpty()) {
			EcoreUtil.replace(contents.get(0), tabDisplay.getSnippet());
		} else {
			getSnippet().getContents().add(tabDisplay.getSnippet());
		}
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getMappingsAsString()
	 */
	public String getMappingsAsString() {
		return joinByDelimiter(getMappings(), ",");
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#removeTabDisplay()
	 */
	public void removeTabDisplay() {
		List<Snippet> contents = getSnippet().getContents();
		if (!contents.isEmpty()) {
			IQueryTabDisplay tab = SnippetUtil.getSnippetFactory().createQueryTabDisplay(this);
			EcoreUtil.replace(contents.get(0), tab.getSnippet());
		}		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#removeAttributes()
	 */
	public void removeAttributes() {
		setPropertyValue(PropertyTypeConstants.QUERY_ATTRIBUTES, "");
		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#removeMappings()
	 */
	public void removeMappings() {
		setPropertyValue(PropertyTypeConstants.QUERY_MAPPINGS, "");		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getSelectionMode()
	 */
	public String getSelectionMode() {
		return getPropertyValue(PropertyTypeConstants.QUERY_SELECTIONMODE);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#runAtStart()
	 */
	public boolean runAtStart() {
		String val = getPropertyValue(PropertyTypeConstants.QUERY_RUNATSTART);
		return Boolean.valueOf(val).booleanValue();
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#setRunAtStart(boolean)
	 */
	public void setRunAtStart(boolean runAtStart) {
		setPropertyValue(PropertyTypeConstants.QUERY_RUNATSTART, runAtStart);
		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#setSelectionMode(java.lang.String)
	 */
	public void setSelectionMode(String selectionMode) {
		if (!StringUtils.isEmpty(selectionMode)) {
			setPropertyValue(PropertyTypeConstants.QUERY_SELECTIONMODE, selectionMode);
		}		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#getSearchModuleDomainEntity()
	 */
	public String getSearchModuleDomainEntity() {
		Model module = getSearchCriteriaModuleModel();
		String entity = null;
		if (module != null) {
			String tabId = getTabDisplay() != null ? getTabDisplay().getSelection() : null;
			Widget widget = SearchModuleUtils.getInputModule(module, tabId);
			if (widget == null) {
				widget = module.getWidget();
			} 
			if (widget == null) {
				// apparently the module represents an invalid reference
				URI uri = EcoreUtil.getURI(module);
				PageModelCore.getDefault().logError("Cannot load "+uri.trimFragment(), null); // //$NON-NLS-1$
			} else {
				entity = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			}
		}
		return entity;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IQuery#removeFilterSets()
	 */
	public void removeFilterSets() {
		List<Snippet> snippets = ((Event) getSnippet().eContainer()).getSnippets();
		for (int i = 0; i < 3;i++) {
			IFilterSet filterSet = SnippetUtil.getSnippetFactory().adaptFilterSetSnippet(snippets, i);
			if (filterSet != null) {
				snippets.remove(filterSet.getSnippet());
			}
		}
		
	}

	@Override
	public int getMaxRowCount() {
		return getProperty(MAXROWS_PROPERTYTYPE).getIntValue();
	}

	@Override
	public void setMaxRowCount(int count) {
		setPropertyValue(MAXROWS_PROPERTYTYPE, count);		
	}

}
