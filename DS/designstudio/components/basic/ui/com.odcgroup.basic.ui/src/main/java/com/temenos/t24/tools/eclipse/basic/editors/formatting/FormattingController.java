package com.temenos.t24.tools.eclipse.basic.editors.formatting;

import org.eclipse.jface.preference.IPreferenceStore;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

public class FormattingController {
    public static final int DEFAULT_NUMBER_SPACES_INDENTATION = 4;
    
    /**
     * Gets the indentation string (spaces) to be used. The string
     * returned looks like "   ", with as many spaces as specified
     * in a properties file.
     * @return string with indentation spaces. e.g. "   "
     */
    public static String getIndentation(){
        IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
        String indentationSpaces = store.getString(PluginConstants.T24_INDENTATION_SPACES);

        String indentation = "";
        int noSpaces;
        if(indentationSpaces  != null){
           noSpaces = Integer.parseInt(indentationSpaces );
        } else {
            noSpaces = DEFAULT_NUMBER_SPACES_INDENTATION;
        }
        for(int i=0; i<noSpaces; i++)
            indentation = indentation + " ";
        return indentation;
    }
}
