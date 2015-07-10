package com.zealcore.se.core;

import java.io.IOException;
import java.util.Collection;

import com.zealcore.se.core.ifw.ILogAdapter;

public class ImportOperationCancelledException extends IOException {

    private static final long serialVersionUID = 2L;

    static final String displayMessage = "Import operation cancelled.";

    static final String errorMessage = "Import operation failed.";

    private ImportExceptionList exceptions;

    private Collection<ILogAdapter> logAdapters;

    private boolean importFailed;

    public ImportOperationCancelledException() {
        this(displayMessage);
    }

    public ImportOperationCancelledException(String message) {
        super(message);
    }

    public ImportOperationCancelledException(ImportExceptionList exceptions,
            Collection<ILogAdapter> logAdapters) {
        this(errorMessage);
        this.exceptions = exceptions;
        this.importFailed = true;
        this.logAdapters = logAdapters;
    }

    public boolean isImportFailed() {
        return importFailed;
    }

    public ImportExceptionList getExceptions() {
        return exceptions;
    }

    public Collection<ILogAdapter> getLogAdapters() {
        return logAdapters;
    }
}
