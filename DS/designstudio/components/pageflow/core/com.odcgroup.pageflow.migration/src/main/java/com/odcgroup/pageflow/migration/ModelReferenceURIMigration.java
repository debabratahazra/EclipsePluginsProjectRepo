package com.odcgroup.pageflow.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * Migration of model references in pageflow models
 * to  make them compatible with the new changes to the resource URI format
 *
 * @author pkk
 *
 */
public class ModelReferenceURIMigration extends AbstractDOMModelMigration {
	
	private static final String PAGEFLOW_ELEMENT = "pageflow:Pageflow";
	private static final String VIEWSTATE = "pageflow:ViewState";
	private static final String SUBPAGEFLOW_STATE = "pageflow:SubPageflowState";
	private static final String STATE = "states";
	private static final String VIEW = "view";
	private static final String SUB_PAGEFLOW = "subPageflow";
	private static final String TRANSITION_MAP = "transitionMappings";
	private static final String ENDSTATE = "endState";
	
	private static final String TYPE_ATTRIBUTE = "xmi:type";
	private static final String HREF_ATTRIBUTE = "href";
	private static final String URL_ATTRIBUTE = "url";

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate
	 * (com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		if (modelURI.fileExtension().equals("pageflow")) {
			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName(PAGEFLOW_ELEMENT);
			if (list != null && list.getLength() > 0) {
				Node pageflow = list.item(0);
				NodeList nodes = pageflow.getChildNodes();

				List<Node> views = new ArrayList<Node>();
				List<Node> subs = new ArrayList<Node>();
				for (int i = 0; i < nodes.getLength(); i++) {
					Node child = nodes.item(i);
					if (child.getNodeName().equals(STATE)) {
						if (checkStateType(child, VIEWSTATE)) {
							Node view = getChildNode(child, VIEW);
							if (view != null) {
								views.add(view);
							}
						} else if (checkStateType(child, SUBPAGEFLOW_STATE)) {
							Node sub = getChildNode(child, SUB_PAGEFLOW);
							if (sub != null) {
								subs.add(sub);
							}			
							subs.addAll(getTransitionMappingEndStates(child));
						} 
					}					
				}
				
				migrateViewReferences(views);
				migrateSubpageflowReferences(subs);
			}
		}

	}
	
	/**
	 * @param views
	 */
	private void migrateViewReferences(List<Node> views) {
		for (Node node : views) {
			Node urlNode = node.getAttributes().getNamedItem(URL_ATTRIBUTE);
			if (urlNode != null) {
				migrateReferenceURINode(urlNode);
			}
		}
	}
	
	/**
	 * @param subs
	 */
	private void migrateSubpageflowReferences(List<Node> subs) {
		for (Node node : subs) {
			Node hrefNode = node.getAttributes().getNamedItem(HREF_ATTRIBUTE);
			if (hrefNode != null) {
				migrateReferenceURINode(hrefNode);
			}
		}
	}
	
	/**
	 * @param node
	 */
	private void migrateReferenceURINode(Node node) {
		if (node != null) {
			String uri = node.getNodeValue();
			if (uri.startsWith("resource:")) {
				String tmp = uri.substring("resource:".length());
				char[] chars = tmp.toCharArray();
				int k = 0;
				while (chars[k] == '/') {
					k++;
				}
				for (int j = 3-k; j > 0; j--) {
					tmp = "/"+tmp;
				}
				node.setNodeValue("resource:"+tmp);
			}
		}
	}
	
	/**
	 * @param state
	 * @param stateType
	 * @return
	 */
	private boolean checkStateType(Node state, String stateType) {
		NamedNodeMap attributes = state.getAttributes();
		Node type = attributes.getNamedItem(TYPE_ATTRIBUTE);
		if (type != null && type.getNodeValue().equals(stateType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param viewstate
	 * @return
	 */
	private Node getChildNode(Node state, String viewType) {
		NodeList children = state.getChildNodes();
		Node child = null;
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if (child.getNodeName().equals(viewType)) {
				return child;
			}
		}
		return null;
	}
	
	/**
	 * @param substate
	 * @return
	 */
	private List<Node> getTransitionMappingEndStates(Node substate) {
		List<Node> endStates = new ArrayList<Node>();
		NodeList children = substate.getChildNodes();
		Node child = null;
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if (child.getNodeName().equals(TRANSITION_MAP)) {
				Node end = getChildNode(child, ENDSTATE);
				if (end != null) {
					endStates.add(end);
				}
			}
		}
		return endStates;	
		
	}
	
	
	
	

}
