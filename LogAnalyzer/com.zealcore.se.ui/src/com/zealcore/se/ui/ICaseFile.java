package com.zealcore.se.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.zealcore.se.ui.editors.ILogSessionWrapper;

/**
 * The ICaseFile interface gives clients acccess to casefile specific routines
 * such as adding LogSessions etc.
 * 
 * This interface is NOT intended to be implemented by clients.
 * 
 * @author stch
 * 
 */
public interface ICaseFile extends IAdaptable, IWorkbenchAdapter {

    /**
     * Retrieves all wrapped LogSessions
     * 
     * @return all wrapped LogSessions
     */
    ILogSessionWrapper[] getLogs();

    /**
     * Adds a LogSession and wrapps it into a ILogSessionWrapper instance.
     * 
     * @param file
     *                the logSession to add and wrap
     * @return the wrapped LogSession
     */
    ILogSessionWrapper addLog(String name);

    /**
     * Returns the file resource that represents this CaseFile. The actual,
     * concrete returned type may vary, clients should not call this method
     * unless they really need to.
     * 
     * @return  -
     */
    IResource getResource();

    /**
     * Removes a wrapped LogSession from the casefile.
     * 
     * @param log
     */
    void removeLog(ILogSessionWrapper log);
}
