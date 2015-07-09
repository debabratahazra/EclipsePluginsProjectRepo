package com.temenos.t24.tools.eclipse.basic.testutilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MiscUtilities {
    /******************************************************************************
     * Utility class.
     * @param filename
     * @return String - text
     */
    public String getFileContents(String filename) {
        StringBuffer sb = new StringBuffer();
        String contents = "";
        BufferedReader br = null;
        try {
            InputStream is = this.getClass().getResourceAsStream("/" + filename);
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }           
            // remove the last "new line"
            contents = sb.toString();
            contents = contents.substring(0, contents.length()-2);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        if (contents == null)
            return "";
        else
            return contents;
    }    
}
