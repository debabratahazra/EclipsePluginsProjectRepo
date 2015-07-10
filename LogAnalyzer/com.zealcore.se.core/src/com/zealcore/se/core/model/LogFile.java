package com.zealcore.se.core.model;

import java.io.File;

import com.zealcore.se.core.annotation.ZCProperty;

public class LogFile {

    private static final int INT_SIZE = 32;

    private static final class DefaultLogFile extends LogFile {
        public DefaultLogFile() {
            setFileName("");
            setSize(0);
            setVersion(0);
        }
    }

    public static LogFile valueOf(final File file) {
        final LogFile log = new LogFile();
        log.setFileName(file.getName());
        log.setImportedAt(System.currentTimeMillis());
        log.setSize(file.length());
        return log;
    }

    public static final LogFile DEFAULT = new DefaultLogFile();

    private long size = -1;

    private long importedAt = -1;

    private int version;

    private String importerId = "";

    private String fileName = "";

    public long getImportedAt() {
        return importedAt;
    }

    public void setImportedAt(final long importedAt) {
        this.importedAt = importedAt;
    }

    public String getImporterId() {
        return importerId;
    }

    public void setImporterId(final String importerId) {
        this.importerId = importerId;
    }

    @ZCProperty(name = "Size")
    public long getSize() {
        return size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    @ZCProperty(name = "Version")
    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (int) (importedAt ^ importedAt >>> LogFile.INT_SIZE);
        result = prime * result
                + (importerId == null ? 0 : importerId.hashCode());
        result = prime * result + (int) (size ^ size >>> LogFile.INT_SIZE);
        result = prime * result + version;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final LogFile other = (LogFile) obj;
        if (importedAt != other.importedAt) {
            return false;
        }
        if (importerId == null) {
            if (other.importerId != null) {
                return false;
            }
        } else if (!importerId.equals(other.importerId)) {
            return false;
        }
        if (size != other.size) {
            return false;
        }
        if (version != other.version) {
            return false;
        }
        return true;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public String toString() {
        return getFileName();
    }
}
