package com.zealcore.se.ui;

/**
 * The ILogMark interface gives clients the opportunity to create timed logs. A
 * log consist of a user defined note and a long timestamp. Clients are not
 * supposed to extend this interface
 * 
 * @author stch
 */
public interface ILogMark {
    /**
     * Retrieves the marked time
     * 
     * @return the marked time
     */
    long getLogmark();

    /**
     * Retrieves the user defined note supplied to the logmark
     * 
     * @return the user define note
     */
    String getNote();
    
    /**
     * Retrieves the log file with respect to log mark.
     * 
     * @return the log file
     */
    String getLogfile();
    
    /**
     * Retrieves the event type with respect to log mark.
     * 
     * @return the event type
     */
    String getEventType();
    
    /**
     * Retrieves the created time of the log mark.
     * 
     * @return the created time 
     */
    long getCreatedTime();
    
}
