package com.zealcore.se.core.model.generic;

import java.util.Map;

import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;

/**
 * s All implementors of {@link IGenericLogItem} should also extend
 * {@link com.zealcore.se.core.model.IObject}
 * 
 * @author stch
 * 
 */
public interface IGenericLogItem {

    /**
     * Adds a property to this generic log item. The value must be any of the
     * primitives or their object representative, a String or a
     * {@link com.zealcore.se.core.model.IObject}.
     * 
     * @param key
     * @param value
     */
    void addProperty(String key, Object value);

    /**
     * Gets a property by name
     * 
     * @param key
     *            the name of the property
     * @return the property with the specified key
     */
    Object getProperty(String key);

    /**
     * This method is not intended to be used by clients
     * 
     * @return -
     */
    Map<String, Object> properties();

    /**
     * Returns the typename of this {@link IGenericLogItem}
     * 
     * @return -
     */
    String getTypeName();

    /**
     * Sets the typename of this {@link IGenericLogItem}
     */
    void setTypeName(String typeName);

    IType getType();

    void setLogFile(final LogFile logFile);
}
