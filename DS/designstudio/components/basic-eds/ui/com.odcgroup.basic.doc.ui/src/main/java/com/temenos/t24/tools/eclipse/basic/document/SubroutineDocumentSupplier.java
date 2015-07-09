package com.temenos.t24.tools.eclipse.basic.document;

import java.util.List;

import org.dom4j.Document;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineDocumentLookup;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;

public class SubroutineDocumentSupplier {

    private SubroutineDocumentSupplier() {
    }

    public static String getSubroutineDocument(String subroutine) {
        Document document = getDocument(subroutine);
        if (document == null) {
            return "";
        }
        String xpath = "//doc/subroutine/" + subroutine;
        String doc = XmlUtil.getText(document, xpath);
        return doc;
    }

    public static String getGosubDocument(String subroutine, String gosub) {
        Document document = getDocument(subroutine);
        if (document == null) {
            return "";
        }
        String xpath = "//doc/gosubs/" + gosub;
        String doc = XmlUtil.getText(document, xpath);
        return doc;
    }

    public static String[] getGosubs(String subroutine) {
        Document document = getDocument(subroutine);
        String xpath = "//doc/gosubs/*";
        List<String> gosubsList = XmlUtil.getNodesText(document, xpath);
        String[] gosubs = new String[gosubsList.size()];
        gosubsList.toArray(gosubs);
        return gosubs;
    }

    private static Document getDocument(String subroutine) {
        SubroutineInfo subroutineInfo = SubroutineDocumentLookup.getInstance().getSubroutineInfo(subroutine);
        if (subroutineInfo == null) {
            return null;
        }
        String path = DocumentFileUtil.getSubroutinePath(subroutineInfo.getProduct(), subroutineInfo.getComponent());
        Document document = XmlUtil.loadDocument(path);
        return document;
    }
}
