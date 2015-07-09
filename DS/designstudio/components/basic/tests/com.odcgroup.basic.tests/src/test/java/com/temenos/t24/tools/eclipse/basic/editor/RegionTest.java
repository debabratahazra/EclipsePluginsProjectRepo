package com.temenos.t24.tools.eclipse.basic.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.editors.regions.RegionUtil;

public class RegionTest {

    private static final String REGIONS_TEST_FILE_1   = "testfiles\\regions_test_1.txt";
    
    @Test
	public void testGetDescription(){
        RegionUtil ru = new RegionUtil();
        String desc = "Test decription";
        String block = ru.buildDescription(desc);
        //System.out.println(block);
        Assert.assertTrue("Test decription ".equals(block));
        
        desc = "LINE1\r\nLINE2";
        //System.out.println(ru.buildDescription(desc));
    }
    
    @Test
	public void testRegions(){
        RegionUtil ru = new RegionUtil();
        String contents = this.getFileContents(REGIONS_TEST_FILE_1);
        ru.findRegions(contents);
    }
    
    @Test
	public void testRegions2(){
        String contents = this.getFileContents("testfiles\\TEMPLATE.txt");
        String[] r = contents.split("\\r\\n");
        StringBuffer sb = new StringBuffer();
        for(int i= 0; i<r.length; i++){
            sb.append(r[i]+"\r\n");
        }
    }    
    
    
    /**
     * @param filename
     * @return String - text
     */
    private String getFileContents(String filename) {
        StringBuffer sb = new StringBuffer();
        String contents;
        BufferedReader br = null;
        try {
            InputStream is = this.getClass().getResourceAsStream("/" + filename);
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        contents = sb.toString();
        if (contents == null)
            return "";
        else
            return contents;
    }    
    
}
