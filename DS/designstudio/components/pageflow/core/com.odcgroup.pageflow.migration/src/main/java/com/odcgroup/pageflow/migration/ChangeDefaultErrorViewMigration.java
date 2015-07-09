package com.odcgroup.pageflow.migration;

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
 * DS-5176
 * @author phanikumark
 *
 */
public class ChangeDefaultErrorViewMigration extends AbstractDOMModelMigration {
	
	private static final String PAGEFLOW_ELEMENT = "pageflow:Pageflow";
	private static final String OLD_DEF_URL = "/page/common/OneModule?module=general/WorkflowErrorGeneral";
	private static final String NEW_DEF_URL = "/page/common/OneModule?module=general/PageflowErrorModule";
	
	/**
	 * 
	 */
	public ChangeDefaultErrorViewMigration() {
	}

	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI,
			Document document) throws MigrationException {
		if (modelURI.fileExtension().equals("pageflow")) {
			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName(PAGEFLOW_ELEMENT);
			if (list != null && list.getLength() > 0) {
				Node pageflow = list.item(0);
				NodeList nodes = pageflow.getChildNodes();
				for (int i = 0; i < nodes.getLength(); i++) {
					Node child = nodes.item(i);
					if (child.getNodeName().equals("errorView")) {
						NamedNodeMap attrs = child.getAttributes();
						Node urlattr = attrs.getNamedItem("url");
						if (OLD_DEF_URL.equals(urlattr.getNodeValue())) {
							urlattr.setNodeValue(NEW_DEF_URL);
						}
					}					
				}
			}
		}
		
	}

}
