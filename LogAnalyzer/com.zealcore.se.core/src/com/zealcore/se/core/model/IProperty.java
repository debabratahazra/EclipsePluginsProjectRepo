package com.zealcore.se.core.model;

/**
 * 
 * @author mala
 * 
 */
public interface IProperty {

    String getName();

    String getDescription();

    boolean isSearchable();

    boolean isPlotable();

    boolean isGeneric();

    /**
     * The getValue is a property accessor on an object. A IProperty accessor is
     * only valid if they hail from the same type.
     * 
     * @param object
     * @return the objects value.
     */
    Object getValue(IObject object);

    /**
     * 
     * EXPERIMENTAL
     * 
     * Get the propertys return type. It is important to note that this method
     * return Class rather than an instance of IType. This is because primitives
     * are not wrapped as IType.
     * 
     * 
     * @return the return type
     */
    @SuppressWarnings("unchecked")
    Class getReturnType();
}
