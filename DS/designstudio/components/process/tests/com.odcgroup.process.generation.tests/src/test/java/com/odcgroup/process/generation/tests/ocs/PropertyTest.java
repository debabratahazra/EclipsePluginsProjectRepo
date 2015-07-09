/**
 * 
 */
package com.odcgroup.process.generation.tests.ocs;

import org.w3c.dom.Document;

/**
 * @author nba
 *
 */
public class PropertyTest extends BaseWorkflowGenerationTester {

	@Override
	protected void checkDocuments(Document doc1, Document doc2) {
		checkXML(doc1, doc2, "wf:property", "name");		
	}

}
