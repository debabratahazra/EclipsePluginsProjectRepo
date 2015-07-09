package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.migration.internal.AbstractPageModelMigration;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Remove all the events associated with the radio button widget
 *
 * @author pkk
 *
 */
public class RadioButtonEventMigration extends AbstractPageModelMigration {
	
	private static final String RADIO_WIDGET_TYPE = "RadioButton";
	private static final String EVENTS_ELEMENT = "events";

	/**
	 * @see com.odcgroup.page.migration.internal.AbstractPageModelMigration#performMigration(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Node)
	 */
	@Override
	protected void performMigration(IOfsProject ofsProject, URI modelURI, Node modelNode) {
		List<Node> radiobuttons = new ArrayList<Node>();		
		collectAllRadioButtonNodes(modelNode, radiobuttons);
		migrateRadioEvents(radiobuttons);
	}
	
	/**
	 * collect all the checkBox & RadioButton widgets in the module/fragment for migration
	 * 
	 * @param widget node
	 * @param radiobuttons nodes
	 */
	private void collectAllRadioButtonNodes(Node widget, List<Node> radiobuttons) {
		NodeList childNodes = widget.getChildNodes();
		Node node = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {
			node = childNodes.item(ii);
			if (CONTENTS_ELEMENT.equals(node.getNodeName())) {
				Node typeAttr = node.getAttributes().getNamedItem(ATTRIBUTE_TYPENAME);
				if (typeAttr != null) {
					if (isContainerWidgetType(typeAttr)) {
						collectAllRadioButtonNodes(node, radiobuttons);
					} else if (typeAttr.getNodeValue().equals(RADIO_WIDGET_TYPE)) {
						radiobuttons.add(node);
					}
				}
			}
		}		
	}
	
	/**
	 * @param radiobuttons
	 */
	private void migrateRadioEvents(List<Node> radiobuttons) {
		for (Node node : radiobuttons) {
			removeRadioEvents(node);
		}
	}
	
	/**
	 * removes the event nodes if any on the given radio node
	 * 
	 * @param radio
	 */
	private void removeRadioEvents(Node radio) {
		NodeList childNodes = radio.getChildNodes();
		Node child = null;
		for(int ii = 0; ii < childNodes.getLength();ii++) {	
			child = childNodes.item(ii);
			if (EVENTS_ELEMENT.equals(child.getNodeName())) {
				radio.removeChild(child);
			}
		}
		
	}

}