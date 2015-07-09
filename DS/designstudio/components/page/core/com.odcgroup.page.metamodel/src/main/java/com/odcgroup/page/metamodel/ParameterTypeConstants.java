package com.odcgroup.page.metamodel;

/**
 * Represents ParameterType names which MUST NOT be changed since they are used
 * in the application.
 *
 * @author pkk
 *
 */
public interface ParameterTypeConstants {
	
	/** The method parameter type */
	public String METHOD = "method";
	
	/** The Call-URI parameter type */
	public String CALL_URI = "call-URI";
	
	/** The target parameter type */
	public String TARGET = "target";
	
	/** Default value for target combo */
	public String TARGET_DEFAULT_VALUE = "self";
	
	/** Default Value for target for values that are removed */
	public String TARGET_REDUCED_DEFAULT = "self";
	
	/** The value for Call-URI parameter for a simplified event with transitionID */
	public static String SIMPLIFIEDEVENT_TRANSITION_CALLURI_VALUE = "<pageflow:continuation/>";
	
	/** The parameter name for a simplified event with transitionID */
	public static String FLOW_ACTION_PARAMETER = "flow-action";
	
	/** The parameter name for asynchronous */
	public static String ASYNCHRONOUS_PARAMETER = "asynchronous";

	/** The widget-group-ref parameter type */
	public String WIDGET_GROUP_REF = "widget-group-ref";

	/** Default value for widget-group-ref text field */
	public String WIDGET_GROUP_REF_DEFAULT_VALUE = "*";
	
	/** Only Changed parameter type  */
	public String ONLY_CHANGED = "only-changed";
	/** value for widget-group-ref if forms are organized in Tabs  */
	public String WIDGET_GROUP_REF_FORM_VALUE = "form";
	
	
	public String DELAY = "delay";
	public String HEIGHT = "height";
	public String LEFT = "left";
	public String MODAL = "modal";
	public String TOP = "top";
	public String WIDTH = "width";
	public String URI = "uri";
	
	
}
