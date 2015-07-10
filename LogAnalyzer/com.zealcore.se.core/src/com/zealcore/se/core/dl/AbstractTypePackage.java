package com.zealcore.se.core.dl;

import java.util.HashSet;
import java.util.Set;

import com.zealcore.se.core.model.IObject;

public abstract class AbstractTypePackage implements ITypePackage {

    private final Set<ILogSessionItemListener> logsessionItemListeners = new HashSet<ILogSessionItemListener>();

    /**
     * Is invoked before processing log events for a part of the log set
     */
    public void begin() {}

    /**
     * Is invoked after processing log events for a part of the log set
     */
    public void end() {}

    /**
     * Is invoked before processing log events for the entire log set. This
     * implementation simply invokes begin()
     */
    public void beginFull() {
        begin();
    }

    /**
     * Is invoked after processing log events for the entire log set. This
     * implementation simply invokes end()
     */
    public void endFull() {
        end();
    }

    public final void addLogsessionItemListener(
            final ILogSessionItemListener listener) {
        this.logsessionItemListeners.add(listener);
    }

    protected final void notifyListeners(final IObject item) {
        for (final ILogSessionItemListener listener : this.logsessionItemListeners) {
            listener.logFileItemCreated(item);
        }
    }

    public final void removeLogsessionItemListener(
            final ILogSessionItemListener listener) {
        this.logsessionItemListeners.remove(listener);
    }

    protected final Set<ILogSessionItemListener> getLogsessionItemListeners() {
        return this.logsessionItemListeners;
    }

}
