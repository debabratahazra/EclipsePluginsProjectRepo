package com.zealcore.se.core.model;

import java.util.Collection;

/**
 * 
 * @author mala
 * 
 */
public interface IType {

    String getName();

    Collection<IProperty> getProperties();

    /**
     * Gets the unique String identifier, the exact format is unspecified but
     * must be guaranteed to be consistent across sessions.
     * 
     * @return the id
     */
    String getId();

    boolean isA(IType type);

    boolean isSearchable();

    boolean isPlottable();
}
