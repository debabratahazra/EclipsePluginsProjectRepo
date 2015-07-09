package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.TransformerConstants.ATTRIBUTE_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.TransformerConstants.EXPRESSION_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.TransformerConstants.ISSELECTED_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.TransformerConstants.NAME_ATTRIBUTE_NAME;
import static com.odcgroup.page.transformmodel.TransformerConstants.TABCONTENT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.TransformerConstants.TABVIEW_ID_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.TransformerConstants.TAB_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_LOGIC;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * Transformer for the tab widget. See TabbedPaneWidgetTransformer for further information.
 * 
 * @author Phani Kumar Kotaprolu
 * @author atr, added dynamic tabs (DS-3437)
 */
public class TabWidgetTransformer extends BaseWidgetTransformer{

	/** The tab constant .*/
	private static final String XGUI_TAB = "tab";
	
	/** The selected constant .*/
	private static final String SELECTED = PropertyTypeConstants.SELECTED;


	/**
	 * Constructor
	 * 
	 * @param type
	 * 			The widget type
	 */
	public TabWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {

		boolean standalone = TabbedPaneTransformerUtil.isParentStandalone(context);
		// if widget is part of a module, check for containment
		if (TabbedPaneTransformerUtil.isModule(widget.getRootWidget())) {
			standalone = TabbedPaneTransformerUtil.isModuleStandalone(widget.getRootWidget());
		} else {
			// if widget is part of a fragment, 
			// check if it is included in a module and the containment
			standalone = TabbedPaneTransformerUtil.isParentStandalone(context);
		}
		
		if (!standalone && !StringUtils.isEmpty(widget.getID())) {
			
			Element element = appendElement(context, XGUI_NAMESPACE_URI, XGUI_TAB);
			element.setAttribute(XGuiConstants.XGUI_ID, widget.getID());
			if (!TabbedPaneTransformerUtil.isTabEnabled(widget)) {
				// DS-3900
				element.setAttribute(XGuiConstants.XGUI_ENABLED, "false");
			}
		
			// Set the parent so that the Attributes are set on the correct element
			Element oldParent = context.setParentElement(element);
			
			transformTranslations(context, widget);
			
			
			
			appendAttribute(context, widget); 
			appendAction(context, widget, standalone);
			appendTabContentTags(context, widget, standalone);
			context.setParentElement(oldParent);
			
			
			/**
			 * DS-4849 : Providing "method="post"" for Tabbed pane.
			 */
			NodeList list = (element.getChildNodes());
			if (list != null && list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeName().equals("xgui:onevent")) {
						Element onEvent = (Element)node;
						NodeList tempList = onEvent.getChildNodes();
						for (int j = 0; j < tempList.getLength(); j++) {
							Node tempNode = tempList.item(j);
							if(tempNode.getNodeName().equals("xgui:submit")) {
								Element onSubmit = (Element)tempNode;
								onSubmit.setAttribute("method", "post");
							}
						}
					}
				}
			}
			
			
		} else if (standalone && !StringUtils.isEmpty(widget.getID())) {
			Element element = appendElement(context, XGUI_NAMESPACE_URI, XGUI_TAB);
			element.setAttribute(XGuiConstants.XGUI_ID, widget.getID());
			if (!TabbedPaneTransformerUtil.isTabEnabled(widget)) {
				element.setAttribute(XGuiConstants.XGUI_ENABLED, "false");
			}
			
			// Set the parent so that the Attributes are set on the correct element
			Element oldParent = context.setParentElement(element);

			transformTranslations(context, widget);

			appendAttribute(context, widget); 
			appendAction(context, widget, standalone);
			appendTabContentTags(context, widget, standalone);
			appendBoxedTabContents(context, widget);
			context.setParentElement(oldParent);
			
			
		}
		
	}
	
	/**
	 * Append the logic tag and it's content for each tab.
	 * 
	 * @param context 
	 * 			The WidgetTransformerContext 
	 * @param widget
	 * 			The widget
	 * @param standalone 
	 * @exception CoreException
	 */
	private void appendTabContentTags(WidgetTransformerContext context, Widget widget, boolean standalone) throws CoreException {
		Element tabContent = createElement(context, TAB_NAMESPACE_URI, TABCONTENT_ELEMENT_NAME);
		
		tabContent.setAttribute(TABVIEW_ID_ATTRIBUTE, widget.getID());		
		TransformUtils.appendChild((Element) context.getParentElement().getParentNode().getParentNode(), tabContent);
		if (!standalone)
			transformChildren(context, widget, tabContent);
	}
	
	/**
	 * append the vbox and the contents of tab in it
	 * 
	 * @param context
	 * @param widget
	 * @exception CoreException 
	 */
	private void appendBoxedTabContents(WidgetTransformerContext context, Widget widget) throws CoreException {
		Element vBox = createElement(context, XGUI_NAMESPACE_URI, XGuiConstants.XGUI_VBOX_ELEMENT_NAME);
		vBox.setAttribute("id", "vbox_"+widget.getID());
		// This needs to be outside the tabbed-pane!
		TransformUtils.appendChild((Element) context.getParentElement().getParentNode().getParentNode(), vBox);
		appendHiddenAttribute(context, vBox, widget);
		transformChildren(context, widget, vBox);
	}
	
	/**
	 * Creates and appends an xsp:attribute element.
	 * 
	 * @param context
	 * 			The widget transformer context
	 * @param widget
	 * 			The widget
	 */
	private void appendAttribute(WidgetTransformerContext context, Widget widget) {		
		
		boolean dynamic = TabbedPaneTransformerUtil.isDynamicTabbedPane(widget.getParent());
		if (dynamic) {
			if (TabbedPaneTransformerUtil.hideEmptyTab(widget)) {
				Element element = appendElement(context, XSP_NAMESPACE_URI, ATTRIBUTE_ELEMENT_NAME);
				element.setAttribute(NAME_ATTRIBUTE_NAME, "enabled");	
				Element udp = createElement(context, UDP_NAMESPACE_URI, "dynamic-tab-enabled");
				String id = widget.getParent().getID();
				udp.setAttribute("dynamic-tabs-name", TabbedPaneTransformerUtil.getModelReference(widget.getParent())+".Tabs_"+id);	
				udp.setAttribute("tab-name", TabbedPaneTransformerUtil.getDynamicTabFilterValue(widget));		
				element.appendChild(udp);
			}
		}

		// Create the element <xsp:attribute name="selected">
		Element element = appendElement(context, XSP_NAMESPACE_URI, ATTRIBUTE_ELEMENT_NAME);
		element.setAttribute(NAME_ATTRIBUTE_NAME, SELECTED);	
		
		// Create the element <tab:is-selected id="...">
		Element isSelected = createElement(context, TAB_NAMESPACE_URI, ISSELECTED_ELEMENT_NAME);
		isSelected.setAttribute(TABVIEW_ID_ATTRIBUTE, widget.getID());		
		element.appendChild(isSelected);

	}
	
	/**
	 * @param context
	 * @param root 
	 * @param widget
	 */
	private void appendHiddenAttribute(WidgetTransformerContext context, Element root, Widget widget) {
		
		// Create the element <xsp:attribute name="hidden"/>
		Element element = createElement(context, XSP_NAMESPACE_URI, ATTRIBUTE_ELEMENT_NAME);
		element.setAttribute(NAME_ATTRIBUTE_NAME, "hidden");	
		root.appendChild(element);	
		
		// Create the element <xsp:expr>!<tab:is-selected id="..."/></xsp:expr>
		Element expr = createElement(context, XSP_NAMESPACE_URI, EXPRESSION_ELEMENT_NAME);
		element.appendChild(expr);
		expr.setTextContent("!");

		// Create the element <tab:is-selected id="..."/>
		Element isSelected = createElement(context, TAB_NAMESPACE_URI, ISSELECTED_ELEMENT_NAME);
		isSelected.setAttribute(TABVIEW_ID_ATTRIBUTE, widget.getID());		
		expr.appendChild(isSelected);
	}
	
	/**
	 * Append the action to the block box
	 * 
	 * @param context
	 * 			The widget transformer context
	 * @param widget
	 * 			The widget
	 * @param standalone
	 * @exception CoreException 
	 */
	private void appendAction(WidgetTransformerContext context, Widget widget, boolean standalone) throws CoreException {
		ElementEventTransformer eventTransformer = new ElementEventTransformer(true, standalone);
		eventTransformer.setDynamicTabbedPane(TabbedPaneTransformerUtil.isDynamicTabbedPane(widget.getParent()));
		eventTransformer.setHideEmptyTab(TabbedPaneTransformerUtil.hideEmptyTab(widget));
		eventTransformer.transform(context, widget);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.BaseWidgetTransformer#transformChildren(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	@SuppressWarnings("rawtypes")
	protected void transformChildren(WidgetTransformerContext context, Widget widget) throws CoreException {
		for (Iterator it = widget.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(child);
			wt.transform(context, child);
		}
	}
	

	/**
	 * Transforms the child Widgets.
	 *
	 * @param context 
	 * 			The WidgetTransformerContext
	 * @param widget 
	 * 			The Element whose children are to be transformed
	 * @param parent The Widget to append children to
	 * @exception CoreException
	 */
	@SuppressWarnings({ "rawtypes" })
	private void transformChildren(WidgetTransformerContext context, Widget widget, Element parent) throws CoreException {
		for (Iterator it = widget.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			
			// We get a reference to the old parent to include element as children
			// then we set the corresponding parent element of the children
			Element oldParent = context.getParentElement();
			WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(child);
			context.setParentElement(parent);
			wt.transform(context, child);
			context.setParentElement(oldParent);
	
		}
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
		// The code is added to an Xsp:Logic section
		return createElement(context, XSP_NAMESPACE_URI, XSP_LOGIC);
	}	
}
