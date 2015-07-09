package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.XSPConstants.XSP_LOGIC;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;

/**
 * This class provide methods for the transformation of the
 * table footer.
 * 
 * @author Alexandre Jaquet
 *
 */
public class TableFooterTransformer extends BaseWidgetTransformer {
	
	/** The text related to paging.  This includes the buttons and actions. */
	private static final String pageSizeText = "if(<udp:page-count/> > 1)\n" +
		"{\n" +
			" <xsp:content>\n" +
				"  <!-- Pagination footer: Appears only if the result has more than 1 page.-->\n"+
				"  <xgui:button halign=\"trail\">\n" +
					"   <xsp:logic>\n" +
						"    if (<udp:current-page/> == <udp:first-page/>)\n"+
							"   <xsp:attribute name=\"enabled\">false</xsp:attribute>\n" +
							"   <xsp:attribute name=\"action\">get('<pageflow:continuation/>',	'', '','<scope:get-module-rank/>p=<udp:first-page/>,flow-action=reload')</xsp:attribute>\n"+	
					"   </xsp:logic>\n"+
					"<xgui:text><i18n:text>general.nav.first</i18n:text></xgui:text>\n"+
				"</xgui:button>\n"+
				"<xgui:button halign=\"trail\">" +
					"<xsp:logic>\n"+
						"if (<udp:has-previous-page/> == false)\n"+
							"<xsp:attribute name=\"enabled\">false</xsp:attribute>\n"+
							"<xsp:attribute name=\"action\">get('<pageflow:continuation/>',	'', '','<scope:get-module-rank/>p=<udp:previous-page/>,flow-action=reload')</xsp:attribute>\n"+							
					"</xsp:logic>\n"+
					"<xgui:text><i18n:text>general.nav.prev</i18n:text></xgui:text>\n"+
				"</xgui:button>\n" +
				"<xgui:button halign=\"trail\">\n" +
					"<xsp:logic>\n" +
						"if (<udp:has-next-page/> == false)\n" +
							"<xsp:attribute name=\"enabled\">false</xsp:attribute>\n"+
							"<xsp:attribute name=\"action\">get('<pageflow:continuation/>',	'', '','<scope:get-module-rank/>p=<udp:next-page/>,flow-action=reload')</xsp:attribute>\n"+	
					"</xsp:logic>\n"+
					"<xgui:text><i18n:text>general.nav.next</i18n:text></xgui:text>\n"+
				"</xgui:button>\n"+
				"<xgui:button halign=\"trail\">\n" +
					"<xsp:logic>\n" +
						"if (<udp:current-page/> == <udp:last-page/>)\n"+
							"<xsp:attribute name=\"enabled\">false</xsp:attribute>\n"+
							"<xsp:attribute name=\"action\">get('<pageflow:continuation/>',	'', '','<scope:get-module-rank/>p=<udp:last-page/>,flow-action=reload')</xsp:attribute>\n"+	
					"</xsp:logic>\n"+
					"<xgui:text><i18n:text>general.nav.last</i18n:text></xgui:text>\n"+
				"</xgui:button>\n"+
			"</xsp:content>\n"+
		"}\n";

	
	/**
	 * Constructor
	 * 
	 * @param type 
	 * 			The WidgetType
	 */
	public TableFooterTransformer (WidgetType type) {
		super(type);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		// do nothing
	}

	/**
	 * Builds the main xgui:hbox and builds it's children.
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @param parent
	 * 			The parent element
	 */
	public void buildXguiHbox(WidgetTransformerContext context, Widget widget,Element parent) {
		Element xspLogic = appendElement(context, parent, XSP_NAMESPACE_URI, XSP_LOGIC);
		xspLogic.setTextContent("if(<udp:row-count/> &gt; 0) { ");
		Element hbox = appendElement(context, parent,XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_HBOX_ELEMENT_NAME);
		appendElement(context,hbox, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_GLUE_ELEMENT_NAME);
		buildsLabel(context, widget, hbox);
		Element xspLogicClosing = appendElement(context,parent, XSP_NAMESPACE_URI, XSP_LOGIC);
		xspLogicClosing.setTextContent("}");

	}

	/**
	 * Builds the label element of the footer.
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @param parent
	 * 			The parent element
	 */
	private void buildsLabel(WidgetTransformerContext context, Widget widget, Element parent){
		Element label = appendElement(context,parent, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_LABEL_ELEMENT);
		Element text = appendElement(context, label, XGuiConstants.XGUI_NAMESPACE_URI, XGuiConstants.XGUI_TEXT_ELEMENT);
		text.setTextContent("<i18n:text>general.results</i18n:text>&#160;<xsp:expr><udp:first-visible-index/>+1</xsp:expr>&#160;<i18n:text>general.to</i18n:text>&#160;<xsp:expr><udp:last-visible-index/>+1</xsp:expr>&#160;<i18n:text>general.of</i18n:text>&#160;<udp:filtered-row-count/>&#160;");
		buildPagingLogic(context, widget, parent);
	}

	/**  
	 * Builds the xsp:logic section and it's children.
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @param parent
	 * 			The parent element 
	 */
	private void buildPagingLogic(WidgetTransformerContext context, Widget widget, Element parent) {
		Element xspLogic = appendElement(context,parent, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
		xspLogic.setTextContent(getPageSizeText());
	}
	
	/**
	 * Gets the text related to paging. This includes the buttons and actions.
	 * 
	 * @return String
	 */
	private String getPageSizeText() {
		return pageSizeText;
	}
	
	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		throw new RuntimeException("It should not be possible to reach here");
	}	
}
