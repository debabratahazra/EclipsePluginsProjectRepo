/*
 * 
 */
package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.zealcore.se.ui.internal.IMultiMap;

/**
 * The Class MultiHashMap is a concrete implementation of a {@link IMultiMap}.
 */
public class MultiHashMap<K, V> implements IMultiMap<K, V> {

    private final Map<K, Collection<V>> delegate = new HashMap<K, Collection<V>>();

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#clear()
     */
    public void clear() {
        this.delegate.clear();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#containsKey(K)
     */
    public boolean containsKey(final K key) {
        return this.delegate.containsKey(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#containsValue(V)
     */
    public boolean containsValue(final V value) {
        for (final Collection<V> collection : this.delegate.values()) {
            if (collection.contains(value)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#entrySet()
     */
    public Set<Entry<K, Collection<V>>> entrySet() {
        return this.delegate.entrySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#get(K)
     */
    public Collection<V> get(final K key) {
        if (this.delegate.containsKey(key)) {
            return this.delegate.get(key);
        }
        return new ArrayList<V>(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#isEmpty()
     */
    public boolean isEmpty() {
        if (this.delegate.isEmpty()) {
            return true;
        }

        for (final Collection<V> collection : this.delegate.values()) {
            if (!collection.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#keySet()
     */
    public Set<K> keySet() {
        return this.delegate.keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#put(K, V)
     */
    public boolean put(final K key, final V value) {
        if (!this.delegate.containsKey(key)) {
            this.delegate.put(key, new HashSet<V>());
        }

        return this.delegate.get(key).add(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#removeAll(K)
     */
    public void removeAll(final K key) {
        this.delegate.remove(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#remove(V)
     */
    public boolean remove(final V value) {
        boolean removed = false;
        for (final Collection<V> collection : this.delegate.values()) {
            if (collection.remove(value)) {
                removed = true;
            }
        }
        return removed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#remove(K, V)
     */
    public boolean remove(final K key, final V value) {
        return get(key).remove(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#size()
     */
    public int size() {
        int size = 0;
        for (final Collection<V> collection : this.delegate.values()) {
            size += collection.size();
        }
        return size;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.ui.internal.IMultiMap#values()
     */
    public Collection<V> values() {
        final Collection<V> values = new ArrayList<V>();
        for (final Collection<V> collection : this.delegate.values()) {
            values.addAll(collection);
        }
        return values;
    }

    public static <K, V> MultiHashMap<K, V> newInstance() {
        return new MultiHashMap<K, V>();
    }

}
