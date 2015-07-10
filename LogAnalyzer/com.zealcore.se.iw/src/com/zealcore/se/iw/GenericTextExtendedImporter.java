package com.zealcore.se.iw;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.format.FieldDescription;
import com.zealcore.se.core.format.FieldDescription.FieldType;
import com.zealcore.se.core.format.FieldValues;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.generic.GenericLogEvent;
import com.zealcore.se.core.services.IServiceProvider;
import com.zealcore.se.core.util.TimestampUtil;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.IModifyingFieldType;
import com.zealcore.se.iw.types.internal.ITimestampType;
import com.zealcore.se.iw.types.internal.IgnoreType;
import com.zealcore.se.iw.types.internal.NameType;
import com.zealcore.se.iw.types.internal.NumberType;
import com.zealcore.se.iw.types.internal.StringType;
import com.zealcore.se.iw.wizard.internal.AbstractTextTreeVisitor;
import com.zealcore.se.iw.wizard.internal.Field;
import com.zealcore.se.iw.wizard.internal.Header;
import com.zealcore.se.iw.wizard.internal.Message;
import com.zealcore.se.iw.wizard.internal.TextParser;

public class GenericTextExtendedImporter implements IExtendedImporter, Closeable {

    private Map<Long, GenericArtifactInfo> artifactList = new HashMap<Long, GenericArtifactInfo>();

    private File file;

    private static final String FILENAME_IS = "Filename is ";

    private final TextParser parser = new TextParser();

    private static final String NO_CONFIG_FOUND = "No Import Wizard configuration found for file: ";

    private static IServiceProvider serviceProvider;

    private volatile boolean importCancelled;

    private volatile boolean parseFailed;
    
    private volatile Exception exception;

    public GenericTextExtendedImporter() {}

    public Iterable<GenericEventInfo> getEvents() {
        importCancelled = false;
        ImporterThread thread = new ImporterThread(
                getImporterConfiguration(this.file), this.file,
                getFieldDescriptorNames(this.file));
        thread.start();
        return thread.iterator();
    }

