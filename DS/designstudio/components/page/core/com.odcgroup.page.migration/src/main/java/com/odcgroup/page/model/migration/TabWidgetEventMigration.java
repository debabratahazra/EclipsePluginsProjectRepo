package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.page.model.util.QTPCompliantIDGenerator;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * DS-2362
 * remove the events associated with tabs, 
 * which were used to manage tab display
 *
 * @author pkk
 *
 */
public class TabWidgetEventMigration extends AbstractPageModelMigration {
	

	private static final String EVENTS_ELEMENT = "events";
	private static final String WIDGET_TABBEDPANE = "TabbedPane";
	private static final String WIDGET_TAB = "Tab";


	/* (non-Javadoc)
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI,
			Node modelNode) {
		List<Node> panes = new ArrayList<Node>();				
		collectAllTabbedPaneNodes(modelNode, panes);
		migrateTabEvents(panes);		
	}
	
	
	/**
	 * look for the events if any on the tabs of the tabbedpanes
	 * and remove them
	 * 
	 * @param tabbedPanes
	 */
	private void migrateTabEvents(List<Node> tabbedPanes) {
		for (Node tabbedPane : tabbedPanes) {
			setID(tabbedPane, false);
			NodeList tabs = tabbedPane.getChildNodes();		
			Node tab = null;
			for(int ii = 0; ii < tabs.getLength();ii++) {				
				tab = tabs.item(ii);
				setID(tab, true);
				if (CONTENTS_ELEMENT.equals(tab.getNodeName())) {
					Node typeAttr = tab.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
					if (typeAttr != null) {
						if (WIDGET_TAB.equals(typeAttr.getNodeValue())) {
							removeTabEvents(tab);
						}
					}
				}
			}
		}
	}
	
	/**
	 * check whether id property exists for the given node
	 * @param node
	 * @return
	 */
	private void setID(Node node, boolean tab) {
		NodeList childNodes = node.getChildNodes();
		Node child = null;
		for(int ii = 0;ii<childNodes.getLength();ii++) {
			child = childNodes.item(ii);
			if (PROPERTIES_ELEMENT.equals(child.getNodeName())) {
				Node typeAttr = child.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (TYPE_ID.equals(typeAttr.getNodeValue())) {
						Node idVal = child.getAttributes().getNamedItem("value");
						String val = idVal.getNodeValue();
						if (StringUtils.isEmpty(val)) {
							String idValue = QTPCompliantIDGenerator.generateID();
							if (tab) {
								idValue = "tab_"+idValue;
							}
							idVal.setNodeValue(idValue);
						}
					}
				}
			}
		}
	}
	
	/**
	 * removes the event nodes if any on the given tab node
	 * 
	 * @param tab
	 */
	private void removeTabEvents(Node tab) {
		NodeList childNodes = tab.getChildNodes();
		Node child = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {	
			child = childNodes.item(ii);
			if (EVENTS_ELEMENT.equals(child.getNodeName())) {
				tab.removeChild(child);
			}
		}
		
	}
	
	/**
	 * collect all the tabbed-panes in the module/fragment
	 * 
	 * @param widget
	 * @param tabbedPanes
	 * @return
	 */
	private List<Node> collectAllTabbedPaneNodes(Node widget, List<Node> tabbedPanes) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainerWidgetType(typeAttr)) {
						if (typeAttr.getNodeValue().equals(WIDGET_TABBEDPANE)) {
							tabbedPanes.add(node);
						}
						collectAllTabbedPaneNodes(node, tabbedPanes);
					}
				}
			}
		}
		return tabbedPanes;
	}	

}
