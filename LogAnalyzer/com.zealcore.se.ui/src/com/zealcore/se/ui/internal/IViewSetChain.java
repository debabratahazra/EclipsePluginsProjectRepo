/*
 * 
 */
package com.zealcore.se.ui.internal;

import com.zealcore.se.ui.ISynchable;

/**
 * The Interface IViewSetChain represents a chain of ViewSet. That is, a number
 * of ViewSets whose time-base are linked together with a given delta.
 */
interface IViewSetChain extends ISynchable {

    /**
     * Adds the view set to the current chain.
     * 
     * @param viewset
     *                the cluster to add
     */
    void addViewSet(final IViewSet viewset);

    /**
     * Removes the view set from the current chain.
     * 
     * @param viewset
     *                the cluster to remove
     */
    void removeViewSet(final IViewSet viewset);
}
