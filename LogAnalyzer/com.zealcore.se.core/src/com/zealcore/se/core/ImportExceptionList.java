/**
 * 
 */
package com.zealcore.se.core;

import java.util.ArrayList;

/**
 * Collection of ImportException. This is used when the first import is done
 * during application startup to collect multiple Import Errors. After all log
 * files are imported and all import exceptions are catched. An object of this
 * class is thrown to and caught in the IU class - that also iterates over all
 * exceptions and displays import error dialogs.
 * 
 * @author cafa
 * 
 */
public class ImportExceptionList extends RuntimeException {

    private static final long serialVersionUID = -2098644834261264390L;
    
    private ArrayList<ImportException> exceptions = new ArrayList<ImportException>();

    public ImportExceptionList() {}

    public int size() {
        return exceptions.size();
    }

    public void add(final ImportException exception) {
        exceptions.add(exception);
    }

    public ArrayList<ImportException> getExceptions() {
        return exceptions;
    }
}
