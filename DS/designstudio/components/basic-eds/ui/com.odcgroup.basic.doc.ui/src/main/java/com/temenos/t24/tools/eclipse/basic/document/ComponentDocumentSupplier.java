package com.temenos.t24.tools.eclipse.basic.document;

import java.util.List;

import org.dom4j.Document;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;

public class ComponentDocumentSupplier {

    private ComponentDocumentSupplier() {
    }

    public static String getComponentDocument(String product, String component) {
        String path = DocumentFileUtil.getComponentPath(product, component);
        Document document = getDocument(path);
        if (document == null) {
            return null;
        }
        String xpath = "//description";
        String doc = XmlUtil.getText(document, xpath);
        return doc.trim();
    }

    public static String[] getSubroutines(String product, String component) {
        String path = DocumentFileUtil.getSubroutinePath(product, component);
        Document document = getDocument(path);
        if (document == null) {
            return new String[0];
        }
        String xpath = "//component/" + component + "/source/subroutine";
        String subroutines = XmlUtil.getText(document, xpath);
        return subroutines.trim().split(",");
    }

    public static String[] getDataFiles(String product, String component) {
        String path = DocumentFileUtil.getDataFilePath(product, component);
        Document document = getDocument(path);
        String xpath = "//tables/table";
        List<String> values = XmlUtil.getAttributeValues(document, xpath, "name");
        String[] stringValues = new String[values.size()];
        return values.toArray(stringValues);
    }

    public static String[] getBatchFiles(String product, String component) {
        String path = DocumentFileUtil.getCobFilePath(product, component);
        Document document = getDocument(path);
        String xpath = "//batches/batch";
        List<String> values = XmlUtil.getAttributeValues(document, xpath, "name");
        String[] stringValues = new String[values.size()];
        return values.toArray(stringValues);
    }
   /** The method was used to get path of the "ComponentName_Sources.XML"
       and using getDocumebt methods to read that .Xml file 
    */
    //Mahudesh
    
    public static String[] getSourceFiles(String product, String component){
        String path= DocumentFileUtil.getSourceFilePath(product, component);
        Document document= getDocument(path);
        if (document == null) {
            return new String[0];
        }
        String xpath = "//sources/source";
        String subroutines = XmlUtil.getSourceText(document, xpath);
        return subroutines.trim().split(",");
       // mahudesh
    }
    private static Document getDocument(String path) {
        return XmlUtil.loadDocument(path);
    }
}
