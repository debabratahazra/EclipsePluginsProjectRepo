package com.temenos.t24.tools.eclipse.basic.views.document;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;

public class DocumentTreeViewInitialiser {

    private static DocumentTreeViewInitialiser INSTANCE = new DocumentTreeViewInitialiser();
    private Document document;

    private DocumentTreeViewInitialiser() {
    }

    public static DocumentTreeViewInitialiser getInstance() {
        return INSTANCE;
    }

    /**
     * get the product from product lookup file
     * 
     * @param productPath - xpath for component specific/general
     * @return
     */
    public List<String> getProducts(String productPath) {
        loadDocument();
        if (document == null) {
            return new ArrayList<String>();
        }
        List<String> products = XmlUtil.getNodesText(document, productPath);
        return products;
    }

    public String[] getComponents(String product) {
        loadDocument();
        if (document == null) {
            return new String[0];
        }
        String componentsText = XmlUtil.getText(document,DocumentViewConstants.PRODUCT_XPATH + product);
        String[] components = componentsText.split(",");
        return components;
    }

    private void loadDocument() {
        String path = DocumentFileUtil.getProductLookupPath();
        document = XmlUtil.loadDocument(path);
    }
}
