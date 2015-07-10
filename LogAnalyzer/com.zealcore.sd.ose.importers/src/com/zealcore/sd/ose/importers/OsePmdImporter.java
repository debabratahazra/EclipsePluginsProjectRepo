package com.zealcore.sd.ose.importers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;

import com.ose.event.format.EventDumpReader;
import com.ose.event.format.EventReaderClient;
import com.ose.event.format.EventXMLReader;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.system.TargetEvent;
import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ImportException;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.importers.BBBinImporter;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.util.ImportUtil;

public class OsePmdImporter implements IImporter, Closeable {

    private ImportContext importContext;

    private BBBinImporter binImporter;

    private File dmpFile;

    private DataOutputStream bin;

    private static final String FILE_SUFFIX_BIN = ".bin";

    private LogType logType = LogType.UNKNOWN;

    private static boolean isDryRun;

    private File binFile;

    private ImportContext binContext;

    public enum LogType {
        PMD_BIN, PMD_XML, UNKNOWN
    }

    public boolean canRead(final File file) {
        boolean retValue = false;
        DumpFile dumpFile = null;
        if (!file.canRead() || (file.length() < 20)) {
            return false;
        }
        try {
            /*
             * Try to read file as binary pmd
             */
            dumpFile = new DumpFile(file);
            List<?> textBlocks = dumpFile.getTextBlocks();
            if (textBlocks != null) {
                logType = LogType.PMD_BIN;
                retValue = true;
            }
        } catch (IffException e) {
            retValue = false;
        } catch (IOException e) {
            retValue = false;
        } finally {
            if (dumpFile != null) {
                dumpFile.close();
            }
        }
        if (!retValue) {
            /*
             * Search for the events-tag in the file
             */
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                return false;
            }

            String line;

            /*
             * skip header
             */
            try {
                char[] cbuf = new char[50];
                br.read(cbuf, 0, 50);
                if (!String.valueOf(cbuf).contains("ISO-8859-1")) {
                    return false;
                }
            } catch (IOException e1) {
                return false;
            }

            for (int i = 0; i < 5; i++) {
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    return false;
                }
                if (line != null && line.length() > 50) {
                    Scanner scanner = new Scanner(line);
                    scanner.findInLine("(.*events.*target.*byteOrder.*)");
                    if (scanner != null) {
                        MatchResult match = null;
                        try {
                            match = scanner.match();
                        } catch (IllegalStateException e) {
                            match = null;
                        }
                        if (match != null) {
                            retValue = true;
                            logType = LogType.PMD_XML;
                            break;
                        }
                    }

                }
            }
            try {
                br.close();
            } catch (IOException e) {
                retValue = false;
            }
        }
        return retValue;
    }

    public Iterable<IArtifact> getArtifacts() {
        if (binImporter != null) {
            return binImporter.getArtifacts();
        }
        return Collections.emptyList();
    }

    public Iterable<ILogEvent> getLogEvents() {
        if (binImporter != null) {
            if (binFile != null && binFile.exists()) {
                return binImporter.getLogEvents();
            } else if (dmpFile.exists()) {
                try {
                    binImporter.close();
                } catch (IOException e) {}
                binImporter = new BBBinImporter();
                /*
                 * .bin-file is missing, re-import!
                 */
                importLogFile();
                ImportUtil.refreshFile(this.binFile);
                return binImporter.getLogEvents();
            }
        }
        return Collections.emptyList();
    }

    public void setContext(final ImportContext context) {
        if (this.importContext != null) {
            throw new IllegalStateException("Can't import log twice!");
        }
        this.importContext = context;
        dmpFile = importContext.getFile();
        binImporter = new BBBinImporter();

        importLogFile();
    }

    private void importLogFile() {
        /*
         * If the binary version of the OSE dump not exists, create it. I.e. if
         * the OSE dump dump has not been run through the EventDumpReader, do it
         * now.
         */
        boolean alreadyImported = false;
        binFile = new File(dmpFile.getAbsolutePath() + FILE_SUFFIX_BIN);

        if (binFile.exists()
                && (binFile.lastModified() > dmpFile.lastModified())) {
            alreadyImported = true;
        }
        if (!alreadyImported) {
            if (this.logType == LogType.UNKNOWN) {
                canRead(dmpFile);
            }
            try {
                this.bin = new DataOutputStream(new BufferedOutputStream(
                        new FileOutputStream(binFile)));
            } catch (FileNotFoundException e) {
                throw new ImportException(e);
            }

            final DataOutputStream tmp = bin;
            bin = new DataOutputStream(new OutputStream() {
                @Override
                public void write(final int b) {}
            });

            if (logType == LogType.PMD_BIN) {
                EventDumpReader eventDumpReader;
                EventReaderHandler eventReaderHandler;
                try {
                    eventReaderHandler = new EventReaderHandler(this.bin, true,
                            importContext);
                } catch (IOException e) {
                    throw new ImportException(e);
                }

                eventDumpReader = new EventDumpReader(eventReaderHandler);
                boolean error = false;
                /*
                 * Dry run to get all strings and nr of events
                 */
                OsePmdImporter.isDryRun = true;
                try {
                    eventDumpReader.read(dmpFile);
                } catch (ImportException ex) {
                    error = true;
                    throw ex;
                } catch (IOException e) {
                    throw new ImportException(e);
                } finally {
                    eventReaderHandler.close();

                    if (error) {
                        try {                            
                            tmp.close();
                        } catch (IOException io) {}
                        performCleanUp();
                    }
                }

                /*
                 * Write BBBin file
                 */
                OsePmdImporter.isDryRun = false;
                bin = tmp;
                eventReaderHandler.setOutputStream(this.bin);
                try {
                    eventDumpReader.read(dmpFile);
                } catch (ImportException ex) {
                    error = true;
                    throw ex;
                } catch (IOException e) {
                    throw new ImportException(e);
                } finally {
                    eventReaderHandler.close();
                    try {
                        tmp.close();
                    } catch (IOException io) {}

                    if (error) {
                        performCleanUp();
                    }
                }

            } else if (logType == LogType.PMD_XML) {
                EventXMLReader eventXMLReader;
                EventReaderHandler eventXMLReaderHandler;
                try {
                    eventXMLReaderHandler = new EventReaderHandler(this.bin,
                            true, importContext);
                } catch (IOException e) {
                    throw new ImportException(e);
                }

                eventXMLReader = new EventXMLReader(eventXMLReaderHandler);
                /*
                 * Dry run to get all strings and nr of events
                 */
                try {
                    eventXMLReader.read(dmpFile);
                } catch (IOException e) {
                    throw new ImportException(e);
                } catch (SAXException e) {
                    throw new ImportException(e);
                } catch (ParserConfigurationException e) {
                    throw new ImportException(e);
                } finally {
                    eventXMLReaderHandler.close();
                }

                /*
                 * Write BBBin file
                 */
                bin = tmp;
                eventXMLReaderHandler.setOutputStream(this.bin);
                try {
                    eventXMLReader.read(dmpFile);
                } catch (IOException e) {
                    throw new ImportException(e);
                } catch (SAXException e) {
                    throw new ImportException(e);
                } catch (ParserConfigurationException e) {
                    throw new ImportException(e);
                } finally {
                    eventXMLReaderHandler.close();
                }
            } else if (logType == LogType.UNKNOWN) {
                throw new ImportException(this.toString()
                        + " failed to read the file: " + this.dmpFile);
            }
        }
        binContext = ImportContext.valueOf(this.importContext.getLogset(),
                binFile);
        binImporter.setContext(binContext);
    }

    public int size() {
        return -1;
    }

    public void close() throws IOException {
        if (binImporter != null) {
            binImporter.close();
        }

        if (importContext.getLogset().getProgressMonitor().isCanceled() || !importContext.getLogset().isImportSuccess()) {
            performCleanUp();
        }
    }

    public String toString() {
        return "OSE PMD/XML Event Importer";
    }

    private static class EventReaderHandler implements EventReaderClient {
        private static final String FAILED_TO_CALCULATE_TIMESTAMPS = "Import problem, failed to calculate timestamps";

        private EventBBBinWriter eventBBBinWriter;

        private ImportContext importContext;

        public EventReaderHandler(final DataOutputStream bin,
                final boolean trace, final ImportContext importContext)
                throws IOException {
            eventBBBinWriter = new EventBBBinWriter(bin, trace);
            this.importContext = importContext;
        }

        public void setOutputStream(final DataOutputStream bin) {
            eventBBBinWriter.setOutputStream(bin);
        }

        public void commonAttributesRead(final String target,
                final boolean bigEndian, final int osType,
                final int numExecutionUnits, final int tickLength,
                final int microTickFrequency, final String scope,
                final String eventActions) {

            // This is a second solution if the microTickFrequency or tickLength
            // is missing
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    // if (isDryRun) {

                    if (microTickFrequency == 0 && tickLength == 0) {
                        MessageDialog
                                .openInformation(new Shell(),
                                        FAILED_TO_CALCULATE_TIMESTAMPS,
                                        "microTickFrequency and tickLength are 0, will use counter timestamps.");
                    } else {
                        if (microTickFrequency == 0) {
                            MessageDialog
                                    .openInformation(new Shell(),
                                            FAILED_TO_CALCULATE_TIMESTAMPS,
                                            "microTickFrequency is 0, will ignore nTicks.");
                        }
                        if (tickLength == 0) {
                            MessageDialog
                                    .openInformation(new Shell(),
                                            FAILED_TO_CALCULATE_TIMESTAMPS,
                                            "tickLength is 0, will use counter timestamps.");
                        }
                    }
                }
                // }
            });
            try {
                eventBBBinWriter.writeCommonAttributes(target, bigEndian,
                        scope, eventActions, tickLength, microTickFrequency,
                        osType);
            } catch (IOException e) {
                throw new ImportException(e);
            }
        }

        public void eventRead(final TargetEvent event) {
            try {
                if (importContext.getLogset().getProgressMonitor().isCanceled()) {
                    eventBBBinWriter.stopImporting(importContext);
                    eventBBBinWriter = null;
                    return;
                }
                eventBBBinWriter.write(event);

            } catch (IOException e) {
                throw new ImportException(e);
            }
        }

        public void close() {
            try {
                eventBBBinWriter.close();
            } catch (IOException e) {
                throw new ImportException(e);
            }
        }
    }

    public void performCleanUp() {
        if (binImporter != null) {
            try {
                binImporter.close();
                binImporter = null;

                if (bin != null) {
                    bin.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        if (binFile != null && binFile.exists()) {
            binFile.delete();
        }
    }
}
