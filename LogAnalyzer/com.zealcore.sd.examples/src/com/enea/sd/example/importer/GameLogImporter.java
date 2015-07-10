package com.enea.sd.example.importer;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.model.generic.GenericLogFile;
import com.zealcore.se.core.model.generic.GenericReceiveEvent;
import com.zealcore.se.core.model.generic.GenericSendEvent;
import com.zealcore.se.core.model.generic.GenericSequence;
import com.zealcore.se.core.model.generic.GenericTask;
import com.zealcore.se.core.model.generic.GenericTaskSwitchEvent;
import com.zealcore.se.core.util.TimestampUtil;

/**
 * The SimpleImporter is an example importer that can be used together with Log
 * Analyzer to import log files of a specified format.<br>
 * 
 * The importer is able to create task/process switches and sequences that are
 * needed to populate the Gantt and Sequence diagrams. There are still more
 * properties that can be implemented to fulfill the log events properties. A
 * few examples are;<br>
 * 1) send events does not display at what time stamp the message is received.<br>
 * 2) receive events does not display at what time stamp the message was sent.<br>
 * It is up to the implementer to create the mechanisms that match send and
 * receives.<br>
 * <br>
 * 
 * The log file format is on the following format:<br>
 * YYYY-MM-DD hh:mm:ss:us MESSAGE_TYPE <paramters><br>
 * * YYYY = year<br>
 * * MM = month<br>
 * * DD = day<br>
 * * hh = hour<br>
 * * mm = minute<br>
 * * ss = second<br>
 * * us = micro second<br>
 * * MESSAGE_TYPE and MESSAGE_PARAMETERS = The type of the message and its
 * parameters that are needed. The message type can be one of the following:<br>
 * -- SYSTEM_INFO &lt;message&gt; = An informative message. The message can be <br>
 * -- TASK_SWITCH &lt;out task&gt; &lt;in task&gt; = Indicates that the current
 * task/process is switched out and another is switched in.<br>
 * -- SEND &lt;sender&gt; &lt;receiver&gt; &lt;message&gt; = A message is sent
 * from one object/task/process to another.<br>
 * -- RECEIVE &lt;sender&gt; &lt;receiver&gt; &lt;message&gt; = A message is
 * received from one object/task/process.<br>
 * <br>
 * <br>
 * The following is an example log file that can be imported by the
 * SimpleImporter. Copy the content and save it as a text file called simple.log<br>
 * <br>
 * ####### Example Log File Begin #######<br>
 * #Enea Example Log<br>
 * #Version 1.1<br>
 * <br>
 * 
 * 2007-03-28 13:56:00:900004 SYSTEM_INFO Temperature is OK!<br>
 * 2007-03-28 13:56:00:900013 TASK_SWITCH system engine_unit<br>
 * 2007-03-28 13:56:00:900102 SEND engine_unit control_unit RPM limit reached<br>
 * 2007-03-28 13:56:00:900106 TASK_SWITCH engine_unit control_unit<br>
 * 2007-03-28 13:56:00:900237 RECEIVE control_unit RPM limit reached<br>
 * 2007-03-28 13:56:00:900213 TASK_SWITCH control_unit system<br>
 * 2007-03-28 13:56:00:900307 SYSTEM_INFO Adjusting parameters to decrease
 * temperature<br>
 * 2007-03-28 13:56:00:900319 SYSTEM_INFO Should not reach this state<br>
 * 2007-03-28 13:56:00:900391 TASK_SWITCH system control_unit<br>
 * 2007-03-28 13:56:00:900413 SEND control_unit engine_unit Error, stop engine<br>
 * 2007-03-28 13:56:00:900472 TASK_SWITCH control_unit system<br>
 * 2007-03-28 13:56:00:900510 SYSTEM_INFO Temperature is rising<br>
 * 2007-03-28 13:56:00:900562 TASK_SWITCH system engine_unit<br>
 * 2007-03-28 13:56:00:900600 RECEIVE engine_unit Error, stop engine<br>
 * 2007-03-28 13:56:00:900622 TASK_SWITCH engine_unit system<br>
 * 2007-03-28 13:56:00:900746 SYSTEM_INFO warning, system in fail state!<br>
 * 2007-03-28 13:56:00:901239 SYSTEM_INFO System restart is ordered from safety
 * mechanism!<br>
 * ####### Example Log File End ####### <br>
 * <br>
 * 
 * 
 * @author cafa
 * 
 */
