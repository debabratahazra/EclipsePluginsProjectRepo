package com.temenos.t24.tools.eclipse.basic.document;

import org.dom4j.Document;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;

public class ProductDocumentSupplier {

  

    private ProductDocumentSupplier() {
    }

  
    public static String getProductDocument(String product) {
        Document document = getDocument(product);
        if (document == null) {
            return null;
        }
        // TODO: Raga - chk for product.xml format
        // String xpath = "//product/" + product;
        String xpath = "//description";
        String doc = XmlUtil.getText(document, xpath);
        return doc.trim();
    }

    private static Document getDocument(String product) {
        // TODO: Raga - chk for product.xml format
        // if (document != null) {
        // Node node = XmlUtil.getSingleNode(document, "//product/*");
        // if (product.equals(node.getName())) {
        // return document;
        // }
        // }
        
        String path = DocumentFileUtil.getProductPath(product);
        Document document = XmlUtil.loadDocument(path);
        return document;
    }
}
