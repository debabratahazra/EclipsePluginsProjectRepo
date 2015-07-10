/*
 * 
 */
package com.zealcore.se.ui.internal;

import com.zealcore.se.ui.editors.ILogSessionWrapper;

/**
 * The Interface ILogSet is package-wide and more specialized interface than the
 * {@link ILogSessionWrapper}.
 */
interface ILogSet extends ILogSessionWrapper {

    /**
     * {@inheritDoc}. Interface implementors are required to return a more
     * specialized ICaseFile implementation.
     */
    ICaseFile2 getCaseFile();
    
    void setCurrentTime(long time);
}
