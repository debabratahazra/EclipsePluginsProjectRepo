package com.temenos.t24.tools.eclipse.basic.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;


public class XmlUtil {
    
    /**
     * Returns the text associated to a node 
     * @param xml - Xml string 
     * @param xpath - xpath within the Xml string
     * @return text assigned to the node. If the node doesn't exist
     * it'll return null. If there is no text in the node <node/> it'll
     * return "".
     */
    public static String getText(String xml, String xpath) {
        // the xml may have special characters, starting with &#, which dom4j 
        // can't parse. They are masked out here, and un-masked later on.
        xml = xml.replaceAll("&#", "@~@");
        String result = "";
        try {
            Document dom = DocumentHelper.parseText(xml);
            Node node = dom.selectSingleNode(xpath);
            if(node!=null){
                result = node.getText();
            } else {
                // Node does not exist
                result = null;
            }
        } catch (DocumentException e) {
            return null;
        } catch(Exception e){
            return null;
        }
        if(result!=null){
            // un-mask the &# characters (if any)
            result = result.replaceAll("@~@", "&#");
        }
        return result;
    }
    
    /**
     * Returns the text associated to a node. It'll never return null, 
     * even if the xpath points to a non existent node. 
     * If there is no text in the node it'll return ""
     * @param xml - Xml string 
     * @param xpath - xpath within the Xml string
     * @return text assigned to the node. If the node doesn't exist
     * it'll return "". If there is no text in the node (e.g. <node/>) it'll
     * return "".
     */
    public static String getSafeText(String xml, String xpath) {
        String result = "";
        try {
            // the xml may have special characters, starting with &#, which dom4j 
            // can't parse. They are masked out here, and un-masked later on.
            xml = xml.replaceAll("&#", "@~@");        
            
            Document dom = DocumentHelper.parseText(xml);
            Node node = dom.selectSingleNode(xpath);
            if(node!=null){
                result = node.getText();
            } else {
                // Node does not exist
                result = "";
            }
        } catch (DocumentException e) {
            return "";
        } catch(Exception e){
            return "";
        }
        if(result!=null){
            // un-mask the &# characters (if any)
            result = result.replaceAll("@~@", "&#");
        }        
        return result;
    }    
}