    public void getLogEvents(final GenericTextImportData config,
            final File logFile) {
        try {
            parser.parse(logFile, config, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getFieldDescriptorNames(final File logFile) {
        List<String> names = new ArrayList<String>();
        List<FieldDescriptor> desc = getImporterConfiguration(logFile)
                .getDescriptors();
        for (FieldDescriptor fieldDescriptor : desc) {
            names.add(fieldDescriptor.getName());
        }
        return names;
    }

    public Iterable<TypeDescription> getTypeDescriptions() {
        Map<String, TypeDescription> types = new HashMap<String, TypeDescription>();
        List<FieldDescription> field = new ArrayList<FieldDescription>();

        List<FieldDescriptor> desc = getImporterConfiguration(this.file)
                .getDescriptors();
        boolean eventTypeDescAvailable = false;
        for (FieldDescriptor fieldDescriptor : desc) {
            IFieldType type = fieldDescriptor.getType();

            if (type instanceof ITimestampType || type instanceof IgnoreType) {

                continue;
            } else if (type instanceof NameType) {
                eventTypeDescAvailable = true;
                continue;
            } else if (type instanceof StringType) {
                field.add(new FieldDescription(fieldDescriptor.getName(),
                        FieldType.STRING));
            } else if (type instanceof NumberType) {
                String str = ((NumberType) type).getLabel();
                if (str.equalsIgnoreCase("Integer")) {
                    field.add(new FieldDescription(fieldDescriptor.getName(),
                            FieldType.INT32));
                } else if (str.equalsIgnoreCase("Double")) {
                    field.add(new FieldDescription(fieldDescriptor.getName(),
                            FieldType.DOUBLE));
                } else if (str.equalsIgnoreCase("Float")) {
                    field.add(new FieldDescription(fieldDescriptor.getName(),
                            FieldType.FLOAT));
                } else if (str.equalsIgnoreCase("Long")) {
                    field.add(new FieldDescription(fieldDescriptor.getName(),
                            FieldType.INT64));
                }
            }
        }
        if (!eventTypeDescAvailable) {
            types.put(
                    getImporterConfiguration(this.file).getDefaultEventName(),
                    new TypeDescription(0, getImporterConfiguration(this.file)
                            .getDefaultEventName(), field));
        } else {
            EventExtendedCompiler compiler = new EventExtendedCompiler(
                    getImporterConfiguration(this.file).getDefaultEventName(),
                    getFieldDescriptorNames(this.file), true);
            try {
                parser.parse(this.file, getImporterConfiguration(this.file),
                        compiler);
                for (Iterator<?> it = compiler.getType().entrySet().iterator(); it
                        .hasNext();) {
                    Map.Entry<String, Integer> entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    Integer value = (Integer) entry.getValue();

                    types.put(String.valueOf(value), new TypeDescription(value,
                            key, field));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                compiler.close();
            }
        }
        return types.values();
    }

    public void setContext(ImportContext context) {

        File log = context.getFile();
        getImporterConfiguration(log);
        if (this.file != null) {
            throw new IllegalStateException(
                    "SetLogFile should only be called once");
        }
        this.file = log;

    }

    public static void setServiceProvider(final IServiceProvider serviceProvider) {
        GenericTextExtendedImporter.serviceProvider = serviceProvider;
    }

    private static GenericImportRegistry getRegistry() {
        return GenericTextExtendedImporter.serviceProvider
                .getService(GenericImportRegistry.class);
    }

    private GenericTextImportData getImporterConfiguration(final File file) {
        try {
            final GenericImportRegistry service = GenericTextExtendedImporter
                    .getRegistry();
            final GenericTextImportData config = service.getImportData(file);
            if (config == null) {
                throw new RuntimeException(
                        GenericTextExtendedImporter.NO_CONFIG_FOUND
                                + file.getName());
            }
            return config;
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(FILENAME_IS + file.getName(), e);
        } catch (final IllegalStateException e) {
            throw new RuntimeException(FILENAME_IS + file.getName(), e);
        }
    }

    public void close() throws IOException {
        importCancelled = true;
    }

    public boolean canRead(File file) {
        return GenericTextExtendedImporter.getRegistry().canRead(file);
    }

    public Iterable<GenericArtifactInfo> getArtifacts() {
        return artifactList.values();
    }

    private class ImporterThread extends Thread {
        private EventExtendedCompiler eventReaderHandler;

        private GenericTextImportData config;

        private File file;

        ImporterThread(final GenericTextImportData config, final File file,
                final List<String> names) {
            super("Text Importer Event Generator Thread");
            this.config = config;
            this.file = file;
            eventReaderHandler = new EventExtendedCompiler(
                    config.getDefaultEventName(), names, false);
        }

        public Iterable<GenericEventInfo> iterator() {
            if (parseFailed) {
                throw new ImportException(exception);
            }
            return eventReaderHandler.iterator();
        }

        public void run() {
            try {
                parser.parse(file, config, eventReaderHandler);
            } catch (Exception e) {
            	parseFailed = true;
            	exception = e;
            } finally {
                eventReaderHandler.close();
            }
        }
    }

    public class EventExtendedCompiler extends AbstractTextTreeVisitor {

        private GenericLogEvent current;

        private final String defaultName;

        private TimestampUtil timestamp;

        private long sequenceNumber;

        private int typeId;

        private Map<String, Integer> types = new HashMap<String, Integer>();

        private static final int EVENT_QUEUE_SIZE = 10000;

        private final BlockingQueue<GenericEventInfo> eventQueue = new ArrayBlockingQueue<GenericEventInfo>(
                EVENT_QUEUE_SIZE);

        private final QueueIterator eventQueueIterator = new QueueIterator();

        private volatile boolean done;

        private List<String> properties;

        private boolean dummyRun;

        EventExtendedCompiler(final String defaultName,
                final List<String> properties, final boolean dummyRun) {
            this.defaultName = defaultName;
            this.properties = properties;
            this.dummyRun = dummyRun;
            sequenceNumber = 0L;
            typeId = 0;
        }

        public Iterable<GenericEventInfo> iterator() {
            return eventQueueIterator;
        }

        public void close() {
            done = true;
        }

        public void visitHeader(final Header header) {
            // this.logFile.addProperty("Text", header.toString());
        }

        @Override
        public void visitMessage(final Message message) {
            this.current = new GenericLogEvent();
            this.timestamp = new TimestampUtil();
            this.current.setTypeName(this.defaultName);
            this.current.setTs(sequenceNumber++);
        }

        @Override
        public void visitField(final Field field) {

            final FieldDescriptor desc = field.getDescriptor();
            final IFieldType type = desc.getType();
            final String trim = field.toString().trim();

            if (type instanceof IModifyingFieldType) {
                final IModifyingFieldType modifier = (IModifyingFieldType) type;
                modifier.modify(this.current, trim);
            } else if (type instanceof ITimestampType) {
                final ITimestampType modifier = (ITimestampType) type;
                modifier.modify(this.current, this.timestamp, trim);
            } else {
                this.current.addProperty(desc.getName(), type.valueOf(trim));
            }

        }

        @Override
        public void visitMessageEnd() {
            addEvent();
            this.current = null;
        }

        private void addEvent() {

            FieldValues values = new FieldValues();
            Map<String, Object> eventProps = this.current.properties();

            if (!types.containsKey(this.current.getTypeName())) {
                types.put(this.current.getTypeName(), Integer.valueOf(typeId));
                typeId++;
            }

            if (properties == null) {
                properties = new ArrayList<String>(eventProps.keySet());
            }

            for (String key : properties) {
                Object obj = eventProps.get(key);
                if (obj instanceof String) {
                    values.addStringValue(obj.toString());
                }
                if (obj instanceof Number) {

                    if (obj instanceof Long) {
                        values.addLongValue(((Number) obj).longValue());
                    } else if (obj instanceof Integer) {
                        values.addIntValue(((Number) obj).intValue());
                    } else if (obj instanceof Float || obj instanceof Double) {
                        values.addFloatValue(((Number) obj).floatValue());

                    }
                }
            }
            if (dummyRun) {
                return;
            }

            if (importCancelled) {
                throw new ImportException("Import cancelled");
            }

            try {
                while (!eventQueue.offer(
                        new GenericEventInfo(this.current.getTs(), types
                                .get(this.current.getTypeName()), values), 10,
                        TimeUnit.MILLISECONDS)) {
                    if (importCancelled) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new ImportException(e);
            }
        }

        public int getTypeId() {
            return typeId;
        }

        public Map<String, Integer> getType() {
            return types;
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
