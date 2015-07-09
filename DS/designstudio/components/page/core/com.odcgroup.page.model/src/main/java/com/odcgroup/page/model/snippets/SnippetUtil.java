package com.odcgroup.page.model.snippets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.PageModelCore;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.impl.SnippetFactory;

/**
 *
 * @author pkk
 *
 */
public class SnippetUtil {
	
	/** */
	private static ISnippetFactory snippetfactory = new SnippetFactory();	
	/** */
	private static final String DATATYPE_SELECTIONMODE_NAME = "SelectionMode"; // $NON-NLS-1$
	
	/**
	 * @return snippetfactory
	 */
	public static ISnippetFactory getSnippetFactory() {
		return snippetfactory;
	}	
	
	/**
	 * @param event
	 * @return query
	 */
	public static IQuery getQuery(Event event) {
		IQuery query = null;
		if (event != null) {
			query = getSnippetFactory().adaptQuerySnippet(event.getSnippets());
		}
		return query;
	}
	
	/**
	 * Find the DataType given its  name
	 * @param name
	 * @return DataType
	 */
	private static DataType findDataType(String name) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (DataType dataType : metamodel.getDataTypes().getTypes()) {
			if (name.equals(dataType.getName())) {
				return dataType;
			}
		}
		return null;
	}
	
	/**
	 * @return dataType
	 */
	public static DataType getSelectionModes() {
		return findDataType(DATATYPE_SELECTIONMODE_NAME);
	}
	
	/**
	 * @param module
	 * @return string[]
	 */
	public static String[] fetchTabs(Model module) {
		if (module == null) {
			return null;
		}
		Map<String, String> tabs = parseModuleForTabs(module);
		return tabs.keySet().toArray(new String[0]);
	}
	
	/**
	 * @param event
	 * @return list
	 */
	public static List<IFilterSet> getFilterSets(Event event) {
		return getSnippetFactory().adaptFilterSets(event.getSnippets());
	}
	
	/**
	 * @param module
	 * @return map
	 */
	public static Map<String, String> parseModuleForTabs(Model module) {		
		Map<String, String> tabs = new HashMap<String, String>();
		
		if (module != null) {
			if (module.getWidget() == null) {
				// invalid module reference
				URI uri = EcoreUtil.getURI(module);
				PageModelCore.getDefault().logError("Cannot load "+uri.trimFragment(), null); // //$NON-NLS-1$
			}
			Widget boxWidget = module.getWidget().getContents().get(0);
			MetaModel metamodel = MetaModelRegistry.getMetaModel();
			WidgetType type = metamodel.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.TABBED_PANE);
			for (Widget tabbedPane : boxWidget.getWidgets(type, false)) {
				for (Widget widget : tabbedPane.getContents()) {
					if (widget.getTypeName().equals(WidgetTypeConstants.TAB)) {
						String value = widget.getPropertyValue(PropertyTypeConstants.TAB_NAME);
						String id = widget.getID();
						if (StringUtils.isEmpty(value)) {
							value = id;
						}
						tabs.put(id, value);
					}
				}
			}
		}
		
		return tabs;
	}

}
