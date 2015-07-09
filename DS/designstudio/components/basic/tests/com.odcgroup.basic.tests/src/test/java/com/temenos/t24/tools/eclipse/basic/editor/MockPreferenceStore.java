package com.temenos.t24.tools.eclipse.basic.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;

public class MockPreferenceStore implements IPreferenceStore {

    private static Map<String, Object> propertyMap = new HashMap<String, Object>();
    static {
        propertyMap.put(PluginConstants.T24_INDENTATION_SPACES, "4");
        propertyMap.put(PluginConstants.T24_LOCAL_USERNAME, "TestLocalUsername");
    }

    public void addPropertyChangeListener(IPropertyChangeListener listener) {
        // TODO Auto-generated method stub
    }

    public boolean contains(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
        // TODO Auto-generated method stub
    }

    public boolean getBoolean(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean getDefaultBoolean(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public double getDefaultDouble(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    public float getDefaultFloat(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getDefaultInt(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    public long getDefaultLong(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getDefaultString(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public double getDouble(String name) {
        return (Double) propertyMap.get(name);
    }

    public float getFloat(String name) {
        return (Float) propertyMap.get(name);
    }

    public int getInt(String name) {
        return (Integer) propertyMap.get(name);
    }

    public long getLong(String name) {
        return (Long) propertyMap.get(name);
    }

    public String getString(String name) {
        return (String) propertyMap.get(name);
    }

    public boolean isDefault(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean needsSaving() {
        // TODO Auto-generated method stub
        return false;
    }

    public void putValue(String name, String value) {
        // TODO Auto-generated method stub
    }

    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        // TODO Auto-generated method stub
    }

    public void setDefault(String name, double value) {
        // TODO Auto-generated method stub
    }

    public void setDefault(String name, float value) {
        // TODO Auto-generated method stub
    }

    public void setDefault(String name, int value) {
        // TODO Auto-generated method stub
    }

    public void setDefault(String name, long value) {
        // TODO Auto-generated method stub
    }

    public void setDefault(String name, String defaultObject) {
        // TODO Auto-generated method stub
    }

    public void setDefault(String name, boolean value) {
        // TODO Auto-generated method stub
    }

    public void setToDefault(String name) {
        // TODO Auto-generated method stub
    }

    public void setValue(String name, double value) {
        propertyMap.put(name, value);
    }

    public void setValue(String name, float value) {
        propertyMap.put(name, value);
    }

    public void setValue(String name, int value) {
        propertyMap.put(name, value);
    }

    public void setValue(String name, long value) {
        propertyMap.put(name, value);
    }

    public void setValue(String name, String value) {
        propertyMap.put(name, value);
    }

    public void setValue(String name, boolean value) {
        propertyMap.put(name, value);
    }
}
