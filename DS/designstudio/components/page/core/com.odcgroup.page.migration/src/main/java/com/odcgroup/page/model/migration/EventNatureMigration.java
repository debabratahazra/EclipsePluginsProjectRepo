package com.odcgroup.page.model.migration;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Add a new attribute "nature" to each event widget that represents a
 * simplified event.
 * 
 * <p>
 * The nature is set to SIMPLIFIED for each recognized simplified event
 * otherwise nothing is done. By default the value of the event nature is
 * "ADVANCED".
 * 
 * <p>
 * A simplified event is recognized if one of the following conditions are met.
 * 
 * <p>
 * eventName = "OnClick" and method="post" and
 * 
 * @author atr
 */
public class EventNatureMigration extends AbstractDOMModelMigration {

	/** Helper class */
	private static class Parameter {

		String name;
		String value;
		boolean userDefined = false;

		public final String getName() {
			return name;
		}
		
		public final boolean hasName() {
			return StringUtils.isNotEmpty(getName());
		}

		public final String getValue() {
			return value;
		}

		public boolean hasValue() {
			return StringUtils.isNotEmpty(getValue());
		}

		public final boolean isUserDefined() {
			return userDefined;
		}

		public Parameter(Node node) {
			Node attribute = node.getAttributes().getNamedItem("name");
			if (attribute != null) {
				this.name = attribute.getNodeValue();
			}
			attribute = node.getAttributes().getNamedItem("value");
			if (attribute != null) {
				this.value = attribute.getNodeValue();
			}
			attribute = node.getAttributes().getNamedItem("userDefined");
			if (attribute != null) {
				this.userDefined = "true".equalsIgnoreCase(attribute.getNodeValue());
			}
		}
	}
	
	private boolean isOnClickEvent(Node node) {
		Node nameNode = node.getAttributes().getNamedItem("eventName");
		return nameNode != null && nameNode.getNodeValue().equalsIgnoreCase("OnClick");		
	}

	private boolean isSubmitFunction(Node node) {
		Node fctNode = node.getAttributes().getNamedItem("functionName");
		return fctNode != null && fctNode.getNodeValue().equalsIgnoreCase("submit");
	}

	/**
	 * @param event the event to be checked
	 * @return {@code true} if the given node is a simplified event
	 */
	private boolean isSimplifiedEvent(Node event) {
		
		boolean simplified = false;
		
		Map<String, Parameter> parameters = new HashMap<String, Parameter>();

		NodeList list = event.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeName().equals("parameters") && node.getAttributes() != null) {
				
				/*
				 * Collect all meaningful parameters. 
				 * All user defined nodes are ignored except "flow-action".
				 * All parameter with no value are also ignored
				 */
				Parameter param = new Parameter(node);
				if (param.hasName()) {
					if ("flow-action".equals(param.getName())) {
						if (param.isUserDefined()) {
							// keep flow-action user defined parameter
							parameters.put(param.getName(), param);
						}
					} else if (! param.isUserDefined() && param.hasValue()) {
						// keep all parameter with a value and not user defined
						parameters.put(param.getName(), param);
					}
				}
			}
		}
				
		Parameter method = parameters.get("method");
		if ((method != null) && "post".equals(method.getValue())) { 
			Parameter callUri = parameters.get("call-URI");
			if (callUri != null && callUri.hasValue()) {
				if ("<pageflow:continuation/>".equals(callUri.getValue())) {
					Parameter flowAction = parameters.get("flow-action");
					if (flowAction != null && flowAction.hasValue() && flowAction.isUserDefined()) {
						Parameter target = parameters.get("target");
						simplified = (target == null) || (target != null && ! target.hasValue());
					}
				} else if (callUri.getValue().endsWith(".pageflow")) {
					Parameter target = parameters.get("target");
					if (target != null && target.hasValue()) {
						Parameter group = parameters.get("widget-group-ref");
						simplified = (group == null) || (group != null && ! group.hasValue());
					}
				}
			}
		}

		return simplified;
	}

	/**
	 * Walk recursively through the DOM tree and migrate specific node
	 * 
	 * @param document
	 *            the DOM tree
	 * @param node
	 *            the node to be inspected
	 */
	private void migrate(Document document, Node node) {

		if (node == null) {
			return;
		}

		// look for "event" nodes and try to migrate
		if (node.getNodeName().equals("events") && node.getAttributes() != null) {
			if (isOnClickEvent(node) && isSubmitFunction(node) && isSimplifiedEvent(node)) {
				Node natureNode = node.getAttributes().getNamedItem("nature");
				if (natureNode == null) {
					// add the new nature attribute
					Attr nature = node.getOwnerDocument().createAttribute("nature");
					nature.setValue("SIMPLIFIED");
					node.getAttributes().setNamedItem(nature);
				} else {
					// nature exist ensure it has the correct value
					natureNode.setNodeValue("SIMPLIFIED");
				}
			}
		} else {
			// migrate children
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				migrate(document, list.item(i));
			}
		}

	}

	/**
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject,
	 *      org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		migrate(document, (Node) document);
	}

}
