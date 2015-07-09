package com.odcgroup.page.transformmodel.snippet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.IQueryTabDisplay;
import com.odcgroup.page.model.util.DatasetAttribute;
import com.odcgroup.page.model.util.SearchDomainObjectUtil;
import com.odcgroup.page.model.util.SearchModuleUtils;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * transformer for the event query snippet
 * 
 * @author pkk
 */
public class QuerySnippetTransformer extends BaseSnippetTransformer{


	/** */
	private static final String OUTPUT = SnippetTransformerConstants.QUERY_OUTPUT;
	/** */
	private static final String PREFIX = PARAM_PREFIX+".";
	/** */
	private static final String MODELREF = SnippetTransformerConstants.QUERY_MODELREF;
	/** */
	private static final String BEANNAME = SnippetTransformerConstants.QUERY_BEANNAME;
	/** */
	private static final String BEANPROPERTY = SnippetTransformerConstants.QUERY_BEANPROPERTY;
	/** */
	private static final String SELMODE = SnippetTransformerConstants.QUERY_SELECTIONMODE;
	/** */
	private static final String RUNSTART = SnippetTransformerConstants.QUERY_RUNATSTART;
	/** */
	private static final String INCLUDE = SnippetTransformerConstants.QUERY_ATTR_INCLUDE;
	/** */
	private static final String EXCLUDE = SnippetTransformerConstants.QUERY_ATTR_EXCLUDE;

	
	/**
	 * @param functionElement
	 */
	public QuerySnippetTransformer(Element functionElement) {
		super(functionElement);
	}
	
	/**
	 * @param context
	 * @param query 
	 */
	public void transform(WidgetTransformerContext context, IQuery query, boolean autocomplete)  {
		if (query == null) {
			return;
		}
		
		// transform selection attributes
		transformAttributes(context, query);
		
		Model model = query.getSearchCriteriaModuleModel();
		String tabId = query.getTabDisplay() != null ? query.getTabDisplay().getSelection() : null;
		Widget outputModule = SearchModuleUtils.getOutputModule(model, tabId);
		
		if (outputModule != null ) {			
			// fetch the dataset from the output module
			String datasetName = outputModule.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
			if (!StringUtils.isEmpty(datasetName)) {
				transformSnippetParameter(context, PREFIX+OUTPUT, datasetName);				
			}
			if (!autocomplete) {
				// fetch the tableModelRef from the output module
				ITable table = SearchModuleUtils.getTableWidget(outputModule);
				String tableModelRef = null;
				if (table != null) {
					tableModelRef = table.getModelReference();
				}
				if (StringUtils.isEmpty(tableModelRef)) {
					tableModelRef = "aaa-search.output";
				}
				transformSnippetParameter(context, PREFIX+MODELREF, tableModelRef);	
			}
		}		

		// generate the parameters specific to query mappings	
		if (query.getMappings() != null && query.getMappings().length > 0) {
			
			if (!context.isEditableTableTree()) {
				List<?> parentWidgets = context.getParentWidgets();
				if (!parentWidgets.isEmpty()) {
					Widget widget = getWidgetWithAssociation(parentWidgets);
					if (widget != null && widget.getTypeName().equals(WidgetTypeConstants.INCLUDE)) {
						String entity = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
						String assoc = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
						if (!StringUtils.isEmpty(entity)) {
							transformSnippetParameter(context, PREFIX+BEANNAME, entity);
							int ii = 1;
							boolean associationFound = !StringUtils.isEmpty(assoc);
							for(String string : query.getMappings()) {
								String value = null;
								if (associationFound) {
									value = assoc+"."+string;
								} else {
									value = string;
								}

								// String beanPropertyPrefix = BeanPropertyUtils.findModuleBeanPropertyPrefix(context, widget);
								
								/// begin DS-7614
								String beanPropertyPrefix = "";
								List<Widget> list = context.getParentWidgets();
								// traverse the inclusion context in the reverse order
								for (int i = list.size(); --i >= 0;) {
									Widget x = list.get(i);
						            Property p = x.getRootWidget().findProperty(PropertyTypeConstants.BEAN_PROPERTY_PREFIX);
						            if (p != null) {
						            	beanPropertyPrefix += p.getValue();
						            	if (StringUtils.isNotBlank(p.getValue())) {
						            		beanPropertyPrefix = beanPropertyPrefix.trim();
						            		// only 1 level of prefix is supported.
						            		break;
						            	}
						            }
								}
								/// end DS-7614
								
								if(!beanPropertyPrefix.isEmpty() && (value != null)){
									value = beanPropertyPrefix +"."+value;
								}
								transformSnippetParameter(context, PREFIX+BEANPROPERTY+ii, value);
								ii++;
							}
						}
					}	
				}
			} else {
				String entity = context.getEditableDataset();
				String assoc = context.getEditableDatasetAssociation();
				if (!StringUtils.isEmpty(entity)) {
					transformSnippetParameter(context, PREFIX+BEANNAME, entity);
					int ii = 1;
					boolean associationFound = !StringUtils.isEmpty(assoc);
					for(String string : query.getMappings()) {
						String value = null;
						if (associationFound) {
							value = assoc+"."+string;
						} else {
							value = string;
						}
						transformSnippetParameter(context, PREFIX+BEANPROPERTY+ii, value);
						ii++;
					}
				}
			}
		}		
		
		if (query.getTabDisplay() != null) {
			IQueryTabDisplay display = query.getTabDisplay();
			transformSnippetParameter(context, "stabs", display.getSelection());
			//transformSnippetParameter(context, "Search.TabEnable", display.getEnabledTabsAsString());
		}
		
		transformSnippetParameter(context, PREFIX+SELMODE, query.getSelectionMode());
		transformSnippetParameter(context, PREFIX+RUNSTART, query.runAtStart()+"");
		if (!autocomplete) {
			transformSnippetParameter(context, PREFIX+"maxRowCount", query.getMaxRowCount()+"");
		}
	}
	
