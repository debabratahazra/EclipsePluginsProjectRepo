package com.zealcore.se.iw.wizard;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericTextImportData;
import com.zealcore.se.iw.types.internal.ImportBehaviour;

/**
 * The MutableImportConfig is a UI friendly mutable
 * {@link GenericTextImportData}. Use this to pass as token between wizard
 * pages and later build a {@link GenericTextImportData}.
 * 
 * @author stch
 * 
 */
final class MutableImportConfig {

    private Set<IChangeListener> changeListeners = new HashSet<IChangeListener>();

    private String defaultEventName;

    private boolean skipEmptyLines;

    private Collection<String> skipLinesRegexp;

    private List<FieldDescriptor> fieldDescriptors;

    private File file;

    private int numberOfHeaderLines;

    void addChangeListener(final IChangeListener listener) {
        this.changeListeners.add(listener);

    }

    void removeChangeListener(final IChangeListener listener) {
        this.changeListeners.remove(listener);

    }

    GenericTextImportData build() {
        return new GenericTextImportData(getDefaultEventName(),
                getFieldDescriptors(), getSkipLinesRegexp(),
                new ImportBehaviour(isSkipEmptyLines(),
                        getNumberOfHeaderLines()), getFile());
    }

    private int getNumberOfHeaderLines() {
        return this.numberOfHeaderLines;
    }

    public void setNumberOfHeaderLines(final int numberOfHeaderLines) {
        this.numberOfHeaderLines = numberOfHeaderLines;
        notifyChangeListeners(true);
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(final File file) {
        this.file = file;
        notifyChangeListeners(true);
    }

    /**
     * Notifies all change listeners of change event
     * 
     * @param changed
     *                if the state has actually changed
     * 
     */
    private void notifyChangeListeners(final boolean changed) {
        for (final IChangeListener listener : this.changeListeners) {
            listener.update(changed);
        }
    }

    public Set<IChangeListener> getChangeListeners() {
        return this.changeListeners;
    }

    public void setChangeListeners(final Set<IChangeListener> changeListeners) {
        this.changeListeners = changeListeners;
        notifyChangeListeners(true);
    }

    public String getDefaultEventName() {
        return this.defaultEventName;
    }

    public void setDefaultEventName(final String defaultEventName) {
        this.defaultEventName = defaultEventName;
        notifyChangeListeners(true);
    }

    public List<FieldDescriptor> getFieldDescriptors() {
        return this.fieldDescriptors;
    }

    public void setFieldDescriptors(final List<FieldDescriptor> fieldDescriptors) {
        this.fieldDescriptors = fieldDescriptors;
        notifyChangeListeners(true);

    }

    public boolean isSkipEmptyLines() {
        return this.skipEmptyLines;
    }

    public void setSkipEmptyLines(final boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
        notifyChangeListeners(true);

    }

    public Collection<String> getSkipLinesRegexp() {
        return this.skipLinesRegexp;
    }

    public void setSkipLinesRegexp(final Collection<String> skipLinesRegexp) {
        this.skipLinesRegexp = skipLinesRegexp;
        notifyChangeListeners(true);
    }

    public boolean isComplete() {
        if (getFieldDescriptors() == null || getFieldDescriptors().size() < 1) {
            return false;
        }
        if (getFile() == null || getSkipLinesRegexp() == null) {
            return false;
        }
        if (getDefaultEventName() == null || getDefaultEventName().length() < 1) {
            return false;
        }
        return true;
    }

}
