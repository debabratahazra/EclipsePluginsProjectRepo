package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.osgi.framework.Bundle;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

/**
 * Memento is used to handle persistance accross eclipse sessions. This is done
 * through storing information in an xml file. Typically this file is located
 * under %eclipse_home%\workspace\.metadata\.plugins\name.of.plugin\file.xml
 */
public class MementoUtilImpl implements MementoUtil {
    private static final String TAG_T24JBASE_PROPERTIES = "Properties";
    private static final String TAG_T24JBASE_PROPERTY = "Property";
    private static final String TAG_PROPERTY_KEY = "key";
    private static final String TAG_PROPERTY_VAL = "value";
    
    // Map holding current properties of plug-in
    private Map<String, String> curProperties = null;

    /**
     * private constructor - use a factory to instantiate this class. 
     */
    private MementoUtilImpl(){}
    
    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#deleteProperty(java.lang.String)
     */
    public void deleteProperty(String key){
        // TODO: NOTE: the following code needs to be review and changed 
        // to something more efficient
        curProperties = this.loadProperties();
        curProperties.remove(key);
        this.saveProperties(curProperties);
    }
    
    
    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#getProperty(java.lang.String)
     */
    public String getProperty(String key){
        Map globalProp  = this.loadProperties();
        return (String)globalProp.get(key);
    }
    
    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#createProperty(java.lang.String, java.lang.String)
     */
    public void createProperty(String key, String value){
        this.updateProperty(key, value);
    }    
    
    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#updateProperty(java.lang.String, java.lang.String)
     */
    public void updateProperty(String key, String value){
        // TODO: NOTE: the following code needs to be review and changed 
        // to something more efficient
        curProperties = this.loadProperties();
        curProperties.remove(key);
        curProperties.put((String)key, (String)value);
        this.saveProperties(curProperties);
    }

    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#getPropertiesFile()
     */
    public File getPropertiesFile() {
        File file = null;
        T24BasicPlugin plugin = T24BasicPlugin.getDefault();
        try {
            if (plugin != null) {
                // Invoke getStateLocation(). Returns the location in the local
                // file system of the plug-in state
                // area for this plug-in. If the plug-in state area did not
                // exist prior to this call, it is created.
                IPath stateLocation = plugin.getStateLocation();
                if (stateLocation == null) {
                    file = new File("/" + PluginConstants.t24GlobalPropertiesFile);
                } else {
                    file = stateLocation.append(PluginConstants.t24GlobalPropertiesFile).toFile();
                }
            } else {
                file = new File("/" + PluginConstants.t24GlobalPropertiesFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    
    /**
     * @param properties
     */
    private void saveProperties(Map<String, String> properties) {
        if(this.curProperties==null){
            this.curProperties = properties; 
        }
        XMLMemento memento = XMLMemento.createWriteRoot(TAG_T24JBASE_PROPERTIES);
        saveProperties(memento);
        FileWriter writer = null;
        try {
            writer = new FileWriter(getPropertiesFile());
            memento.save(writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Saves the values stored in the instance variable Map properties
     * to the plug-in file.
     * @param memento
     */
    private void saveProperties(XMLMemento memento) {
        Set keys = curProperties.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
           String key = (String)iter.next();
           IMemento child = memento.createChild(TAG_T24JBASE_PROPERTY);
           child.putString(TAG_PROPERTY_KEY, key);
           child.putString(TAG_PROPERTY_VAL, (String)curProperties.get(key));
        }
     }    
    

    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#loadProperties()
     */
    public Map<String, String> loadProperties() {
        curProperties = new HashMap<String, String>();
        FileReader reader = null;
        try {
           reader = new FileReader (getPropertiesFile());
           loadProperties(XMLMemento.createReadRoot(reader));
        }
        catch (FileNotFoundException e) {
           // Ignored... no Properties exist yet. an empty map 
           // will be returned.
        }
        catch (Exception e) {
           // Log the exception and move on.
           e.printStackTrace();
        } finally {
           try {
              if (reader != null) reader.close();
           } catch (IOException e) {
             e.printStackTrace();
           }
        }
        return curProperties;
     }


    /**
     * @param memento
     */
     private void loadProperties(XMLMemento memento) {
        IMemento [] children = memento.getChildren(TAG_T24JBASE_PROPERTY);
        for (int i = 0; i < children.length; i++) {
            curProperties.put(children[i].getString(TAG_PROPERTY_KEY), children[i].getString(TAG_PROPERTY_VAL));
        }
     }   

     /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#loadGlobalT24EnvironmentProperties()
     */
     public void loadGlobalT24EnvironmentProperties() {
             // Get the new plugin version from the MANIFEST.MF file (which
                // is always provided with plugins)
            String newVersion = this.getPluginVersion();
            
            // Check whether the properties were saved in a previous session.
            // If they were not, that would mean that this is the very first
            // session.
            File propertiesFile = getPropertiesFile();
            long length = 0L;
            if(propertiesFile!=null){
                length = propertiesFile.length(); // Length in bytes
            }
            if((propertiesFile==null) || length<1L){
                // No properties file from previous sessions, so make an initial
                // transfer
                // of properties between the original file provided with this
                // plugin and
                // a brand new state file
                firstLoadEnvironmentProperties(newVersion);
                
            } else {
                // The xml properties file was previously created. Check if it
                // is the latest
                // version. If it is not, then it needs to be updated
                String oldVersion = this.getProperty("t24.basic.editor.properties.ver");
                StringUtil su = new StringUtil();
                if((oldVersion==null) ||
                   ((newVersion!=null) && su.isNewThan(newVersion, oldVersion))){
                        updateEnvironmentProperties(newVersion);
                    }                    
            }
     }
     
     /**
      * Moves properties values from an external .properties file to an internal eclipse xml
      * file which is handled with MementoUtil. This file is used to persist these
      * properties accross sessions.
      * @param newPropertiesVersion - xx.yy.zz the plugin version as found in the plugin MANIFEST.MF file.
      * @return void
      */
     private void firstLoadEnvironmentProperties(String newPropertiesVersion) {
        InputStream is = null;
        try {
            String environmentVarFile = PluginConstants.config_dir + PluginConstants.globalPropertiesFilename;
            Properties prop = new Properties();
            is = this.getClass().getResourceAsStream(environmentVarFile);
            if(is!=null)
              prop.load(is);
            // Read properties
            curProperties = new HashMap<String, String>();
            Enumeration keys = prop.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                curProperties.put((String) key, (String) prop.getProperty(key));
            }
            saveProperties(curProperties);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(is);
        }
        // update the version
        this.updateProperty("t24.basic.editor.properties.ver", newPropertiesVersion);
    }
     
     
     /**
      * Iterates through the properties in the provided new properties file (newProps),
      * and checks whether they already exist or not in the current file (oldProps). 
      * 1) If a property from newProps doesn't exist in oldProps then add it, 
      * 2) If a property from newProps exist in oldProps then don't do anything (don't overwrite it).
      * 3) If a property from oldProps doesn't exist in newProps then don't include it in the properties.
      *  
      * @param newPropertiesVersion - xx.yy.zz the plugin version as found in the plugin MANIFEST.MF file.
      */
     private void updateEnvironmentProperties(String newPropertiesVersion) {
        InputStream is = null;
        try {
            // READ THE NEW PROPERTIES
            String environmentVarFile = PluginConstants.config_dir + PluginConstants.globalPropertiesFilename;
            Properties newProp = new Properties();
            is = this.getClass().getResourceAsStream(environmentVarFile);
            if(is!=null)
              newProp.load(is);

            // READ THE CURRENT PROPERTIES
            //Map<String, String> oldProp = this.loadProperties();
            this.loadProperties();

            // Iterate through all the new properties.
            Enumeration keys = newProp.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                
                // Check whether this property already exists in current properties file.
                if(curProperties.containsKey(key)){
                    // property already exists in current file. Don't overwrite it.
                } else {
                    // property doesn't exist in old property file, so create it.
                    curProperties.put((String) key, (String) newProp.getProperty(key));                    
                }
            }
            
            // remove the old properties which don't appear in the new properties (i.e. have been deleted)
            Iterator it = curProperties.keySet().iterator();
            ArrayList curKeysNotInNewProps = new ArrayList<String>();
            while (it.hasNext()) {
                String curKey = (String)it.next();
                if(!newProp.containsKey(curKey) && !isUserProperty(curKey)){
                    // property doesn't exist in new properties file, this means that 
                    // it has been deleted and is intended to be ignored.
                    curKeysNotInNewProps.add((String)curKey);
                }
            }
            for(int i=0; i<curKeysNotInNewProps.size(); i++){
                String key = (String)curKeysNotInNewProps.get(i);
                if(curProperties.containsKey(key)){
                    curProperties.remove(key);
                }
            }           
            
            saveProperties(curProperties);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(is);
        }
        // update the version
        this.updateProperty("t24.basic.editor.properties.ver", newPropertiesVersion);
    }     
     
     
     /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.utils.MementoUtil#isUserProperty(java.lang.String)
     */
     public boolean isUserProperty(String propKey){
         if(propKey.indexOf("t24.macro.user") != -1 ||
            propKey.indexOf("t24.template.user") != -1 || propKey.indexOf("t24.remote") != -1){
             return true;
         } else {
             return false;
         }
     }
     
    /**
     * @return the plugin version as defined in its MANIFEST.MF file (Bundle-Version)
     */
    private String getPluginVersion(){
        Bundle pluginManifestBundle = T24BasicPlugin.getDefault().getBundle();
        return (String)pluginManifestBundle.getHeaders().get("Bundle-Version");
    }
     
}
