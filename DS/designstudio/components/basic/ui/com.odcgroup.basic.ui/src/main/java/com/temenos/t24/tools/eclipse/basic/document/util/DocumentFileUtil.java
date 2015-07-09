package com.temenos.t24.tools.eclipse.basic.document.util;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

public class DocumentFileUtil {

    public static final String SEP = "/";
  
    public static String getProductPath(String product) {
        String filePath = getRoot() + SEP + product + SEP + product + ".xml";
        return filePath;
    }

    public static String getComponentPath(String product, String component) {
        String filePath = getRoot() + SEP + product + SEP + component + SEP + component + ".xml";
        return filePath;
    }

    public static String getSubroutinePath(String product, String component) {
        String filePath = getRoot() + SEP + product + SEP + component + SEP + component + "_Source.xml";
        return filePath;
    }

    public static String getDataFilePath(String product, String component) {
        String filePath = getRoot() + SEP + product + SEP + component + SEP + component + "_Table.xml";
        return filePath;
    }

    public static String getCobFilePath(String product, String component) {
        String filePath = getRoot() + SEP + product + SEP + component + SEP + component + "_Cob.xml";
        return filePath;
    }
    /**
	* the  method was used to get the file path of T24CompDoc
    * and find the "ComponentName_Sources.XML"
    */   
	// Mahudesh
    public static String getSourceFilePath(String product, String component) {
        String filePath = getRoot() + SEP + product + SEP + component + SEP + component +  "_Sources.xml"; 
        return filePath;
    } 
    public static String getSubroutineLookupPath() {
        String filePath = getRoot() + SEP + "subroutine-lookup.xml";
        return filePath;
    }

    public static String getProductLookupPath() {
        String filePath = getRoot() + SEP + "product-lookup.xml";
        return filePath;
    }
    
    public static String getRoot(){
        String root = EclipseUtil.getPreferenceStore().getString(PluginConstants.T24_DOCUMENT_FULLPATH).trim();
        return root;
        
    }
}
