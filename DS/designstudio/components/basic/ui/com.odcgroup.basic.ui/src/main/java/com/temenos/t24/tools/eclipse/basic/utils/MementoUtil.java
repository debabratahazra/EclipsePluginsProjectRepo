package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.File;
import java.util.Map;

public interface MementoUtil {

    public void deleteProperty(String key);

    /**
     * Returns the value associated to the property key passed. 
     * @param key - a property key, e.g. "t24.remote.server.directory" 
     * @return String - property value.
     */
    public String getProperty(String key);

    /**
     * Creates a new property in the plug-in persistence file.
     * It'll look like:
     * <Properties>
     *   <Property key="property_key" value="property_value"/>
     * </Properties>
     * @param key
     * @param value
     */
    public void createProperty(String key, String value);

    /**
     * Updates the property, passed as parameter, in a file used internally by the 
     * plugin as a persistence storage.
     * @param key - property name (e.g. t24.remote.server.directory)
     * @param value - content of the property
     */
    public void updateProperty(String key, String value);

    /**
     * Retrieve the xml file holding the persisted state
     * @return File - File pointing to where the properties are stored
     */
    public File getPropertiesFile();

    public Map<String, String> loadProperties();

    /**
     * Loads Properties from the properties file provided with the plugin
     * into an internal local eclipse xml file managed through eclipse's 
     * Memento framework. This method is intended to be called just once 
     * when the plugin starts.
     */
    public void loadGlobalT24EnvironmentProperties();

    /**
     * Finds out if the passed property key was created by the User. This 
     * happens typically in macros or templates, where users can create their own.
     * @param propKey - string holding the property entry key (e.g. t24.macro.user.MACRO_NAME)
     * @return true/false
     */
    public boolean isUserProperty(String propKey);
}
