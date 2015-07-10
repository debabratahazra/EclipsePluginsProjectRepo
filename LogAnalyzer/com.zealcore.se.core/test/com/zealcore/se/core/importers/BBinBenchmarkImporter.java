package com.zealcore.se.core.importers;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.zip.ZipInputStream;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.Function;
import com.zealcore.se.core.model.FunctionEnter;
import com.zealcore.se.core.model.FunctionExit;
import com.zealcore.se.core.model.FunctionSequence;
import com.zealcore.se.core.model.IActivitySetter;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.model.generic.GenericTask;

/**
 * @author pasa
 * 
 */
public class BBinBenchmarkImporter implements IImporter, Closeable {

    private DataInputStream buffer;

    private int nrOfEvents;

    private LogFile fileObject;

    private HashMap<String, GenericTask> taskMap;

    private File logFile;

    private ImportContext importContext;

    private int version;

    private FunctionSequenceBuilder functionSequenceBuilder;

    private static final int MIN_SIZE = 48;

    private static final String RBRACKET = "]";

    private static final String UNKNOWN = "Unknown";

    private ArrayList<ILogEvent> logEvents = new ArrayList<ILogEvent>();

    @Override
    public String toString() {
        return "BBBin Benchmark Importer";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#canRead(java.io.File)
     */
    public boolean canRead(final File logFile) {

        long id = 0;

        if (logFile.canRead()
                && logFile.length() > BBinBenchmarkImporter.MIN_SIZE) {
            try {
                buffer = new DataInputStream(new FileInputStream(logFile));
                id = buffer.readInt();
                version = buffer.readInt();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                buffer.close();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return id == 0x0BADBABEL;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getArtifacts()
     */
    @SuppressWarnings("unchecked")
    public Iterable<IArtifact> getArtifacts() {
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getLogEvents()
     */
    public Iterable<ILogEvent> getLogEvents() {
        return logEvents;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#setLogFile(java.io.File)
     */
    public void setContext(final ImportContext context) {
        long startTs = System.currentTimeMillis();

        importContext = context;
        final File file = context.getFile();

        if (logFile != null) {
            throw new IllegalStateException(
                    "SetLogFile should only be called once");
        }
        logFile = file;
        if (!file.canRead()) {
            throw new RuntimeException("Failed to read file: " + file.getName()
                    + " on path: " + file.getAbsolutePath());
        }

        try {
            initialize();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Iterable<ILogEvent> it = new Iterable<ILogEvent>() {
            public Iterator<ILogEvent> iterator() {
                return new FileIterator();
            }
        };

        for (ILogEvent logEvent : it) {
            logEvents.add(logEvent);
        }
        long endTs = System.currentTimeMillis();
//        MessageDialog.openError(new Shell(), "Import Completed!", "Start ts:"
//                + startTs + ", end ts:" + endTs + "\nTotal time in millis:"
//                + (endTs - startTs));
        System.out.println("Import Completed!, Start ts:" + startTs
                + ", end ts:" + endTs + "\nTotal time in millis:"
                + (endTs - startTs));
    }

    private void initialize() throws IOException {
        final File file = importContext.getFile();

        final InputStream fis = new FileInputStream(file);

        if (isZipped(file)) {
            final ZipInputStream zipIn = new ZipInputStream(fis);
            zipIn.getNextEntry();

            buffer = new DataInputStream(new BufferedInputStream(zipIn));
        } else {
            buffer = new DataInputStream(new BufferedInputStream(fis));
        }

        fileObject = importContext.getLog();
        fileObject.setVersion(1);

        taskMap = new HashMap<String, GenericTask>();

        /*
         * Read and ignore magic id
         */
        buffer.readInt();

        version = buffer.readInt();
        /*
         * Read and ignore data for future usage (4*10 bytes)
         */
        buffer.skipBytes(36);
        readStrings();
        if (version >= 6) {
            readUserStructs();
        }

        if (version >= 3) {
            readTaskTable();
        }

        if (version >= 2) {
            createStaticStateMachines();
        }
        nrOfEvents = buffer.readInt();
    }

    private void readUserStructs() throws IOException {
        int structCount = buffer.readInt();
    }

    private void readStrings() throws IOException {
        final int stringCntr = buffer.readInt();
    }

    private void readTaskTable() throws IOException {
        Map<Integer, TaskStats> taskStatsMap = new HashMap<Integer, TaskStats>();
        final int taskStatsCount = buffer.readInt();
        final int taskCount = buffer.readInt();
    }

    private void createStaticStateMachines() throws IOException {
        /*
         * Read nr of statemachines
         */
        final int nrOfStateMachines = buffer.readInt();
    }

    private boolean isZipped(final File logFile2) throws IOException {
        if (logFile2.length() < 4) {
            return false;
        }
        DataInputStream reader = null;
        try {
            reader = new DataInputStream(new FileInputStream(logFile2));
            final int magic = reader.readInt();
            return magic == 0x504b0304 || magic == 0x504b0506;

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    ILogEvent readEvent() throws IOException {
        ILogEvent event = null;

        final long ts = buffer.readLong();
        final int eventId = buffer.readInt();

        if (eventId == BBBinWriter.Message.BENCHMARK.binTypeId()) {
            event = createBenchmarkEvent(ts);
        }

        if (event == null) {
            GenericLogEvent genericLogEvent = new GenericLogEvent();
            genericLogEvent.setTypeName("UnknownEventType");
            genericLogEvent.setLogFile(fileObject);
            genericLogEvent.setTs(ts);
            event = genericLogEvent;
        }
        return event;
    }

    private ILogEvent createBenchmarkEvent(final long ts) throws IOException {
        int stringLength = buffer.readInt();
        byte[] bytes = new byte[stringLength];
        buffer.read(bytes);
        String data = new String(bytes, "ISO-8859-1");

        GenericLogEvent simple = new GenericLogEvent(ts);
        simple.setTypeName("BenchmarkEvent");
        simple.addProperty("data", data);
        simple.setLogFile(fileObject);

        return simple;
    }

    private GenericTask getTaskFromName(final String name) {
        GenericTask task;
        // final Long key = Long.valueOf(id);
        if (!taskMap.containsKey(name)) {
            throw new IllegalStateException(
                    "ERROR, failed to get task with name:" + name);
        } else {
            task = importContext.resolveArtifact((GenericTask) taskMap
                    .get(name));
        }
        return task;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#size()
     */
    public int size() {
        return nrOfEvents;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException {
        if (buffer != null) {
            buffer.close();
        }

    }

    class FileIterator implements Iterator<ILogEvent> {
        private int index;

        public boolean hasNext() {
            return index < nrOfEvents;
        }

        public ILogEvent next() {
            try {
                ILogEvent readEvent = null;
                while (readEvent == null && hasNext()) {
                    readEvent = readEvent();
                    ++index;
                }
                if (readEvent == null) {
                    readEvent = new ILogEvent() {

                        public String getDate() {
                            return "";
                        }

                        public LogFile getLogFile() {
                            return LogFile.DEFAULT;
                        }

                        public long getTs() {
                            return Long.MAX_VALUE;
                        }

                        public void setLogFile(final LogFile fileObject) {}

                        public void setTs(final long ts) {}

                        public Long getTimeReference() {
                            return Long.MAX_VALUE;
                        }

                        public int compareTo(final Object arg0) {
                            throw new UnsupportedOperationException();
                        }

                        public IType getType() {
                            throw new UnsupportedOperationException();
                        }

                        public List<SEProperty> getZPropertyAnnotations() {
                            throw new UnsupportedOperationException();
                        }

                        public Collection<IArtifact> referencedArtifacts() {
                            return new ArrayList<IArtifact>();
                        }

                        public void substitute(final IArtifact oldArtifact,
                                final IArtifact newArtifact) {

                        }
                    };
                }
                return readEvent;
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    static class ActorId {
        private int key;

        private long instance;

        private static ActorId single = new ActorId(0, 0);

        public ActorId(final int key, final long instance) {
            this.key = key;
            this.instance = instance;
        }

        public ActorId copy() {
            return new ActorId(key, instance);
        }

        static ActorId valueOf(final int strix, final long instance) {
            ActorId.single.instance = instance;
            ActorId.single.key = strix;
            return ActorId.single;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            long result = 1;
            result = prime * result + instance;
            result = prime * result + key;
            return (int) result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ActorId other = (ActorId) obj;
            if (instance != other.instance) {
                return false;
            }
            if (key != other.key) {
                return false;
            }
            return true;
        }

    }

    private final class TaskStats {

        private long remainingTicksCount;

        private long remainingTicksMin;

        private long remainingTicksMax;

        private float remainingTicksAvg;

        public TaskStats(final DataInputStream buffer) throws IOException {
            remainingTicksCount = buffer.readLong();
            remainingTicksMin = buffer.readLong();
            remainingTicksMax = buffer.readLong();
            remainingTicksAvg = buffer.readFloat();
        }

        long getRemainingTicksCount() {
            return remainingTicksCount;
        }

        long getRemainingTicksMin() {
            return remainingTicksMin;
        }

        long getRemainingTicksMax() {
            return remainingTicksMax;
        }

        float getRemainingTicksAvg() {
            return remainingTicksAvg;
        }
    }

    private final class FunctionSequenceBuilder {
        private final ImportContext importContext;

        private Map<String, ISequenceMember> sequenceMemberMap = new HashMap<String, ISequenceMember>();

        private Stack<ISequenceMember> functionStack = new Stack<ISequenceMember>();

        private ISequence sequence;

        private IActivitySetter lastActivity;

        public FunctionSequenceBuilder(final ImportContext importContext) {
            if (importContext == null) {
                throw new IllegalArgumentException(
                        "importContext must not be null");
            }
            this.importContext = importContext;
            if (fileObject == null) {
                throw new IllegalArgumentException(
                        "fileObject must not be null");
            }

            sequence = new FunctionSequence();
            sequence.setLogFile(fileObject);
            sequence.setName("FunctionTrace[" + fileObject.getFileName()
                    + RBRACKET);
            sequence.setRoot(true);
            sequence = importContext.resolveArtifact(sequence);

            ISequenceMember unknownFunction = new Function();
            unknownFunction.setLogFile(fileObject);
            unknownFunction.setName(UNKNOWN);
            unknownFunction.setParent(sequence);
            unknownFunction.setRoot(false);
            unknownFunction = importContext.resolveArtifact(unknownFunction);
            functionStack.push(unknownFunction);
        }

        private ISequenceMember getFunctionByName(final String name) {
            ISequenceMember sequenceMember = sequenceMemberMap.get(name);
            if (sequenceMember == null) {
                sequenceMember = new Function();
                sequenceMember.setLogFile(fileObject);
                sequenceMember.setName(name);
                sequenceMember.setParent(sequence);
                sequenceMember.setRoot(false);
                sequenceMember = importContext.resolveArtifact(sequenceMember);

                sequenceMemberMap.put(name, sequenceMember);
            }
            return sequenceMember;
        }

        public void addFunctionExit(final FunctionExit functionExit) {
            ISequenceMember sender = functionStack.pop();
            ISequenceMember recepient = functionStack.peek();
            functionExit.setRecipent(recepient);
            functionExit.setSender(sender);
            updateLastActivity(functionExit);
            lastActivity = functionExit;
        }

        public void addFunctionEnter(final FunctionEnter functionEnter) {
            ISequenceMember recipent = getFunctionByName(functionEnter
                    .getFunctionName());
            functionEnter.setRecipent(recipent);
            ISequenceMember sender = functionStack.peek();
            functionEnter.setSender(sender);
            functionStack.push(recipent);

            updateLastActivity(functionEnter);
            lastActivity = functionEnter;

        }

        private void updateLastActivity(final ISequenceMessage message) {
            if (lastActivity != null) {
                // lastActivity.setEndTime(message.getTs());
            }
        }
    }

    private FunctionSequenceBuilder getFunctionSequenceBuilder() {
        if (functionSequenceBuilder == null) {
            functionSequenceBuilder = new FunctionSequenceBuilder(importContext);
        }
        return functionSequenceBuilder;
    }
}
