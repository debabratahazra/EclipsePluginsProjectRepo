package com.odcgroup.page.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.PageModelCore;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITable;

/**
 * Utility class with helper methods for Search Module functionality
 *
 * @author pkk
 * @author atr, code more robust (retrieve the root widget from an included model)
 */
public class SearchModuleUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchModuleUtils.class);
	
	/** */
	public static final String CONTAINER_SEARCH_MODULE = "container";
	/** */
	public static final String INPUT_SEARCH_MODULE = "input";
	/** */
	public static final String OUTPUT_SEARCH_MODULE = "output";
	/** */
	public static final String AUTOCOMPLETE_SEARCH_MODULE = "autoComplete";
	
	/**
	 * rule to check the containability for search modules
	 * 
	 * @param parent
	 * @param child 
	 * @return boolean
	 */
	public static boolean canContainChild(Widget parent, Widget child) {
		Widget root = parent.getRootWidget();
		if (root == null) {
			root = parent;
		}
		if (isModule(root)) {
			String value = getSearchMode(root);
			if (CONTAINER_SEARCH_MODULE.equals(value)) {
				if (parent.getTypeName().equals(WidgetTypeConstants.MODULE) 
						|| parent.getTypeName().equals(WidgetTypeConstants.BOX)
						|| parent.getTypeName().equals(WidgetTypeConstants.TAB)) {
					if (parent.getTypeName().equals(WidgetTypeConstants.TAB)) {
						root = parent;
					} else if (parent.getTypeName().equals(WidgetTypeConstants.BOX) ) {
						Widget tab = fetchParentTab(parent);
						if (tab != null) {
							root = tab;
						}		
					}
					return canContainerModuleContainChild(root, child);
				}
			} else if (INPUT_SEARCH_MODULE.equals(value)) {
				if (isModule(child)) {
					return false;
				}
				if (parent.getTypeName().equals(WidgetTypeConstants.MODULE) 
						|| parent.getTypeName().equals(WidgetTypeConstants.BOX)
						|| parent.getTypeName().equals(WidgetTypeConstants.TAB)) {
					return canInputModuleContainChild(root, child);
				}
			} else if (AUTOCOMPLETE_SEARCH_MODULE.equals(value)) {
				if (isModule(child) || isFragment(child)) {
					return false;
				}
			} else if (OUTPUT_SEARCH_MODULE.equals(value)) {
				if (isModule(child)) {
					return false;
				}
			} else {
				if (isOutputModule(child) || isInputModule(child)) {
					return false;
				}
				if(child.getTypeName().equals(WidgetTypeConstants.INCLUDE_XSP) && ("Stand-alone".equalsIgnoreCase(root.getPropertyValue(PropertyTypeConstants.MODULE_CONTAINMENT)))){
					return false;
				}
			}
		} else if (isFragment(root)) {
			if (child.getTypeName().equals(WidgetTypeConstants.FILTER_CRITERIA)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private static Widget fetchParentTab(Widget widget) {
		Widget parent  = widget.getParent();
		Widget tab = null;
		if (parent != null) {
			if (WidgetTypeConstants.TAB.equals(parent.getTypeName())) {
				tab = parent;
			} else {
				tab = fetchParentTab(parent);
			}
		}
		return tab;
	}
	

	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean moduleWithDomainBasedFragments(Widget widget) {
		if (isModule(widget)) {
			String value = getSearchMode(widget);
			if ((CONTAINER_SEARCH_MODULE.equals(value) 
					|| INPUT_SEARCH_MODULE.equals(value)) 
					&& hasDomainBasedFragments(widget)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean checkSupportedWidgetsForInput(Widget widget) {
		if (isModule(widget)) {
			String value = getSearchMode(widget);
			if (INPUT_SEARCH_MODULE.equals(value)
					&& hasInputNonSupportedWidgets(widget)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean checkSupportedWidgetsForContainer(Widget widget) {
		if (isModule(widget)) {
			String value = getSearchMode(widget);
			if (CONTAINER_SEARCH_MODULE.equals(value)
					&& hasContainerNonSupportedWidgets(widget)) {
				return false;
			}
		}
		return true;		
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean checkSearchWidgetsinNonSearchModule(Widget widget) {
		if (isModule(widget)) {
			String value = getSearchMode(widget);
			if ("none".equals(value) && (getIncludedInputs(widget) > 0 || getIncludedOutputs(widget) > 0)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean hasMoreInputModules(Widget widget) {
		if (collectSearchModules(widget, true).size()> 1) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * @param widget
	 * @return boolean
	 */
	public static boolean hasMoreOutputModules(Widget widget) {
		if (collectSearchModules(widget, false).size()> 1) {
			return false;
		}
		return true;	
	}
	
	/**
	 * @param root
	 * @param child
	 * @return boolean
	 */
	private static boolean canInputModuleContainChild(Widget root, Widget child ) {
		String childName = child.getTypeName();
		if (!childName.equals(WidgetTypeConstants.FRAGMENT)
				&& !childName.equals(WidgetTypeConstants.FILTER_CRITERIA)
				&& !childName.equals(WidgetTypeConstants.BOX)
				&& !childName.equals(WidgetTypeConstants.INCLUDE)) {
			return false;
		}
		if (childName.equals(WidgetTypeConstants.FRAGMENT)) {
			return isFragmentNotDomainBased(child);
		} else if ((childName.equals(WidgetTypeConstants.FILTER_CRITERIA)) 
				&& hasSearchCriteriaOrTabbedPane(root)) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param root
	 * @param child
	 * @return boolean
	 */
	private static boolean canContainerModuleContainChild(Widget root, Widget child ) {
		String childName = child.getTypeName();
		if (!childName.equals(WidgetTypeConstants.FRAGMENT)
			&& !childName.equals(WidgetTypeConstants.MODULE)
			&& !childName.equals(WidgetTypeConstants.TABBED_PANE)
			&& !childName.equals(WidgetTypeConstants.BOX)
			&& !childName.equals(WidgetTypeConstants.INCLUDE)) {
				return false;
		}
		if (childName.equals(WidgetTypeConstants.FRAGMENT)) {
			// allow only fragments that are not based on domain
			return isFragmentNotDomainBased(child);
		} else if (childName.equals(WidgetTypeConstants.MODULE)) {
			// allow only output & input search modules
			if (!isOutputModule(child) && !isInputModule(child)) {
				return false;
			}
			if (isOutputModule(child) && getIncludedOutputs(root) > 0) {
				//only one output module is allowed
				return false;
			} else if (isInputModule(child) && getIncludedInputs(root) > 0) {
				// only one input module is allowed
				return false;
			}
		} else if (childName.equals(WidgetTypeConstants.TABBED_PANE) 
				&& hasSearchCriteriaOrTabbedPane(root)) {
			// only one tabbed pane is allowed
			return false;
		}
		return true;
	
	}
	
	/**
	 * either a FilterCriteria or a TabbedPane is allowed
	 * 
	 * @param module
	 * @return boolean
	 */
	private static boolean hasSearchCriteriaOrTabbedPane(Widget module) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (widget.getTypeName().equals(WidgetTypeConstants.FILTER_CRITERIA)) {
					return true;
				}
				if (widget.getTypeName().equals(WidgetTypeConstants.TABBED_PANE)) {
					return true;
				}
				if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
					if (includedModel != null) {
						Widget inclRoot = includedModel.getWidget();
						if (inclRoot != null) {
							if (hasSearchCriteriaOrTabbedPane(inclRoot)) {						
								return true;
							}
						} else {
							URI uri = EcoreUtil.getURI(includedModel);
							logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
						}
					}
				}
			}
		}
		return false;	
	}
	
	/**
	 * @param module
	 * @return output
	 */
	public static Widget getOutputModule(Model module) {
		return getOutputModule(module, null);
	}
	
	/**
	 * @param module
	 * @param tabID
	 * @return output
	 */
	public static Widget getOutputModule(Model module, String tabID) {
		Widget result = null;
		if (module != null) {
			Widget widget = module.getWidget();
			if (widget == null) {
				URI uri = EcoreUtil.getURI(module);
				PageModelCore.getDefault().logError("Cannot load "+uri.trimFragment(), null); // //$NON-NLS-1$
			} else if (isModule(widget)) {
				String value = getSearchMode(widget);
				boolean notTabbed = StringUtils.isEmpty(tabID);
				if (CONTAINER_SEARCH_MODULE.equals(value))  {
					if (notTabbed) {
						List<Widget> outputs = getAllIncludedOutputs(widget);
						if (!outputs.isEmpty()) {
							result = outputs.get(0);
						}
					} else {
						result = getIncludedOutput(widget, tabID);					
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * @param module
	 * @param tabID
	 * @return input
	 */
	public static Widget getInputModule(Model module, String tabID) {
		Widget result = null;
		if (module != null) {
			Widget widget = module.getWidget();
			if (widget == null) {
				URI uri = EcoreUtil.getURI(module);
				PageModelCore.getDefault().logError("Cannot load "+uri.trimFragment(), null); // //$NON-NLS-1$
			} else if (isModule(widget)) {
				String value = getSearchMode(widget);
				boolean notTabbed = StringUtils.isEmpty(tabID);
				if (CONTAINER_SEARCH_MODULE.equals(value))  {
					if (notTabbed) {
						List<Widget> inputs = getAllIncludedInputs(widget);
						if (!inputs.isEmpty()) {
							result = inputs.get(0);
						}
					} else {				
						result = getIncludedInput(widget, tabID);
					}
				}
			}
		}
		return result;		
	
	}
	
	/**
	 * @param module
	 * @return widget
	 */
	public static Widget getInputModule(Model module) {
		return getInputModule(module, null);	
	}
	
	/**
	 * @param module
	 * @return table
	 */
	public static ITable getTableWidget(Widget module) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (widget.getTypeName().equals("TableTree")) {
					ITable table = TableHelper.getTable(widget);
					if (table != null) {
						return table;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param module
	 * @param tabID
	 * @return widget
	 */
	private static Widget getIncludedOutput(Widget module, String tabID) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (widget.getTypeName().equals(WidgetTypeConstants.TAB)) {
					String id = widget.getID();
					if (!StringUtils.isEmpty(id) && id.equals(tabID)) {
						List<Widget> widgets = getAllIncludedOutputs(widget);
						if (!widgets.isEmpty()) {
							return widgets.get(0);
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param module
	 * @param tabID
	 * @return widget
	 */
	private static Widget getIncludedInput(Widget module, String tabID) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (widget.getTypeName().equals(WidgetTypeConstants.TAB)) {
					String id = widget.getID();
					if (!StringUtils.isEmpty(id) && id.equals(tabID)) {
						List<Widget> widgets = getAllIncludedInputs(widget);
						if (!widgets.isEmpty()) {
							return widgets.get(0);
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param module
	 * @return list
	 */
	private static List<Widget> getAllIncludedOutputs(Widget module) {
		List<Widget> widgets = new ArrayList<Widget>();
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
					if (includedModel != null) {
						Widget inclRoot = includedModel.getWidget();
						if (inclRoot != null) {
							if (isModule(inclRoot) && isOutputModule(inclRoot)) {	
								widgets.add(inclRoot);
							}
						} else {
							URI uri = EcoreUtil.getURI(includedModel);
							logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
						}
					}
				}
			}
		}
		return widgets;	
	}
	
	/**
	 * @param module
	 * @return list
	 */
	private static List<Widget> getAllIncludedInputs(Widget module) {
		List<Widget> widgets = new ArrayList<Widget>();
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
					if (includedModel != null) {
						Widget inclRoot = includedModel.getWidget();
						if (inclRoot != null) {
							if (isModule(inclRoot) && isInputModule(inclRoot)) {
								widgets.add(inclRoot);
							}
						} else {
							URI uri = EcoreUtil.getURI(includedModel);
							logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
						}
					}
				} 
			}
		}
		return widgets;		
	}
	
	/**
	 * @param widget
	 * @param input 
	 * @return list
	 */
	private static List<Widget> collectSearchModules(Widget widget, boolean input) {
		List<Widget> inputs = new ArrayList<Widget>();
		if ((isModule(widget) && getSearchMode(widget).equals(CONTAINER_SEARCH_MODULE))
				|| (widget.getTypeName().equals(WidgetTypeConstants.TAB) && isModule(widget.getRootWidget()))) {
			List<Widget> contents = widget.getContents();
			for (Widget child : contents) {		
				if (input)
					collectInputModules(child, inputs);		
				else
					collectOutputModules(child, inputs);
			}
		}
		return inputs;
	}
	
	/**
	 * @param widget
	 * @param widgets
	 */
	private static void collectOutputModules(Widget widget, List<Widget> widgets) {
		if (WidgetTypeConstants.BOX.equals(widget.getTypeName())) {
			List<Widget> contents = widget.getContents();
			for (Widget child : contents) {		
				if (WidgetTypeConstants.BOX.equals(child.getTypeName())) {
					collectOutputModules(child, widgets);
				} else if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
					if (includedModel != null) {
						Widget inclRoot = includedModel.getWidget();
						if (inclRoot != null) {
							if (isModule(inclRoot) && isOutputModule(inclRoot)) {
								widgets.add(inclRoot);						
							}
						} else {
							URI uri = EcoreUtil.getURI(includedModel);
							logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param widget
	 * @param widgets
	 */
	private static void collectInputModules(Widget widget, List<Widget> widgets) {
		if (WidgetTypeConstants.BOX.equals(widget.getTypeName())) {
			List<Widget> contents = widget.getContents();
			for (Widget child : contents) {		
				if (WidgetTypeConstants.BOX.equals(child.getTypeName())) {
					collectInputModules(child, widgets);
				} else if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
					if (includedModel != null) {
						Widget inclRoot = includedModel.getWidget();
						if (inclRoot != null) {
							if (isModule(inclRoot) && isInputModule(inclRoot)) {
								widgets.add(inclRoot);						
							}
						} else {
							URI uri = EcoreUtil.getURI(includedModel);
							logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param module
	 * @return int
	 */
	private static int getIncludedOutputs(Widget module) {
		List<Widget> list = getAllIncludedOutputs(module);
		return list.size();
	}
	
	/**
	 * @param module
	 * @return int
	 */
	private static int getIncludedInputs(Widget module) {
		List<Widget> list = getAllIncludedInputs(module);
		return list.size();
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isModule(Widget widget) {
		return widget.getType().getName().equals(WidgetTypeConstants.MODULE);
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isFragment(Widget widget) {
		return widget.getType().getName().equals(WidgetTypeConstants.FRAGMENT);		
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isInclude(Widget widget) {
		return widget.getType().getName().equals(WidgetTypeConstants.INCLUDE);
	}
	
	/**
	 * @param widget
	 * @return model
	 */
	private static Model getIncludedModel(Widget widget) {
    	Property include = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
    	return include != null ? include.getModel() : null;
	}
	
	/**
	 * @param module
	 * @return boolean
	 */
	private static boolean isOutputModule(Widget module) {
		return OUTPUT_SEARCH_MODULE.equals(getSearchMode(module));
	}
	
	/**
	 * @param module
	 * @return boolean
	 */
	private static boolean isInputModule(Widget module) {
		return INPUT_SEARCH_MODULE.equals(getSearchMode(module));
	}

	
	/**
	 * @param module
	 * @return mode
	 */
	private static String getSearchMode(Widget module) {
		String value = module.getPropertyValue(PropertyTypeConstants.SEARCH);
		if (value == null) {
			value = "none";
		}
		return value;
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isFragmentNotDomainBased(Widget widget) {
		String value = widget.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		return StringUtils.isEmpty(value);
	}
	
	/**
	 * @param module
	 * @return boolean
	 */
	private static boolean hasDomainBasedFragments(Widget module) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
					if (includedModel != null) {
						Widget inclRoot = includedModel.getWidget();
						if (inclRoot != null) {
							if (isFragment(inclRoot) && !isFragmentNotDomainBased(inclRoot)) {						
								return true;
							}
						} else {
							URI uri = EcoreUtil.getURI(includedModel);
							logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param module
	 * @return boolean
	 */
	private static boolean hasInputNonSupportedWidgets(Widget module) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				String type = widget.getTypeName();
				if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
	            	if (includedModel == null) {
	            		return true;
	            	}
	            	Widget inclRoot = includedModel.getWidget();
	            	if (inclRoot != null) {
		            	if (!isNotDomainBasedFragment(inclRoot)) {
		            		return true;
		            	}
	            	} else {
						URI uri = EcoreUtil.getURI(includedModel);
						logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
	            	}
				} else if (!(type.equals(WidgetTypeConstants.BOX) 
						|| type.equals(WidgetTypeConstants.FILTER_CRITERIA))) {
					return true;
				}
			}
		}
		return false;		
	}
	
	/**
	 * @param module
	 * @return boolean
	 */
	private static boolean hasContainerNonSupportedWidgets(Widget module) {
		Iterator<EObject> contents = module.eAllContents();
		while (contents.hasNext()) {
			EObject eObject = (EObject) contents.next();
			if (eObject instanceof Widget) {
				Widget widget = (Widget) eObject;
				String type = widget.getTypeName();
				if (isInclude(widget)) {
					Model includedModel = getIncludedModel(widget);
	            	if (includedModel == null) {
	            		return true;
	            	}
	            	Widget inclRoot = includedModel.getWidget();
	            	if (inclRoot != null) {
		            	if (!isNotDomainBasedFragment(inclRoot) 
								&& !isInputModule(inclRoot) 
								&& !isOutputModule(inclRoot)) {
							return true;
						}
	            	} else {
						URI uri = EcoreUtil.getURI(includedModel);
						logger.error("The included model '{}' cannot be loaded.", uri.trimFragment().toString());
	            	}
				} else if(!(type.equals(WidgetTypeConstants.BOX) 
							|| type.equals(WidgetTypeConstants.TABBED_PANE)
							|| type.equals(WidgetTypeConstants.TAB))) {
					return true;
				}
			}
		}
		return false;		
	
	}
	
	/**
	 * @param widget
	 * @return boolean
	 */
	private static boolean isNotDomainBasedFragment(Widget widget) {
		if (isFragment(widget) && isFragmentNotDomainBased(widget)) {						
			return true;
		}
		return false;
	}

}
