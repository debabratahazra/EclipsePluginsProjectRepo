package com.odcgroup.process.migration;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractDOMModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class RemoveModifiedByModifiedDateMigration extends AbstractDOMModelMigration {

	private String NS = "process:Process";
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractDOMModelMigration#migrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.common.util.URI, org.w3c.dom.Document)
	 */
	@Override
	protected void migrate(IOfsProject ofsProject, URI modelURI, Document document) throws MigrationException {
		Element elem = ((org.w3c.dom.Element)document.getElementsByTagName(NS).item(0));
		elem.removeAttribute("modifiedBy");
		elem.removeAttribute("modifiedDate");
	}
	

}
