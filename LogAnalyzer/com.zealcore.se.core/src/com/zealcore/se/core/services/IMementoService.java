/*
 * 
 */
package com.zealcore.se.core.services;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;

/**
 * The Interface IMementoService.
 */
public interface IMementoService {

    /**
     * Creates a write root.
     * 
     * @param root
     *                the root
     * @param path
     *                the path
     * 
     * @return the I memento2
     */
    IMemento2 createWriteRoot(final String root, final IPath path);

    /**
     * Create read root.
     * 
     * @param path
     *                the path
     * 
     * @return the I memento
     * 
     * @throws FileNotFoundException
     *                 the file not found exception
     */
    IMemento createReadRoot(final IPath path) throws FileNotFoundException,
            WorkbenchException;
}
