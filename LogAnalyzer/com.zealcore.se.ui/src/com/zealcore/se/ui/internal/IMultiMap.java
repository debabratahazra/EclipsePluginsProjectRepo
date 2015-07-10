/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.Collection;
import java.util.Set;
import java.util.Map.Entry;

/**
 * The Interface for IMultiMap instance. a MultiMap is a normal map where the
 * value is a collection of values rather than a single value.
 */
public interface IMultiMap<K, V> {

    /**
     * Clears the contents of the multimap.
     */
    void clear();

    /**
     * Checks if the multimap contains the given key.
     * 
     * @param key
     *                the key
     * 
     * @return true, if key contains key
     */
    boolean containsKey(final K key);

    /**
     * Checks if the multimap contains the specified value.
     * 
     * @param value
     *                the value
     * 
     * @return true, if contains value
     */
    boolean containsValue(final V value);

    /**
     * Returns the entry set
     * 
     * @return the entry set
     */
    Set<Entry<K, Collection<V>>> entrySet();

    /**
     * Gets the collection of values mapped to the given key.
     * 
     * @param key
     *                the key
     * 
     * @return the collection< v>
     */
    Collection<V> get(final K key);

    /**
     * Checks if is empty.
     * 
     * @return true, if is empty
     */
    boolean isEmpty();

    /**
     * Returns the key set.
     * 
     * @return the set
     */
    Set<K> keySet();

    boolean put(final K key, final V value);

    /**
     * Removes all entry for a given key.
     * 
     * @param key
     *                the key to remove
     */
    void removeAll(final K key);

    /**
     * Removes the specified value from all key-entries.
     * 
     * @param value
     *                the value to remove
     * 
     * @return true, if remove is successful
     */
    boolean remove(final V value);

    /**
     * Removes the value from the valueset with the specified key.
     * 
     * @param key
     *                the key
     * @param value
     *                the value
     * 
     * @return true, if remove is successful
     */
    boolean remove(final K key, final V value);

    /**
     * Returns the size of the multimap.
     * 
     * @return the int
     */
    int size();

    /**
     * Returns all values for the multimap. This method does not guarantee that
     * each value V is unique, there is no Set.
     * 
     * @return the collection< v>
     */
    Collection<V> values();

}
