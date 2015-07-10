package com.zealcore.se.core.dl;

import java.util.Collection;

import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IType;

public interface ITypePackage {
    Collection<IType> getTypes();

    /**
     * Is invoked for each event that is created by setLogfile.
     * @see com.zealcore.se.core.IImporter
     * @param abstractLogEvent
     */
    void processLogEvent(ILogEvent abstractLogEvent);

    void addLogsessionItemListener(ILogSessionItemListener listener);

    /**
     * Is invoked before processing log events for a part of the log set
     */
    void begin();

    /**
     * Is invoked after processing log events for a part of the log set
     */
    void end();

    /**
     * Is invoked before processing log events for the entire log set
     */
    void beginFull();

    /**
     * Is invoked after processing log events for the entire log set
     */
    void endFull();

    void removeLogsessionItemListener(ILogSessionItemListener listener);
}
