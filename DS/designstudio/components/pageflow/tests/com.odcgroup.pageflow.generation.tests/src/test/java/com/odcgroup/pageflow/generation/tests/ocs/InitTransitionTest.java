package com.odcgroup.pageflow.generation.tests.ocs;

import org.w3c.dom.Document;

public class InitTransitionTest extends BasePageflowGenerationTester {

    @Override
	protected void checkDocuments(Document doc1, Document doc2) {
		checkXML(doc1, doc2, "wf:init-transition", "name");
	}

 
}