	/**
	 * @param parentWidgets
	 * @return widget
	 */
	private Widget getWidgetWithAssociation(List<?> parentWidgets) {
		for (int i = parentWidgets.size(); --i >= 0;) {
			Widget widget = (Widget) parentWidgets.get(i);
			if (widget.getTypeName().equals(WidgetTypeConstants.INCLUDE)) {
				String assoc = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ASSOCIATION);
				if (!StringUtils.isEmpty(assoc)) {
					return widget;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param context
	 * @param query
	 */
	private void transformAttributes(WidgetTransformerContext context, IQuery query) {
		String[] selectedAttributes = query.getAttributes();
		if (selectedAttributes != null && selectedAttributes.length > 0) {
			String dsname = query.getSearchModuleDomainEntity();
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(context.getProject());
			List<DatasetAttribute> attributes = SearchDomainObjectUtil.getDomainAttributes(dsname, query.getMappings(), ofsProject);
			int attSize = attributes.size();
			int excluded = 0;
			int included = selectedAttributes.length;
			if (attSize == included) {
				return;
			}
			if (attSize > 0) {
				excluded = attSize - included;
			}
			if (excluded > included || excluded == 0) {
				transformSnippetParameter(context, PREFIX+INCLUDE, query.getAttributesAsString());
			} else {
				List<String> attributeNames = getDomainAttributes(attributes);
				String excludedVal = getExcludedAttribuesAsString(attributeNames, createList(selectedAttributes));
				transformSnippetParameter(context, PREFIX+EXCLUDE, excludedVal);
			}
		}
	}
	
	/**
	 * @param complete
	 * @param included
	 * @return string
	 */
	private String getExcludedAttribuesAsString(List<String> complete, List<String> included) {
		List<String> excluded = new ArrayList<String>();
		for (String string : complete) {
			if (!included.contains(string)) {
				excluded.add(string);
			}
		}
		return StringUtils.join(excluded, ",");
	}
	
	/**
	 * @param attributes
	 * @return list
	 */
	private List<String> getDomainAttributes(List<DatasetAttribute> attributes) {
		List<String> names = new ArrayList<String>();
		for (DatasetAttribute datasetAttribute : attributes) {
			names.add(datasetAttribute.getDisplayName());
		}
		return names;
	}
	
	
	/**
	 * @param values
	 * @return list
	 */
	private List<String> createList(String[] values) {
		List<String> list = new ArrayList<String>();
		for (String string : values) {
			list.add(string);
		}
		return list;
	}	

}
