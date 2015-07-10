package com.zealcore.se.core.importers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.srl.offline.Blackbox;
import com.zealcore.srl.offline.SrlTransform;

public class SRLExtendedImporter implements IExtendedImporter {

    private static final String FILE_SUFFIX_IMG = ".img";

    private static final String FILE_SUFFIX_EXE = ".exe";

    private static final String FILE_SUFFIX_ELF = ".elf";

    private static final String FILE_SUFFIX_OUT = ".out";

    private static final String FILE_SUFFIX_XMI = ".xmi";

    /**
     * Endian values from srl_common.h
     */
    static final int SRL_LITTLE_ENDIAN = 0x0000005A;

    static final int SRL_BIG_ENDIAN = 0xffffffA5;

    /**
     * SRL_ID from srl_core.c
     */
    private static final long SRL_ID_BIG_ENDIAN = 0x5A53524CL;

    private static final long SRL_ID_LITTLE_ENDIAN = 0x4C52535AL;

    private static final int MIN_SIZE = 48;

    private ImportContext importContext;

    private volatile boolean importCancelled;

    private ArrayList<GenericArtifactInfo> artifacts = new ArrayList<GenericArtifactInfo>();

    private List<TypeDescription> typeDescriptions = new ArrayList<TypeDescription>();

    private Blackbox blackBox;

    private boolean parseFailed;

    private Exception exception;

    public SRLExtendedImporter() {
    }

    public boolean canRead(File logFile) {
        if (checkRead(logFile)) {
            try {
            	String fileName 
            		= logFile.getAbsolutePath().substring(0,
                        logFile.getAbsolutePath().lastIndexOf("."));
                SrlTransform srlTransform = new SrlTransform();

                blackBox = srlTransform.transform(logFile.getAbsolutePath(),
                		getResourceFileName(fileName), getXmiFileName(fileName), false);
                EventReaderHandler eventReaderHandler = new EventReaderHandler();
                SRLEventReader eventReader = new SRLEventReader(
                        eventReaderHandler);
                eventReader.read(blackBox, true);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    public boolean checkRead(File logFile) {
        int endian = 0;
        int zsrlId = 0;
        DataInputStream buffer = null;
        if (logFile.canRead() && logFile.length() > MIN_SIZE) {
            try {
                buffer = new DataInputStream(new FileInputStream(logFile));
                if (buffer != null) {
                    endian = (int) buffer.readByte();
                    // Flush three bytes: major, minor, revision
                    buffer.readByte();
                    buffer.readByte();
                    buffer.readByte();
                    zsrlId = buffer.readInt();
                }
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
        }

        // Check endian and the SRL constant, "ZSRL" (0x5A, 0x53, 0x52, 4C)
        // return true;
        if (endian == SRL_BIG_ENDIAN) {
            if (zsrlId == SRL_ID_BIG_ENDIAN) {
                return true;
            }
        } else if (endian == SRL_LITTLE_ENDIAN) {
            if (zsrlId == SRL_ID_LITTLE_ENDIAN) {
                return true;
            }
        }
        return false;
    }

    public void setContext(ImportContext context) {
        this.importContext = context;
    }

    public Iterable<TypeDescription> getTypeDescriptions() {
        typeDescriptions.addAll(EventFactory.getTypeDescriptions());
        return typeDescriptions;
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

    private class ImporterThread extends Thread {
        private final EventReaderHandler eventReaderHandler = new EventReaderHandler();

        ImporterThread() {
            super("SRL Event Importer Thread");
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
            } catch(Exception ex) {
            	parseFailed = true;
            	exception = ex;
            } finally {
                eventReaderHandler.close();
            }
        }

        private void importLogFile() {
            if (blackBox == null) {
            	try  {
	                SrlTransform srlTransform = new SrlTransform();
	                String file = importContext.getFile().getName()
	                        .toLowerCase();
	                String fileName 
            		= importContext.getFile().getAbsolutePath().substring(0,
            				importContext.getFile().getAbsolutePath().lastIndexOf("."));
	                blackBox = srlTransform.transform(file, getResourceFileName(fileName),
	                		getXmiFileName(fileName), false);
            	} catch (Exception ex) {
            		throw new ImportException(ex);
            	}

            }
            SRLEventReader eventReader = new SRLEventReader(eventReaderHandler);
            eventReader.read(blackBox, false);
        }
    }

    private String getResourceFileName(String fileName) {
        String resourceFile = null;
        if (new File(fileName + FILE_SUFFIX_IMG).exists()) {
        	resourceFile = fileName + FILE_SUFFIX_IMG ;
        } else if (new File(fileName + FILE_SUFFIX_EXE).exists()) {
        	resourceFile = fileName + FILE_SUFFIX_EXE;
        } else if (new File(fileName + FILE_SUFFIX_ELF).exists()) {
        	resourceFile = fileName + FILE_SUFFIX_ELF;
        } else if (new File(fileName + FILE_SUFFIX_OUT).exists()) {
        	resourceFile = fileName + FILE_SUFFIX_OUT;
        }
        return resourceFile;
    }
    
    private String getXmiFileName(String fileName) {
        String xmiFile = null;
        if (new File(fileName + FILE_SUFFIX_XMI).exists()) {
            xmiFile = fileName + FILE_SUFFIX_XMI;
        }
        return xmiFile;
    }
    
    private class EventReaderHandler implements EventReaderClient {
        private static final int EVENT_QUEUE_SIZE = 10000;

        private final BlockingQueue<GenericEventInfo> eventQueue = new ArrayBlockingQueue<GenericEventInfo>(
                EVENT_QUEUE_SIZE);

        private final QueueIterator eventQueueIterator = new QueueIterator();

        private volatile boolean done;

        public void eventRead(GenericEventInfo event) {
            if (importCancelled) {
                throw new ImportException("Import cancelled");
            }

            try {
                while (!eventQueue.offer(event, 10, TimeUnit.MILLISECONDS)) {
                    if (importCancelled) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new ImportException(e);
            }
        }

        public void eventRead(GenericArtifactInfo event) {
            artifacts.add(event);
        }

        public void typeDescRead(TypeDescription typeDesc) {
            typeDescriptions.add(typeDesc);
        }

        public Iterable<GenericEventInfo> iterator() {
            return eventQueueIterator;
        }

        public void close() {
            done = true;
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
                        nextEvent = eventQueue
                                .poll(1000, TimeUnit.MILLISECONDS);
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
