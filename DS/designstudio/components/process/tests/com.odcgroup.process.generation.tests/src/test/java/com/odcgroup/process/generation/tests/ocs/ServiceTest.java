package com.odcgroup.process.generation.tests.ocs;

import org.w3c.dom.Document;

public class ServiceTest extends BaseWorkflowGenerationTester {

	@Override
	protected void checkDocuments(Document doc1, Document doc2) {
		checkXML(doc1, doc2, "workflow:service", "href");		
	}

}
