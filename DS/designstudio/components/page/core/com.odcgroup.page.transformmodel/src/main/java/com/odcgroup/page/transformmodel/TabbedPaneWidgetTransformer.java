package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ROOT_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TABBED_PANE_ELEMENT;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * The transformer for a TabbedPane. This works in conjunction with a TabWidgetTransformer.
 * 
 * @author Phani Kumar Kotaprolu
 * @author atr, added dynamic tabs (DS-3437)
 */
public class TabbedPaneWidgetTransformer extends BaseWidgetTransformer {


	
	/**
	 * Generates element &lt;udp:build-dynamic-tabs-enabled&gt;
	 * @param context
	 * @param tabbedPane
	 */
	private void generateSQLRequest(WidgetTransformerContext context, Widget tabbedPane) {
		Element elem = createElement(context, UDP_NAMESPACE_URI, "build-dynamic-tabs-enabled");
		context.getParentElement().appendChild(elem);
		String modelReference = TabbedPaneTransformerUtil.getModelReference(tabbedPane);
		elem.setAttribute("delegating-model-ref", modelReference);
		elem.setAttribute("dynamic-tabs-name", modelReference+".Tabs"+"_"+tabbedPane.getID());
		elem.setAttribute("tab-column", TabbedPaneTransformerUtil.getPropertyValue(tabbedPane, "tabs-filtered-attribute"));
		elem.setAttribute("level-max",TabbedPaneTransformerUtil.getFilterLevel(tabbedPane)+"");
	}
	
	/**
	 * Creates a new TabbedPaneWidgetTransformer.
	 * 
	 * @param type
	 *            the widget type
	 */
	public TabbedPaneWidgetTransformer(WidgetType type) {
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
		
		
		boolean isDynamic = TabbedPaneTransformerUtil.isDynamicTabbedPane(widget);
		if (isDynamic) {

			if (context.isRootUDPOpen()) {
				// do not generate tag udp:udp 
				generateSQLRequest(context, widget);
				Element tabView = generateTabViewElement(context,widget);		
				transformTabbedPane(context, widget, tabView, standalone);
			} else {
				// generate a tag udp:udp 
				context.openRootUDP();
				Element udpElem = createElement(context, UDP_NAMESPACE_URI, UDP_ROOT_ELEMENT);
				context.getParentElement().appendChild(udpElem);
				Element oldParent = context.getParentElement();
				context.setParentElement(udpElem);
				generateSQLRequest(context, widget);
				Element tabView = generateTabViewElement(context,widget);		
				transformTabbedPane(context, widget, tabView, standalone);
				context.setParentElement(oldParent);
				context.closeRootUDP();
			}
			
		} else {
			Element tabView = generateTabViewElement(context,widget);		
			transformTabbedPane(context, widget, tabView, standalone);
		}
		
	}
	
	/**
	 * transform the tabbed-pane
	 * 
	 * @param context
	 * @param widget
	 * @param tabView 
	 * @param standalone
	 * @exception CoreException 
	 */
	private void transformTabbedPane(WidgetTransformerContext context, Widget widget, Element tabView, boolean standalone) throws CoreException {
		Element element = createElement(context, XGUI_NAMESPACE_URI, XGUI_TABBED_PANE_ELEMENT);
		TransformUtils.appendChild((Element) tabView, element);
		List<Widget> tabs = widget.getContents();
		Widget defaultTab = null;
		if (!tabs.isEmpty()) {
			defaultTab = tabs.get(0);
			String tabId = defaultTab.getID();
			tabView.setAttribute(TransformerConstants.TABVIEW_DEFAULTSELECTION_ATTRIBUTE, tabId);
		}
		String id = widget.getID();
		if (!StringUtils.isEmpty(id)) {
			element.setAttribute(TransformerConstants.TABVIEW_ID_ATTRIBUTE, id);
		}
		// Set the parent so that the child elements are set on the correct element
		Element oldParent = context.setParentElement(element);
		generateTabs(context, widget);
		context.setParentElement(oldParent);
	}
	
	/**
	 * @param context
	 * @param widget
	 * @return Element
	 */
	private Element generateTabViewElement(WidgetTransformerContext context, Widget widget) {
		Namespace ns = context.getTransformModel().findNamespace(TransformerConstants.TAB_NAMESPACE_URI);
		Element element = context.getDocument().createElementNS(ns.getUri(), TransformerConstants.TABVIEW_ELEMENT_NAME);
		element.setPrefix(ns.getPrefix());
		boolean isDynamic = TabbedPaneTransformerUtil.isDynamicTabbedPane(widget);
		String ID = "";
		if (isDynamic) {
			String modelReference = TabbedPaneTransformerUtil.getModelReference(widget);
			ID = modelReference+".TabViews"+"_"+widget.getID();
		} else {
			ID = widget.getID();
		}
		element.setAttribute(TransformerConstants.TABVIEW_ID_ATTRIBUTE, ID);
		TransformUtils.appendChild(context, element);
		return element;
	}

	/**
	 * Generates the tab tags
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @exception CoreException
	 */
	@SuppressWarnings("unchecked")
	private void generateTabs(WidgetTransformerContext context, Widget widget) throws CoreException {
		for (Iterator it = widget.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(child);
			wt.transform(context, child);
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
		return createElement(context, XGUI_NAMESPACE_URI, XGUI_TABBED_PANE_ELEMENT);
	}	
}
