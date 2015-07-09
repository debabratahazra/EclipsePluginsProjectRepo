package com.odcgroup.pageflow.generation.tests.ocs;

import org.w3c.dom.Document;


public class DecisionStateTest extends BasePageflowGenerationTester {

    @Override
    protected void checkDocuments(Document doc1, Document doc2) {
        // check decision state
        checkXML(doc1, doc2, "wf:transition", "to-redirector");
    }

}
