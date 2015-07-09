/**
 * 
 */
package com.odcgroup.pageflow.generation.tests.ocs;

import org.w3c.dom.Document;

/**
 * @author nba
 *
 */
public class ProcessTest extends BasePageflowGenerationTester {

    /**
	 * @see com.odcgroup.pageflow.generation.ocs.BasePageflowGenerationTester#checkDocuments(org.w3c.dom.Document, org.w3c.dom.Document)
	 */
	@Override
	protected void checkDocuments(Document doc1, Document doc2) {
		checkXML(doc1, doc2, "wf:process", "class-name");
	}

}
