package com.temenos.t24.tools.eclipse.basic.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.util.T24FilesStream;

public class HelpCache {

    private final static HelpCache HELP_CACHE = new HelpCache();
    private Map<String, String> helpMap = new HashMap<String, String>();
    /** File stream for different contexts - real and mock */
    private static T24FilesStream t24FileStream;
    /** Keywords list */
    private static List<String> keywords = new ArrayList<String>();

    private HelpCache() {
        t24FileStream = (T24FilesStream) T24BasicPlugin.getSpringApplicationContext().getBean("t24SourceStream");
    }

    public static HelpCache getInstance() {
        loadKeywordsList();
        return HELP_CACHE;
    }

    /**
     * Loads Help text from the properties file into a local eclipse xml
     * managed through eclipse's Memento framework
     */
    public void loadHelpProperties() throws IOException {
        // Load properties file
        InputStream is = null;
        try {
            Properties prop = new Properties();
            String keywordsHelpFile = PluginConstants.config_dir + PluginConstants.basicKeywordsHelpFilename;
            is = HelpCache.class.getResourceAsStream(keywordsHelpFile);
            if (is != null) {
                prop.load(is);
            }
            Enumeration<Object> keys = prop.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                helpMap.put((String) key, (String) prop.getProperty(key));
            }
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * Loads the keywords into a List
     */
    private static void loadKeywordsList() {
        InputStream is = null;
        try {
            is = t24FileStream.getKeywordsFileStream();
            InputStreamReader isr = null;
            BufferedReader br = null;
            if (is != null) {
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String keyword = br.readLine();
                while (keyword != null) {
                    keywords.add(keyword);
                    keyword = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * @param word
     * @return - the text string with help or NULL if no help was found
     */
    public String getHelpTextFor(String word) {
        return (String) helpMap.get(word);
    }

    /**
     * Returns the list of Keywords 
     * 
     * @return Keywords list
     */
    public List<String> getKeywordList() {
        return keywords;
    }
}
