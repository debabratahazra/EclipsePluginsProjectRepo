package com.zealcore.se.core.importers;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.util.ImportUtil;
import com.zealcore.srl.offline.SrlTransform;

/**
 * @author cafa
 * 
 */
public class SRLImporter implements IImporter, Closeable {

    private static final String E_PARAM = "-e";

    private static final String FILE_SUFFIX_BIN = ".bin";

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

    private DataInputStream buffer;

    private BBBinImporter binImporter;

    private File logFile;

    private File binFile;

    private ImportContext importContext;

    @Override
    public String toString() {
        return "System Recorder Importer (c) Enea ZealCore AB";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#canRead(java.io.File)
     */
    public boolean canRead(final File logFile) {
        int endian = 0;
        int zsrlId = 0;

        if (logFile.canRead() && logFile.length() > SRLImporter.MIN_SIZE) {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getArtifacts()
     */
    public Iterable<IArtifact> getArtifacts() {
        if (binImporter != null) {
            return binImporter.getArtifacts();
        }
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getLogEvents()
     */
    public Iterable<ILogEvent> getLogEvents() {
        if (binImporter != null) {
            if (this.binFile != null && this.binFile.exists()) {
                return binImporter.getLogEvents();
            } else if (logFile.exists()) {
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

    private void importLogFile() {
        final String fileName = logFile.getAbsolutePath().substring(0,
                logFile.getAbsolutePath().lastIndexOf("."));

        // If the binary version of the SRL not exists, create it. I.e. if the
        // System Recorder dump has not been run trough the SRL-Transformer, do
        // it now.
        // if (!(new File(fileName + FILE_SUFFIX_BIN).exists())) {
        final List<String> args = new ArrayList<String>();
        args.add("-o" + logFile.getAbsolutePath() + FILE_SUFFIX_BIN);
        boolean alreadyImported = false;
        binFile = new File(logFile.getAbsolutePath() + FILE_SUFFIX_BIN);
        if (logFile.exists()) {
            args.add(logFile.getAbsolutePath());

            if (binFile.exists()
                    && (binFile.lastModified() > logFile.lastModified())) {
                alreadyImported = true;
            }
            if (!alreadyImported) {
                if (new File(fileName + FILE_SUFFIX_IMG).exists()) {
                    args.add(E_PARAM + fileName + FILE_SUFFIX_IMG);
                } else if (new File(fileName + FILE_SUFFIX_EXE).exists()) {
                    args.add(E_PARAM + fileName + FILE_SUFFIX_EXE);
                } else if (new File(fileName + FILE_SUFFIX_ELF).exists()) {
                    args.add(E_PARAM + fileName + FILE_SUFFIX_ELF);
                } else if (new File(fileName + FILE_SUFFIX_OUT).exists()) {
                    args.add(E_PARAM + fileName + FILE_SUFFIX_OUT);
                }
                if (new File(fileName + FILE_SUFFIX_XMI).exists()) {
                    args.add("-x" + fileName + FILE_SUFFIX_XMI);
                }

                SrlTransform.main(args.toArray(new String[args.size()]));
            }
        }

        final ImportContext srlContext = ImportContext.valueOf(importContext
                .getLogset(), binFile);
        binImporter.setContext(srlContext);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#setLogFile(java.io.File)
     */
    public void setContext(final ImportContext context) {
        if (logFile != null) {
            throw new IllegalStateException(
                    "SetLogFile should only be called once");
        }
        this.importContext = context;
        logFile = importContext.getFile();
        binImporter = new BBBinImporter();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#size()
     */
    public int size() {
        if (binImporter != null) {
            return binImporter.size();
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    public void close() throws IOException {
        if (binImporter != null) {
            binImporter.close();
        }

        if (importContext.getLogset().getProgressMonitor().isCanceled()
                || !importContext.getLogset().isImportSuccess()) {
            if (binFile != null && binFile.exists()) {
                binFile.delete();
            }
        }
    }
}
