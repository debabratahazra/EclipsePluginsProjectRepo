package com.odcgroup.pageflow.generation.tests.ocs;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author pkk
 */
public class DecisionStateMultipleIncomingTransitionsTest extends BasePageflowGenerationTester {

    @Override
    protected void checkDocuments(Document doc1, Document doc2) {

        String element = "wf:transition";
        String key = "to-redirector";
        String process = "wf:process";
        String className = "com.odcgroup.sample.class";

        int count = 0;
        boolean decisionStateFound = false;
        NodeList testNodes = doc2.getElementsByTagName(element);
        for (int i = 0; i < testNodes.getLength(); i++) {
            Node testNode = testNodes.item(i);
            Node redirector = testNode.getAttributes().getNamedItem(key);
            if (redirector != null && redirector.getNodeValue().equals("true")) {
            	decisionStateFound = true;
            	NodeList children = testNode.getChildNodes();
            	for(int j = 0; j < children.getLength(); j++) {
            		Node node = children.item(j);
            		if (node.getNodeName().equals(process)) {
            			Node item = node.getAttributes().getNamedItem("class-name");
            			if (item != null && item.getNodeValue().equals(className)) {
            				count++;
            			}
            		}
            	}
            }
        }
        
        if (decisionStateFound && count != 2) {
        	Assert.fail("Document should contain redirector with process ["+className+"] twice");
        }
    
    }

}
