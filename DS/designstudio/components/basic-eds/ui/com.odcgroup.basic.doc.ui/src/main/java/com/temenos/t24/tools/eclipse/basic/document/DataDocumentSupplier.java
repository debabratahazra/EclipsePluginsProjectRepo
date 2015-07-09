package com.temenos.t24.tools.eclipse.basic.document;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.temenos.t24.tools.eclipse.basic.document.parser.TableInfo;
import com.temenos.t24.tools.eclipse.basic.document.parser.TablesWrapper;
import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;
import com.temenos.t24.tools.eclipse.basic.views.document.DocumentViewConstants;

public class DataDocumentSupplier {

    private DataDocumentSupplier() {
    }

    public static String getDataFileDocument(String product, String component, String file) {
        Document document = getDocument(product, component);
        if (document == null) {
            return null;
        }
        String xpath = "//tables/table[@name='" + file + "']";
        Node node = XmlUtil.getSingleNode(document, xpath);
        String name = XmlUtil.getAttributeValue(node, "name");
        String type = XmlUtil.getAttributeValue(node, "type");
        String classification = XmlUtil.getAttributeValue(node, "classification");
        String xpathDesc = xpath + "/description";
        String tableDesc = XmlUtil.getText(document, xpathDesc);
        name = DocumentViewConstants.tableName +" "+ name + "\n\n" + DocumentViewConstants.description +" "+ tableDesc +"\n\n"
                + DocumentViewConstants.type +" "+ type + "\n\n" + DocumentViewConstants.classification +" "+ classification + "\n\n";
        return name;
    }

    @SuppressWarnings("unchecked")
	public static TablesWrapper getTables(String product, String component) {
        TablesWrapper tablesWrapper = new TablesWrapper(component);
        Document document = getDocument(product, component);
        if (document == null) {
            return null;
        }
        List<Node> list = document.selectNodes("//table");
        for (Node node : list) {
            String name = XmlUtil.getAttributeValue(node, "name");
            String type = XmlUtil.getAttributeValue(node, "type");
            String classification = XmlUtil.getAttributeValue(node, "classification");
            String xpathDesc = "description";
            String tableDesc= XmlUtil.getChildNodeValue(node, xpathDesc);
            TableInfo newTable = TableInfo.newTable(name, tableDesc, type, classification);
            tablesWrapper.addTable(newTable);
        }
        return tablesWrapper;
    }

    private static Document getDocument(String product, String component) {
        String path = DocumentFileUtil.getDataFilePath(product, component);
        return XmlUtil.loadDocument(path);
    }
}
