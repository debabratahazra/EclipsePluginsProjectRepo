package com.zealcore.se.core.model.generic;

import java.io.File;

import com.zealcore.se.core.model.LogFile;

/**
 * This class wrapps the a log file but also adds the possibility to add
 * parameters.
 * 
 * @author cafa
 * 
 */
public class GenericLogFile extends LogFile {

    private final GenericAdapter adapter = new GenericAdapter();

    /**
     * @param name
     *            of the property
     * @param value
     *            the properties value
     */
    public void addProperty(final String name, final Object value) {
        this.adapter.addProperty(name, value);
    }

    /**
     * @param file
     *            the file that is to be refered to in the returned
     *            GenericLogFile object.
     * @return the created GenericLogFile.
     */
    public static GenericLogFile valueOf(final File file) {
        final GenericLogFile f = new GenericLogFile();
        f.setFileName(file.getName());
        f.setImportedAt(System.currentTimeMillis());
        f.setSize(file.length());
        return f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.LogFile#toString()
     */
    @Override
    public String toString() {
        return this.getFileName();
    }
}
