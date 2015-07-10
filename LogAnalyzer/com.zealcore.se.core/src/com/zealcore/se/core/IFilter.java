package com.zealcore.se.core;


/**
 * 
 * NOTE The toString implementation should preferably return the name
 * 
 * This interface is experimental and may change in the near future
 * 
 * @version 1.0
 * @author Stefan Chyssler
 * @since 1.0
 */
public interface IFilter<T> {

    /**
     * If this method returns false, clients should filter out the element.
     * 
     * IMPORTANT: This method must not throw exceptions, thus beware of
     * class-casting. Always check with instanceof or similar.
     * 
     * @param x
     *                the type to test
     * @return true if element makes it through the filter
     */
    boolean filter(T x);

}
