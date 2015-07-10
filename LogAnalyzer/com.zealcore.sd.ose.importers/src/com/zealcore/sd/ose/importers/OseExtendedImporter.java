package com.zealcore.sd.ose.importers;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import com.ose.event.format.EventDumpReader;
import com.ose.event.format.EventReaderClient;
import com.ose.event.format.EventXMLReader;
import com.ose.system.TargetEvent;
import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.ifw.ImportContext;

public class OseExtendedImporter implements IExtendedImporter, Closeable {
    private static final String EXTENSION_PMD = ".pmd";

    private static final String EXTENSION_EVENT = ".event";

    private ImportContext importContext;

    private Collection<GenericArtifactInfo> artifacts;

    private volatile boolean importCancelled;

    boolean parseFailed;

    Exception exception;

    public boolean canRead(final File file) {
        if (file == null) {
            throw new IllegalArgumentException();
        }

        if (!file.isFile() || !file.canRead() || (file.length() < 20)) {
            return false;
        }

        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(EXTENSION_PMD)
                || fileName.endsWith(EXTENSION_EVENT);
    }

    public void setContext(final ImportContext context) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        if (importContext != null) {
            throw new IllegalStateException("Can't import log file twice!");
        }
        importContext = context;
    }

    public Iterable<TypeDescription> getTypeDescriptions() {
        return EventFactory.getTypeDescriptions();
    }

    public Iterable<GenericEventInfo> getEvents() {
        importCancelled = false;
        ImporterThread thread = new ImporterThread();
        thread.start();
        return thread.iterator();
    }

    public Iterable<GenericArtifactInfo> getArtifacts() {
        return artifacts;
    }

    public void close() throws IOException {
        artifacts = null;
        importCancelled = true;
    }

    public String toString() {
        return "OSE PMD/XML Event Importer";
    }

    private class ImporterThread extends Thread {
        private final EventReaderHandler eventReaderHandler = 
            new EventReaderHandler();

        ImporterThread() {
            super("OSE PMD/XML Event Importer Thread");
        }

        public Iterable<GenericEventInfo> iterator() {
            if (parseFailed) {
                throw new ImportException(exception);
            }
            return eventReaderHandler.iterator();
        }

        public void run() {
            try {
                importLogFile();
            } catch (Exception ex) {
                parseFailed = true;
                exception = ex;
            } finally {
                eventReaderHandler.close();
            }
        }

        private void importLogFile() {
            File eventFile = importContext.getFile();
            String fileName = eventFile.getName().toLowerCase();

            if (fileName.endsWith(EXTENSION_PMD)) {
                try {
                    EventDumpReader eventDumpReader = 
                        new EventDumpReader(eventReaderHandler);
                    eventDumpReader.read(eventFile);
                } catch (IOException e) {
                    throw new ImportException(e);
                }
            } else if (fileName.endsWith(EXTENSION_EVENT)) {
                try {
                    EventXMLReader eventXMLReader = 
                        new EventXMLReader(eventReaderHandler);
                    eventXMLReader.read(eventFile);
                } catch (IOException e) {
                    throw new ImportException(e);
                } catch (SAXException e) {
                    throw new ImportException(e);
                } catch (ParserConfigurationException e) {
                    throw new ImportException(e);
                }
            } else {
                throw new ImportException(OseExtendedImporter.this.toString()
                        + " failed to recognize the file: " + eventFile);
            }
        }
    }

    private class EventReaderHandler implements EventReaderClient {
        private static final int EVENT_QUEUE_SIZE = 10000;

        private final EventFactory eventFactory = new EventFactory();

        private final BlockingQueue<GenericEventInfo> eventQueue = 
            new ArrayBlockingQueue<GenericEventInfo>(EVENT_QUEUE_SIZE);

        private final QueueIterator eventQueueIterator = new QueueIterator();

        private volatile boolean done;

        public void commonAttributesRead(final String target,
                final boolean bigEndian, final int osType,
                final int numExecutionUnits, final int tickLength,
                final int microTickFrequency, final String scope,
                final String eventActions) {
            eventFactory.init(bigEndian, tickLength, microTickFrequency);
        }

        public void eventRead(final TargetEvent event) {
            if (importCancelled) {
                throw new ImportException("Import cancelled");
            }

            try {
                while (!eventQueue.offer(eventFactory.getEvent(event), 10,
                        TimeUnit.MILLISECONDS)) {
                    if (importCancelled) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new ImportException(e);
            }
        }

        public Iterable<GenericEventInfo> iterator() {
            return eventQueueIterator;
        }

        public void close() {
            done = true;
            artifacts = eventFactory.getArtifacts();
        }

        private class QueueIterator implements Iterable<GenericEventInfo>,
                Iterator<GenericEventInfo> {
            private GenericEventInfo nextEvent;

            public Iterator<GenericEventInfo> iterator() {
                return this;
            }

            public boolean hasNext() {
                if (importCancelled) {
                    eventQueue.clear();
                    return false;
                }

                if (nextEvent != null) {
                    return true;
                }

                while (!(done && eventQueue.isEmpty())) {
                    try {
                        nextEvent = eventQueue.poll(1000, TimeUnit.MILLISECONDS);
                        if (nextEvent != null) {
                            return true;
                        }
                    } catch (InterruptedException e) {
                        return false;
                    }
                }
                if (parseFailed) {
                    throw new ImportException(exception);
                }
                return false;
            }

            public GenericEventInfo next() {
                if (hasNext()) {
                    GenericEventInfo event = nextEvent;
                    nextEvent = null;
                    return event;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
}
