package com.odcgroup.pageflow.generation.tests.ocs;

import org.w3c.dom.Document;

public class SubPageflowTest extends BasePageflowGenerationTester {

    @Override
	protected void checkDocuments(Document doc1, Document doc2) {
		// checking sub-pageflows
		checkXML(doc1, doc2, "wf:transition", "name");
	}

}