public class GameLogImporter implements IImporter, Closeable {

//    private static final String SYSTEM_INFO = "SYSTEM_INFO";
//
//    private static final String TASK_SWITCH = "TASK_SWITCH";
//
//    private static final String RECEIVE = "RECEIVE";
//
//    private static final String SEND = "SEND";

    private static final String NUMBER = "\\d+";

    private static final String WS = "\\s+";

    private static final String WILD_CARD = ".+";

    private static final String REGEXP = "(" + NUMBER + ")-(" + NUMBER + ")-("
            + NUMBER + ")" + WS + "(" + NUMBER + "):(" + NUMBER + "):("
            + NUMBER + ")" + WS + "(" + WILD_CARD + ")";

    private static final String SYSTEM_INFO_PARAMS = "([A-Za-z\\d\\D]+)";

    private static final String SEND_PARAMS = "([A-Za-z_]+)" + WS
            + "([A-Za-z_]+)" + WS + "(.*)";

    private static final String TASKSWITCH_PARAMS = "([A-Za-z_\\d]+)" + WS
            + "([A-Za-z_\\d]+)";

    private static final String RECEIVE_PARAMS = "([A-Za-z_]+)" + WS + "("
            + WILD_CARD + ")";

    private BufferedReader logFileReader;

    private ImportContext importContext;

    private GenericLogFile logfile;

    private File file;

    private Map<String, GenericTask> artifacts = new HashMap<String, GenericTask>();

