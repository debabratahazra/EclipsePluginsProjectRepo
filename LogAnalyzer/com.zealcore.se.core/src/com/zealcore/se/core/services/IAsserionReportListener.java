/**
 * 
 */
package com.zealcore.se.core.services;

/**
 * The listener interface for receiving asserion report events. The class that
 * is interested in processing a asserion report event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's <code>addAsserionReportListener<code> method.
 * 
 * When the AsserionReport occurs, that object's <code>reportEvent</code> method is invoked.
 * 
 * @see IAssertionReportEvent
 */
public interface IAsserionReportListener {

    /**
     * Invoked when report event occurs.
     * 
     * @param event
     *                the event
     */
    void reportEvent(IAssertionReportEvent event);
    
    void clearEvent();

}
