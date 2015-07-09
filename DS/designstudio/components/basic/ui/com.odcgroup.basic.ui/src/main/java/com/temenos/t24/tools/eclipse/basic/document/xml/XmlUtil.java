package com.temenos.t24.tools.eclipse.basic.document.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;

public class XmlUtil {
    
    private static LogConsole console = LogConsole.getT24BasicConsole();

    public static Node getSingleNode(Document document, String path) {
        Node node = document.selectSingleNode(path);
        return node;
    }

    public static String getText(Document document, String path) {
        Node node = document.selectSingleNode(path);
        if (node != null) {
            return node.getStringValue();
        }
        return "";
    }
    /** code to get Subroutine Names under the Source folder,
     * stored & return the sourceName split/ending with (',').  
       **/
    //  Mahudesh 
    //TODO to be refactored xmlutil
    @SuppressWarnings("unchecked")
    public static String getSourceText(Document document, String path){
        List<Element> node=document.selectNodes(path);
        ListIterator<Element>  it=node.listIterator();
        String sourceName="";
        while(it.hasNext()){
            Element element=it.next();
            sourceName+=element.attributeValue("name")+",";
        }
        return sourceName;
    }
    //  Mahudesh
    @SuppressWarnings("unchecked")
    public static List<String> getAttributeValues(Document document, String xpath, String attribute) {
        if (document == null) {
         return new ArrayList<String>();
        }
        List<Node> nodes = document.selectNodes(xpath);
        List<String> values = new ArrayList<String>();
        for (Node node : nodes) {
            String value = getAttributeValue(node, attribute);
            values.add(value);
        }
        return values;
    }

    public static String getAttributeValue(Node node, String name) {
        String attrValue = node.valueOf("@" + name);
        return attrValue;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<String> getNodesText(Document document, String path) {
        List<String> nodesText = new ArrayList<String>();
        if(document==null){
         console.logMessage("Configure the T24 documentation directory location in the preference page");
           return  nodesText;
        }
        List nodes = document.selectNodes(path);
       
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            nodesText.add(node.getName());
        }
        return nodesText;
    }

    public static String getChildNodeValue(Node node, String path) {
        Node childNode = node.selectSingleNode(path);
        if (childNode != null) {
            return childNode.getStringValue();
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    public static List<Node> getNodes(Node node, String path) {
        List<Node> childNodes = node.selectNodes(path);
        return childNodes;
    }
    
    @SuppressWarnings("unchecked")
    public static List<Node> getNodes(Document document, String path) {
      List<Node> nodes = document.selectNodes(path);
      return nodes;
  }

    public static Document loadDocument(String path) {
        SAXReader reader = new SAXReader();
        File lookupFile = new File(path);
        try {
            return reader.read(lookupFile);
        } catch (DocumentException e) {
            return null;
        }
    }
    
    public static void setText(Document document, String path,String text) {
        Node node = document.selectSingleNode(path);
        if (node != null) {
           node.setText(text);
        }
    }
}
