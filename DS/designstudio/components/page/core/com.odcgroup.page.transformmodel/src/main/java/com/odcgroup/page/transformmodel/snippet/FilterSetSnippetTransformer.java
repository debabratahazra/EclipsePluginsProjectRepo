package com.odcgroup.page.transformmodel.snippet;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.transformmodel.TabbedPaneTransformerUtil;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;

/**
 * FilterSet snippet transformer
 * 
 * @author pkk
 *
 */
public class FilterSetSnippetTransformer extends BaseSnippetTransformer {
	
	/** */
	private static final String LEVEL = SnippetTransformerConstants.FILTERSET_LEVEL;
	/** */
	@SuppressWarnings("unused")
	private static final String LOGICAL_OP = SnippetTransformerConstants.FILTERSET_LOGICAL_OPERATOR;
	/** */
	private static final String TARGET_DS = SnippetTransformerConstants.FILTERSET_TARGETDS;
	/** */	
	private String prefix = PARAM_PREFIX+".";
	/** */
	private WidgetTransformerContext context = null;
	

	/**
	 * @param functionElement
	 */
	public FilterSetSnippetTransformer(Element functionElement) {
		super(functionElement);
	}

	
	/**
	 * @param context
	 * @param filterset
	 */
	public void transform(WidgetTransformerContext context, IFilterSet filterset, Widget eventWidget) {
		
		if (filterset == null) {
			return;
		}
		this.context = context;
		
		String domainEntity = null;
		EObject eObj = filterset.getSnippet().eContainer();
		if (eObj instanceof Event) {
			IQuery query = SnippetUtil.getQuery((Event) eObj);
			if (isDynamicTab(eventWidget)) {
				domainEntity =  filterset.getTargetDatasetName();
			} else {
				domainEntity = query != null ? query.getSearchModuleDomainEntity() : filterset.getTargetDatasetName();	
			}
		}
		
		if (StringUtils.isEmpty(domainEntity)) {
			return;
		}
		
		prefix = prefix + filterset.getId()+".";
		
		// domain entity
		transformSnippetParameter(TARGET_DS, domainEntity);
		
		
		// level
		int level = filterset.getLevel();
		if (level > -1) {
			transformSnippetParameter(LEVEL, level+"");
		}
		
		//cancel - DS-5637
		if(filterset.isCancelled()){
			transformSnippetParameter("cancel", "true");
		}
		else if(!filterset.isCancelled()){
			transformSnippetParameter("cancel", "false");
		}
		
		
		// transform filters
		transformFilters(context, filterset.getFilters(), eventWidget);
		
	}
	
	/**
	 * @param widget
	 */
	private boolean isDynamicTab(Widget widget) {
		if ("Tab".equals(widget.getTypeName())) {
			Widget parent = widget.getParent();
			if (TabbedPaneTransformerUtil.isDynamicTabbedPane(parent)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param suffix
	 * @param value
	 */
	private void transformSnippetParameter(String suffix, String value) {
		String key = prefix+suffix;
		transformSnippetParameter(context, key, value);
	}
	
	/**
	 * @param context
	 * @param filters
	 */
	private void transformFilters(WidgetTransformerContext context, List<IFilter> filters, Widget widget) {
		FilterSnippetTransformer transformer = new FilterSnippetTransformer(getFunctioneElement());
		for (IFilter filter : filters) {
			transformer.transform(context, filter, widget);
		}
	}

}
