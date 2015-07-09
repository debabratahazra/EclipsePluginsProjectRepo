package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

public class RemoteSitesManagerHelper {

    private static Map<String, String> props = new HashMap<String, String>();

    /**
     * Stores the site details to xml through MementoUtil
     * 
     * @param site
     */
    public static void updateRemoteSite(RemoteSite site) {
        String macroKey = "t24.remotesite." + site.getSiteName();
        String macroValue = RemoteSiteModelController.marshal(site);
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        mu.updateProperty(macroKey, macroValue);        
    }

    /**
     * Sets newRemoteSite as default site
     * 
     * @param oldDefaultSite
     * @param newRemoteSite
     */
    public static void changeDefaultSite(RemoteSite oldDefaultSite, RemoteSite newRemoteSite) {
        if (oldDefaultSite != null) {
            oldDefaultSite.setDefault(false);
            updateRemoteSite(oldDefaultSite);
        }
        if(newRemoteSite != null){
            newRemoteSite.setDefault(true);
            updateRemoteSite(newRemoteSite);
        }
    }

    /**
     * Delets the site details from xml
     * 
     * @param site
     */
    public static void deleteRemoteSite(String siteName) {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        String keyIndex = "t24.remotesite.";
        String key = keyIndex + siteName;
        mu.deleteProperty(key);
    }

    public static Set<String> getRemoteSiteKeys() {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        if (props.size() <= 0) {
            props = mu.loadProperties();
        }
        Set<String> keys = props.keySet();
        return keys;
    }

    public static RemoteSite getRemoteSite(String key) {
        String remoteSiteData = (String) props.get(key);
        return RemoteSiteModelController.unmarshal(remoteSiteData);
    }

    }