    private GenericSequence sequence;

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#canRead(java.io.File)
     */
    public boolean canRead(final File log) {
        boolean retValue = false;
        if (log.canRead()) {

            try {
                logFileReader = new BufferedReader(new FileReader(log));
            } catch (FileNotFoundException e) {
                return false;
            }
            try {
                String readLine = logFileReader.readLine();
                Scanner s = new Scanner(readLine);
                s.findInLine(REGEXP);

                retValue = true;
                try {
                    s.match();
                } catch (final IllegalStateException e) {
                    retValue = false;
                }
            } catch (IOException e) {
                retValue = false;
            }
        }
        try {
            if (logFileReader != null) {
                logFileReader.close();
            }
        } catch (IOException e) {
            retValue = false;
        }
        return retValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#setContext(ImportContext)
     */
    public void setContext(final ImportContext context) {
        if (importContext != null) {
            throw new IllegalStateException("Can't import log twice!");
        }
        importContext = context;

        if (file != null) {
            throw new IllegalStateException(
                    "setContext should only be called once");
        }

        file = importContext.getFile();
        file = context.getFile();
        if (!canRead(file)) {
            throw new ImportException("Invalid file format. " + toString()
                    + " can't read the file: " + file.getName() + " on path: "
                    + file.getAbsolutePath());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getArtifacts()
     */
    public Iterable<IArtifact> getArtifacts() {
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getLogEvents()
     */
    public Iterable<ILogEvent> getLogEvents() {
        try {
            initialize();
        } catch (IOException e) {
            throw new ImportException("Failed to read events from file: "
                    + this.importContext.getFile().getAbsolutePath(), e);
        }

        return new Iterable<ILogEvent>() {
            public Iterator<ILogEvent> iterator() {
                return new StringElementIterator();
            }
        };
    }

    /**
     * Sets up the importer before it can start reading log events.<br>
     * 
     * @throws IOException
     */
    private void initialize() throws IOException {
        logFileReader = new BufferedReader(new FileReader(file));

        logfile = new GenericLogFile();
        logfile.setFileName(importContext.getFile().getName());
        logfile.setImporterId(this.getClass().getName());
        logfile.setImportedAt(System.currentTimeMillis());
        logfile.setVersion(1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#size()
     */
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "Simple Importer (c) Enea 2008";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException {
        if (logFileReader != null) {
            logFileReader.close();
        }
    }

    /**
     * Creates a log event from the given string. A log event always have a time
     * stamp, a reference to its log file and a log event type. Any number of
     * properties can be set to a generic log event using the addPropery method.
     * 
     * @param readLine
     *            string with event data
     * @return the created log event.
     */
    public AbstractLogEvent createEventFromString(final String readLine) {
        AbstractLogEvent event = null;
        Scanner s = new Scanner(readLine);
        s.findInLine(REGEXP);

        MatchResult result;
        try {
            result = s.match();
        } catch (final IllegalStateException e) {
            throw new ImportException("Unable to parse: " + readLine
                    + ", with regular expression" + REGEXP, e);
        }

        TimestampUtil tsu = new TimestampUtil();
        tsu.setYear(result.group(1));
        tsu.setMonth(result.group(2));
        tsu.setDay(result.group(3));
        tsu.setHour(result.group(4));
        tsu.setMinute(result.group(5));
        tsu.setSecond(result.group(6));

        event = createMessageEvent(result.group(7), tsu);

        // if (SYSTEM_INFO.equals(eventName)) {
        // String params = result.group(9);
        // event = createMessageEvent(params, tsu);
        // } else if (TASK_SWITCH.equals(eventName)) {
        // String params = result.group(9);
        // event = createTaskSwitchEvent(params, tsu);
        // } else if (SEND.equals(eventName)) {
        // String params = result.group(9);
        // event = createSendEvent(params, tsu);
        // } else if (RECEIVE.equals(eventName)) {
        // String params = result.group(9);
        // event = createReceiveEvent(params, tsu);
        // }

        return event;
    }

    /**
     * Creates a generic log event where the log event type (that is shown in
     * the GUI) is set to "LogMessage".<br>
     * <br>
     * 
     * @param params
     *            contains the parameters to the log event that is returned.
     * @param tsu
     *            is the time stamp.
     * @return the created log event.
     */
    private AbstractLogEvent createMessageEvent(final String params,
            final TimestampUtil tsu) {
        final Scanner s = new Scanner(params);
        MatchResult result;
        s.findInLine(SYSTEM_INFO_PARAMS);

        try {
            result = s.match();
        } catch (final IllegalStateException e) {
            throw new ImportException("Unable to parse: " + params
                    + ", with regular expression" + REGEXP, e);
        }
        GenericLogEvent event = new GenericLogEvent();
        event.setTs(tsu.getTs());
        /*
         * Add Message as a dynamic property
         */
        event.addProperty("Message", result.group(1));

        event.setTypeName("LogMessage");
        event.setLogFile(logfile);
        return event;
    }

    /**
     * Creates a generic task(/process) switch event where the log event type
     * (that is shown in the GUI) is set to TaskSwitch.<br>
     * <br>
     * 
     * @param params
     *            contains the parameters to the log event that is returned.
     * @param tsu
     *            is the time stamp.
     * @return the created log event.
     */
    @SuppressWarnings("unused")
    private AbstractLogEvent createTaskSwitchEvent(final String params,
            final TimestampUtil tsu) {
        final Scanner s = new Scanner(params);
        s.findInLine(TASKSWITCH_PARAMS);
        MatchResult result;
        try {
            result = s.match();
        } catch (final IllegalStateException e) {
            throw new ImportException("Unable to parse: " + params
                    + ", with regular expression" + REGEXP, e);
        }
        GenericTaskSwitchEvent event = new GenericTaskSwitchEvent();
        event.setLogFile(logfile);
        event.setTs(tsu.getTs());
        event.setTypeName("TaskSwitch");

        GenericTask outTask = getArtifactByName(result.group(1));
        event.setResourceUserOut(outTask);

        GenericTask inTask = getArtifactByName(result.group(2));
        event.setResourceUserIn(inTask);

        return event;
    }

    /**
     * Creates a generic send event where the log event type (that is shown in
     * the GUI) is set to SendEvent.<br>
     * <br>
     * 
     * @param params
     *            contains the parameters that are needed to create the send
     *            event: sender, receiver, signal
     * @param tsu
     *            is the time stamp.
     * @return the created log event.
     */
    @SuppressWarnings("unused")
    private AbstractLogEvent createSendEvent(final String params,
            final TimestampUtil tsu) {
        final Scanner s = new Scanner(params);
        s.findInLine(SEND_PARAMS);
        MatchResult result;
        try {
            result = s.match();
        } catch (final IllegalStateException e) {
            throw new ImportException("Unable to parse: " + params
                    + ", with regular expression" + REGEXP, e);
        }
        GenericSendEvent event = new GenericSendEvent();
        event.setLogFile(logfile);
        event.setTs(tsu.getTs());
        event.setSender(getArtifactByName(result.group(1)));
        event.setRecipent(getArtifactByName(result.group(2)));
        event.setMessage(result.group(3));
        return event;
    }

    /**
     * Creates a generic receive event where the log event type (that is shown
     * in the GUI) is set to ReceiveEvent.<br>
     * <br>
     * 
     * @param params
     *            contains the parameters that are needed to create the receive
     *            event: sender, receiver, signal
     * @param tsu
     *            is the time stamp.
     * @return the created log event.
     */
    @SuppressWarnings("unused")
    private AbstractLogEvent createReceiveEvent(String params, TimestampUtil tsu) {
        final Scanner s = new Scanner(params);
        s.findInLine(RECEIVE_PARAMS);
        MatchResult result;
        try {
            result = s.match();
        } catch (final IllegalStateException e) {
            throw new ImportException("Unable to parse: " + params
                    + ", with regular expression" + REGEXP, e);
        }
        GenericReceiveEvent event = new GenericReceiveEvent();
        event.setLogFile(logfile);
        event.setTs(tsu.getTs());

        String recName = result.group(1);
        event.setReceiver(getArtifactByName(recName));
        event.setResourceUser(getArtifactByName(recName));
        event.setMessage(result.group(2));

        return event;
    }

    /**
     * Returns a artifact of the GenericTask type. If the task is already
     * created, the created is used, otherwise is a new created and returned.
     * Tasks are are used for the sequence diagram view and the Gantt diagram
     * view.<br>
     * <br>
     * 
     * @param name
     *            is the name of the task
     * @return the already created task or a newly created and added one.
     */
    private GenericTask getArtifactByName(final String name) {
        GenericTask artifact = null;
        if (artifacts.containsKey(name)) {
            artifact = artifacts.get(name);
        } else {
            artifact = new GenericTask();
            artifact.setLogFile(logfile);
            artifact.setName(name);
            artifact.setTaskId(name.hashCode());
            artifact.setParent(getSequence());
            artifact = importContext.resolveArtifact(artifact);
            artifacts.put(name, artifact);
        }
        return artifact;
    }

    /**
     * Returns a sequence. If the sequence is already created, the created is
     * used, otherwise is a new created and returned. Sequences are are used for
     * the sequence diagram view. There can exist more than one sequence. When
     * opening a sequence diagram, the user have to specify what sequence to
     * show.<br>
     * The artifacts that are created have to have a sequence to refer to. <br>
     * <br>
     * 
     * @return the already created sequence or a newly created and added one.
     */
    private ISequence getSequence() {
        if (this.sequence == null) {
            this.sequence = new GenericSequence();
            this.sequence.setLogFile(logfile);
            this.sequence.setRoot(true);
            this.sequence.setName("Sequence");
            this.sequence = importContext.resolveArtifact(sequence);
        }
        return this.sequence;
    }

    /**
     * A helper class that is needed to be able to know if the log file contains
     * more log events after the last one read and parsed. Caching is the answer
     * of this problem.
     * 
     * @author cafa
     * 
     */
    class StringElementIterator implements Iterator<ILogEvent> {
        private LinkedList<ILogEvent> cachedEvents;

        public StringElementIterator() {
            cachedEvents = new LinkedList<ILogEvent>();

            /*
             * The next-method needs to know if there are more events. Caching
             * is the answer of this problem.
             */
            readEvent();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            if (cachedEvents.size() > 0) {
                return true;
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Iterator#next()
         */
        public ILogEvent next() {
            readEvent();
            ILogEvent first = cachedEvents.getFirst();
            cachedEvents.removeFirst();
            return first;
        }

        /**
         * Reads from the log file and creates an event. The event is put in the
         * cache.
         */
        private void readEvent() {
            ILogEvent readEvent = null;
            try {
                String readLine = logFileReader.readLine();
                while (readLine != null && readLine.length() < 10) {
                    readLine = logFileReader.readLine();
                }
                if (readLine != null) {
                    readEvent = createEventFromString(readLine);
                }
            } catch (IOException e) {
                return;
            }

            if (readEvent != null) {
                cachedEvents.addLast(readEvent);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
