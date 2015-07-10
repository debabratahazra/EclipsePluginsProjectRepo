/*
 * 
 */
package com.zealcore.se.ui.internal;

import com.zealcore.se.ui.ITimeCluster;

/**
 * The Interface IViewSet is a more specialized version of the
 * {@link ITimeCluster}.
 */
interface IViewSet extends ITimeCluster {

    /**
     * {@inheritDoc}. Requires implementors to return a more specialized form
     * of {@link ILogSessionWrapper}
     * 
     */
    ILogSet getParent();
}
