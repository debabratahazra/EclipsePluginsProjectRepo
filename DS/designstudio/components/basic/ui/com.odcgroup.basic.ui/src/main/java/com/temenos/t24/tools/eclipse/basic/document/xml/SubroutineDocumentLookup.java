package com.temenos.t24.tools.eclipse.basic.document.xml;

import org.dom4j.Document;
import org.dom4j.Node;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;

public class SubroutineDocumentLookup {

    private static SubroutineDocumentLookup INSTANCE = new SubroutineDocumentLookup();
    private Document document;

    private SubroutineDocumentLookup() {
    }

    public static SubroutineDocumentLookup getInstance() {
        return INSTANCE;
    }

    public SubroutineInfo getSubroutineInfo(String subroutineName) {
        if (document == null) {
            loadDocument();
        }
        String product = "";
        String component = "";
        // prior code
        // Node node = XmlUtil.getSingleNode(document, "//subroutines/" +
        // subroutineName);
        // modified by bharath
        if (document != null) {
            Node node = XmlUtil.getSingleNode(document, "//sources/source[@name ='" + subroutineName + "']");
            if (node != null) {
                product = XmlUtil.getAttributeValue(node, "product");
                component = XmlUtil.getAttributeValue(node, "component");
            }
        }
        return new SubroutineInfo(subroutineName, product, component);
    }

    private void loadDocument() {
        String lookupPath = DocumentFileUtil.getSubroutineLookupPath();
        document = XmlUtil.loadDocument(lookupPath);
    }
}
