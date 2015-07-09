package com.temenos.t24.tools.eclipse.basic.document.capture;

import org.dom4j.Document;
import org.eclipse.core.runtime.Assert;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;

public class DocumentCreationSupport {

    private static DocumentCreationSupport INSTANCE = new DocumentCreationSupport();
    private Document document;

    public static DocumentCreationSupport getInstance() {
        return INSTANCE;
    }

    public String getDocumentContent(String product, String component) {
        Assert.isNotNull(product, "T24 Product name is null");
        Assert.isNotNull(component, "T24 Component name is null");
        document = getDocument(product, component);
        if (document == null) {
            return null;
        }
        String xpath = "//description";
        String componentDocument = XmlUtil.getText(document, xpath);
        return componentDocument.trim();
    }

    public void updateDocument(String product, String component, String docContent) {
        Assert.isNotNull(product, "T24 Product name is null");
        Assert.isNotNull(component, "T24 Component name is null");
        Assert.isNotNull(document, "Component document is null");
        String xpath = "//description";
        XmlUtil.setText(document, xpath, docContent);
        String path = DocumentFileUtil.getComponentPath(product, component);
        (new FileUtil()).saveToFile(document.asXML(), path, true);
        EditorDocumentUtil.getIFile(path);
    }

    private DocumentCreationSupport() {
    }

    private Document getDocument(String product, String component) {
        String path = DocumentFileUtil.getComponentPath(product, component);
        return XmlUtil.loadDocument(path);
    }
}
