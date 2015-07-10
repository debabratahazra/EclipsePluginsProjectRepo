package com.zealcore.se.core.ifw;

import com.zealcore.se.core.model.IObject;

public interface IQuery {

    /**
     * Sets the {@link com.zealcore.se.core.ifw.IDataSource} which should be used throughout the lifetime
     * of this IQuery
     * 
     * @param data
     */
    void initialize(final IDataSource data);

    /**
     * Let the query visit an element and return true of false if the query want
     * more objects
     * 
     * @param item
     *                the item to inspect
     * @return false if the query do not want more items, true otherwise
     */
    boolean visit(final IObject item);

    /**
     * Called prior to any visit to notify the query that a new data will be
     * added. This can be used to clear a cache or similar
     */
    boolean visitBegin(final Reason reason);

    /**
     * Called on queries at the end of an import {@link #visit(IObject)}
     * 
     * @param atEnd
     *                if this query were active for all events in the logset
     */
    void visitEnd(final boolean atEnd);

    void setLogset(final Logset logset);
    
    Logset getLogset();
    
    void setStart(final boolean start);

    void setEnd(final boolean end);
}
