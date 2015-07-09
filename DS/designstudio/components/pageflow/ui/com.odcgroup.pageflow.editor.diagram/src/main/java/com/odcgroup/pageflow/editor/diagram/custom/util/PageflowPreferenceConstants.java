package com.odcgroup.pageflow.editor.diagram.custom.util;

/**
 * PageflowEditor Preference Constants
 * @author pkk
 *
 */
public interface PageflowPreferenceConstants {	

	
	/**
	 * Transition Line Style Constant
	 */
	public static String PREF_TRN_LINE_STYLE = "pageflow.Connectors.lineStyle";
	
	/**
	 * Transition Routing Style Constant
	 */
	public static String PREF_TRN_ROUTE_SHORTEST = "pageflow.Connectors.route.shortest";
	
	/**
	 * Transition Routing Smoothing Factor Constant
	 */
	public static String PREF_TRN_ROUTE_SMOOTH_FACTOR = "pageflow.Connectors.smooth.factor";
	
	/**
	 * Transition Highlighted Color Constant
	 */
	public static String PREF_TRN_LINE_HIGHLIGHT_COLOR = "pageflow.Connectors.line.hightlight.color";
	
	/**
	 * Transition Line Fill Color Constant
	 */
	public static String PREF_TRN_LINE_FILL_COLOR = "pageflow.Connectors.line.fill.color";
	
	/**
	 * ViewState Fill Color Constant
	 */
	public static String PREF_VIEW_STATE_FILL_COLOR = "pageflow.viewstate.fill.color";
	
	/**
	 * SubPageflowState Fill Color Constant
	 */
	public static String PREF_SUBPAGEFLOW_STATE_FILL_COLOR = "pageflow.subpageflowstate.fill.color";
	
	/**
	 * PageflowShapes Line Color Constant
	 */
	public static String PREF_PAGEFLOW_SHAPE_LINE_COLOR = "pageflow.shapes.line.color";
	
	/**
	 * Transition Label Action Content Constant
	 */
	public static String PREF_TRN_LABEL_ACTION_CONTENT = "pageflow.Connectors.label.action.content";
	
	/**
	 * ErrorURL Constant
	 */
	public static String PREF_PAGEFLOW_ERROR_URL = "pageflow.error.url";
	/**
	 * AbortClass Constant
	 */
	public static String PREF_PAGEFLOW_ABORT_CLASS = "pageflow.abort.class";
	
	/**
	 * ErrorURL Default Value Constant
	 */
	public static String DEF_PAGEFLOW_ERROR_URL = "/page/common/OneModule?module=general/PageflowErrorModule";
	/**
	 * AbortClass Default Value Constant 
	 */
	public static String DEF_PAGEFLOW_ABORT_CLASS = "class:com.odcgroup.uif.workflow.ConfigurableURLRedirector";
	
	public static String PREF_DESC_LABEL_DISPLAY = "pageflow.desc.label.display";
	
	public static String PREF_SHOWID_LABEL = "pageflow.showid.label";
	
	public static String PREF_STATE_SIZE = "pageflow.states.size";

}
