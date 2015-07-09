package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class MockMementoUtilImpl implements MementoUtil {
    
    public static Map<String, String> props = new HashMap<String, String>();
    
    private MockMementoUtilImpl(){        
    }

    public void createProperty(String key, String value) {
        props.put(key, value);
    }

    public void deleteProperty(String key) {
        if(props.containsKey(key)){
            props.remove(key);
        }
    }

    public File getPropertiesFile() {
        return null;
    }

    public String getProperty(String key) {
        if(props.containsKey(key)){
            return props.get(key);
        } else {
            return null;
        }
    }

    public boolean isUserProperty(String propKey) {
        return false;
    }

    public void loadGlobalT24EnvironmentProperties() {
    }

    public Map<String, String> loadProperties() {
        return null;
    }

    public void updateProperty(String key, String value) {
        if(props.containsKey(key)){
            props.remove(key);
            props.put(key, value);
        } 
    }
}
