/*
 * 
 */
package com.zealcore.se.ui.internal;

import org.eclipse.ui.IMemento;
import com.zealcore.se.ui.ILogMark;

public class LogMark implements ILogMark {

    private static final String CREATED_AT_NODE = "created";

    private static final String NOTE_NODE = "note";

    private static final String LOGMARK_NODE = "logmark";
    
    private static final String TYPE_AT_NODE = "type";
    
    private static final String LOGFILE_AT_NODE = "logfile";

    private final long logmark;

    private final String note;

    private final long created;
    
    private final String logFile;
    
    private final String eventType;
    
    /**
     * Creates a new LogMark
     * 
     * @param logmark
     *                the long value of the logmarked time.
     * @param note
     *                a description of the logmark
     * @param created
     *                the time this logmark was created.
     */
    LogMark(final long logmark, final String note, final long created , 
    		final String logFile, final String eventType) {
        super();
        this.logmark = logmark;
        this.note = note;
        this.created = created;
        this.logFile = logFile;
        this.eventType = eventType;
    }

    /**
     * Creates a new LogMark representing the data in the memento.
     * 
     * @param memento
     *                the memento
     * 
     * @return the log mark
     */
    static LogMark valueOf(final IMemento memento) {
        final String logMark = memento.getString(LogMark.LOGMARK_NODE);
        final String note = memento.getString(LogMark.NOTE_NODE);
        final String created = memento.getString(LogMark.CREATED_AT_NODE);
        final String logFile = memento.getString(LogMark.LOGFILE_AT_NODE);
        final String eventType = memento.getString(LogMark.TYPE_AT_NODE);

        final long bmt = Long.valueOf(logMark).longValue();
        final long crt = Long.valueOf(created).longValue();

        return new LogMark(bmt, note, crt, logFile, eventType);
    }

    /**
     * Saves the state to a memento
     * 
     * @param memento
     *                the memento
     */
    void saveState(final IMemento memento) {
        memento.putString(LogMark.LOGMARK_NODE, Long.toString(this.logmark));
        memento.putString(LogMark.NOTE_NODE, this.note);
        memento.putString(LogMark.CREATED_AT_NODE, Long.toString(this.created));
        memento.putString(LogMark.LOGFILE_AT_NODE, this.logFile);
        memento.putString(LogMark.TYPE_AT_NODE, this.eventType);
    }
    
    /**
     * {@inheritDoc}
     */
    public long getLogmark() {
        return this.logmark;
    }

    /**
     * {@inheritDoc}
     */
    public String getNote() {
        return this.note;
    }

    /**
     * {@inheritDoc}
     */
    public String getLogfile() {
        return this.logFile;
    }

    /**
     * {@inheritDoc}
     */
    public String getEventType() {
        return this.eventType;
    }

    /**
     * {@inheritDoc}
     */
    public long getCreatedTime() {
        return this.created;
    }
}
