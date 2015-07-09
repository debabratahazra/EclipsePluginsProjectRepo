package com.odcgroup.page.transformmodel;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;

/**
 * Constants related to transformations.
 * 
 * @author Gary Hayes
 */
public interface TransformerConstants {
	
	/** The prefix used when defining namespaces. */
	public static final String NAMESPACE_PREFIX = "xmlns:";
	
	/** The name of the Attribute language. */
	public static final String LANGUAGE_NAME = "language";
	
	/** The value of the Attribute language. */
	public static final String LANGUAGE_VALUE = "java";	
	
	/** The name of the action tag .*/
	public static final String ACTION_NAME = "action";
	
	/** The name of the submit tag .*/
	public static final String SUBMIT_NAME = "submit";
	
	/** The NAV namespace. */
	public static final String NAV_NAMESPACE_URI = "http://www.odcgroup.com/uif/nav/0.1";		
	
	/** The element name of the attribute tag. */
	public static final String ATTRIBUTE_ELEMENT_NAME = "attribute";
		
	/** The element name of the onevent tag .*/
	public static final String ONEVENT_NAME = "onevent";

	/** The element name of the logic tag. */
	public static final String LOGIC_ELEMENT_NAME = "logic";
	
	/** The element name of the expression tag. */
	public static final String EXPRESSION_ELEMENT_NAME = "expr";
	
	/** The attribute name of the name attribute. */
	public static final String NAME_ATTRIBUTE_NAME = "name";	
				
	/** The namespace of the CInclude Library. */
	public static final String C_INCLUDE_NAMESPACE_URI = "http://apache.org/cocoon/include/1.0";
	
	/** The attribute name for the CInclude tag. */
	public static final String C_INCLUDE_ATTRIBUTE_NAME = "src";	
			
	/** The namespace of the XInclude Library. */
	public static final String X_INCLUDE_NAMESPACE_URI = "http://www.w3.org/2003/XInclude";

	/** The attribute name for the XInclude tag. */
	public static final String X_INCLUDE_ATTRIBUTE_NAME = "href";
	
	/** The name of the Include Element (used for both CInclude and XInclude). */
	public static final String INCLUDE_ELEMENT_NAME = "include";	
	
	/** The name of the external include */
	public static final String EXTERNAL_INCLUDE_ELEMENT_NAME = "external";
	
	/** The attribute name of the get tag used in submit element. */
	public static final String GET = "get";

	/** The element name of the onevent tag. */
	public static final String ONEVENT_ELEMENT_NAME = "onevent";
	
	/** The attribute name of the type tag used in the onevent element. */
	public static final String TYPE_ATTRIBUTE_NAME = "type";
	
	/** The attribute name of the method tag used in the submit element. */
	public static final String METHOD_ATTRIBUTE_NAME = "method";
	
	/** The attribute name of the call-URI tag used in the submit element. */
	public static final String CALL_URI_ATTRIBUTE_NAME = "call-URI";
	
	/** The attribute click constant. */
	public static final String CLICK = "click";
	
	/** The String containing the platform resource protocol. */
	public static final String PLATFORM_RESOURCE_PROTOCOL = "platform:/resource/";	
	
	/** The String used as the cocoon protocol. */
	public static final String COCOON_PROTOCOL = "cocoon://";	
	
	/** The file extension of a page fragment. */
	public static final String PAGE_UI_FILE_EXTENSION = PageConstants.FRAGMENT_FILE_EXTENSION;	
	
	/** The name of the XGui Widget Library. */
	public static final String XGUI_WIDGET_LIBRARY_NAME = WidgetLibraryConstants.XGUI;
	
	/** The name of the Include Widget. */
	public static final String INCLUDE_WIDGET_NAME = WidgetTypeConstants.INCLUDE;
	
	/** The name of the property containing the source path. */
	public static final String INCLUDE_SRC_PROPERTY_NAME = PropertyTypeConstants.INCLUDE_SOURCE;
	
	/** The name of the property containing the xsp inclusion. */
	public static final String INCLUDE_XSP_PROPERTY_NAME = PropertyTypeConstants.INCLUDE_XSP;
	
	/** The name of the property containing the type of inclusion. */
	public static final String INCLUDE_TYPE_PROPERTY_NAME = PropertyTypeConstants.INCLUDE_TYPE;	
	
	/** The name of the Code Widget. */
	public static final String CODE_WIDGET_NAME = WidgetTypeConstants.CODE;

	/** The Property containing the code. */
	public static final String CODE_PROPERTY_NAME = PropertyTypeConstants.CODE;	
	
	/** The Property containing the horizontal alignment. */
	public static final String HORIZONTAL_ALIGNMENT_PROPERTY_NAME = PropertyTypeConstants.HORIZONTAL_ALIGNMENT;

	/** The Property containing the vertical alignment. */
	public static final String VERTICAL_ALIGNMENT_PROPERTY_NAME = PropertyTypeConstants.VERTICAL_ALIGNMENT;

	/** The Property containing the horizontal text position. */
	public static final String HORIZONTAL_TEXT_POSITION_PROPERTY_NAME = PropertyTypeConstants.HORIZONTAL_TEXT_POSITION;

	/** The Property containing the vertical text position. */
	public static final String VERTICAL_TEXT_POSITION_PROPERTY_NAME = PropertyTypeConstants.VERTICAL_TEXT_POSITION;

	/** The name of the Box Widget. */
	public static final String BOX_WIDGET_NAME = WidgetTypeConstants.BOX;

	/** The Property containing the type of box. */
	public static final String BOX_TYPE_PROPERTY_NAME = PropertyTypeConstants.BOX_TYPE;	
	
	/** The value for a absolute box type. */
	public static final String ABSOLUTE_BOX_TYPE_VALUE = "absolute";
	
	/** The value for a horizontal box type. */
	public static final String HORIZONTAL_BOX_TYPE_VALUE = "horizontal";
	
	/** The value for a vertical box type. */
	public static final String VERTICAL_BOX_TYPE_VALUE = "vertical";
	
	/** The value for a User Parameter */
	public static final String EVENT_USER_PARAMETER = "param";
	
	/** The value for a setWidgetProperty element */	
	public static final String SETWIDGETPROPERTY_ELEMENT_NAME = "setWidgetProperty";
	
	/** The namespace for tab logicsheet */
	public static final String TAB_NAMESPACE_URI = "http://www.odcgroup.com/uif/tab/0.1";
	
	/** The element name of the tab-view tag */
	public static final String TABVIEW_ELEMENT_NAME = "tab-view";
	
	/** The element name of the tab-content tag */
	public static final String TABCONTENT_ELEMENT_NAME = "tab-content";
	
	/** The element name of the is-selected tag */
	public static final String ISSELECTED_ELEMENT_NAME = "is-selected";
	
	/** The id attribute of the tab-view element */
	public static final String TABVIEW_ID_ATTRIBUTE = "id";

	/** The default-selections attribute of the tab-view element */
	public static final String TABVIEW_DEFAULTSELECTION_ATTRIBUTE = "default-selected";
	
	// Added for DS-6543 -- Begin
	/** The attribute name in the xgui:autocomplete tag */
	public static final String RECENT = "recent";
	
	/** The property name of 'Provide access to history items' in general tab */
	public static final String ACCESS_HISTROY_ITEMS = "accessHistoryItems";
	// Added for DS-6543 -- End
	/** Property name of the favorite Search Field widget "Provide access to favorite items" */
	public static final String FAVORITE = "favorite";		 
}
