/*
 * 
 */
package com.zealcore.se.ui;

import java.util.Set;
import java.util.Map.Entry;

import com.zealcore.se.core.ifw.ITimeProvider;
import com.zealcore.se.ui.editors.ILogSessionWrapper;

/**
 * The ITimeCluster provides clients with routines to interact with
 * timeclusters. TimeClusters are typically an item of wrapped logsessions and
 * casefiles.
 * 
 * This interface is NOT intended to be implemented by clients
 * 
 * @author stch
 */
public interface ITimeCluster extends ITimeProvider {

    int getId();

    /**
     * Gets the unique id for this timecluster.
     * 
     * @return the unique id for this timecluster
     */
    String getUid();

    /**
     * Returns the current time that was set for this timecluster. Clients
     * should not that the time does not necessariliy have to be >= geetMin()
     * and <= getMax(). If this is the case, clients should note that the
     * current time is invalid for the timecluster.
     * 
     * @see ITimeCluster#setCurrentTime(long)
     * @return the current time
     */
    long getCurrentTime();

    /**
     * sets the current time to time
     * 
     * @param time
     *                the time to set
     */
    void setCurrentTime(long time);

    /**
     * Gets the parent ILogSessionWrapper
     * 
     * @return the parent
     */
    ILogSessionWrapper getParent();

    /**
     * Adds a new synchable to the timecluster
     * 
     * @param synchable
     * @return -
     */
    boolean addSynchable(ISynchable synchable);

    /**
     * Tries to add this cluster(ViewSet) to a dedicated chain.
     * 
     * @return true, if chain was successfull
     */
    boolean chain();

    /**
     * Removes the ViewSet from the chain
     * 
     * @return true, if un chain was successfull
     */
    boolean unChain();

    /**
     * Checks if is chained.
     * 
     * @return true, if is chained
     */
    boolean isChained();

    /**
     * Logmarks the current time with a specific note.
     * 
     * @param note
     *                the note
     * 
     * @return the I log mark
     */
    ILogMark logmark(String note);

    /**
     * Gets the logmarks defined in this Viewset.
     * 
     * @return the logmarks
     */
    ILogMark[] getLogmarks();

    /**
     * Removes the synchable.
     * 
     * @param synchable
     *                the synchable to remove
     * 
     * @return true, if remove synchable is successful
     */
    boolean removeSynchable(ISynchable synchable);

    /**
     * Gets the min timestamp for this ViewSet.
     * 
     * @return the min
     * 
     */
    long getMin();

    /**
     * Gets the max timestamp for this ViewSet.
     * 
     * @return the max
     * 
     */
    long getMax();

    /**
     * Removes the logmark.
     * 
     * @param bm
     *                the logmark to remove
     */
    void removeLogmark(ILogMark bm);

    /**
     * Gets the name of thie ViewSet.
     * 
     * @return the name
     */
    String getName();

    void addLogMark(ILogMark mark);
    
    void setSynchronizationOffset(final String offsetWith, final long offset);
    
    long getSynchronizationOffset(final String offsetWith);
    
    Set<Entry<String, Long>> getSynchronizationOffsets();
    
    Object removeOffset(String name);
    
    void removeAllOffsets();
    
    long getPrimarySynchronizedEventTime();
}
