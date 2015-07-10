package com.zealcore.se.iw;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.zealcore.se.iw.types.internal.ImportBehaviour;

public class GenericTextImportData {
    static final String KEY_FIELD_DESCRIPTOR = "fieldDescriptor";

    static final String KEY_FIELD_DESCRIPTORS = "fieldDescriptors";

    static final String KEY_DEFAULT_EVENT_NAME = "defaultEventName";

    static final String KEY_FILEID = "fileid";

    static final String KEY_FILTER = "filter";

    static final String KEY_FILTERS_ROOT = "filters";

    static final String KEY_FILTER_TEXT = "filterText";

    static final String KEY_NOF_HEADERLINES = "nofHeaderLines";

    static final String KEY_SKIP_EMPTY_LINES = "skipEmptyLines";

    public static final String IMPORTER_ID = "com.zealcore.se.iw.wizard.GenericTextImporter";

    private String defaultEventName;

    private List<FieldDescriptor> descriptors;

    private ImportBehaviour importBehaviour;

    private String fileId;

    private Collection<String> filters;

    public GenericTextImportData(final String defaultEventName,
            final List<FieldDescriptor> descriptors,
            final Collection<String> filters,
            final ImportBehaviour importBehaviour, final File file) {
        super();
        if (defaultEventName == null || defaultEventName.length() == 0) {
            throw new IllegalArgumentException("Must specify an event name");
        }
        this.defaultEventName = defaultEventName;
        if (descriptors == null || descriptors.size() == 0) {
            throw new IllegalArgumentException(
                    "At least one field decriptor must exist");
        }
        this.descriptors = descriptors;
        if (importBehaviour == null) {
            throw new IllegalArgumentException("Missing import behaviour data");
        }
        this.importBehaviour = importBehaviour;
        this.filters = filters;
        final String fileName = file.getName();
        final int indexOfDot = fileName.lastIndexOf(".");
        if (indexOfDot < 0) {
            this.fileId = fileName;
        } else {
            this.fileId = fileName.substring(indexOfDot);
        }
    }

    public GenericTextImportData(final IMemento instance) {
        try {
            this.defaultEventName = instance
                    .getString(GenericTextImportData.KEY_DEFAULT_EVENT_NAME);
            final IMemento fields = instance
                    .getChild(GenericTextImportData.KEY_FIELD_DESCRIPTORS);
            final IMemento[] children = fields
                    .getChildren(GenericTextImportData.KEY_FIELD_DESCRIPTOR);
            this.fileId = instance.getString(GenericTextImportData.KEY_FILEID);
            final Integer nofHeaderLinesI = instance
                    .getInteger(GenericTextImportData.KEY_NOF_HEADERLINES);
            final Integer skipEmptyLinesI = instance
                    .getInteger(GenericTextImportData.KEY_SKIP_EMPTY_LINES);
            int nofHeaderLines = 0;
            boolean skipEmptyLines = true;
            if (nofHeaderLinesI != null) {
                nofHeaderLines = nofHeaderLinesI;
            }
            if (skipEmptyLinesI != null) {
                skipEmptyLines = skipEmptyLinesI == 0 ? false : true;
            }
            this.importBehaviour = new ImportBehaviour(skipEmptyLines,
                    nofHeaderLines);
            this.descriptors = new ArrayList<FieldDescriptor>();
            for (final IMemento memento : children) {
                final FieldDescriptor descriptor = new FieldDescriptor(memento);
                this.descriptors.add(descriptor);
            }
            final IMemento filtersRoot = instance
                    .getChild(GenericTextImportData.KEY_FILTERS_ROOT);
            if (filtersRoot != null) {
                final IMemento[] filtersChildren = filtersRoot
                        .getChildren(GenericTextImportData.KEY_FILTER);
                if (filtersChildren != null && filtersChildren.length > 0) {
                    this.filters = new HashSet<String>();
                    for (final IMemento memento : filtersChildren) {
                        final String strFilter = memento
                                .getString(GenericTextImportData.KEY_FILTER_TEXT);
                        this.filters.add(strFilter);
                    }
                }
            }
        } catch (final NullPointerException e) {
            throw new IllegalStateException("Error when restoring state");
        }
    }

    public GenericTextImportData() {
        this.defaultEventName = "";
        this.descriptors = new ArrayList<FieldDescriptor>();
        this.fileId = "NoSuchFile";
        this.filters = new ArrayList<String>();
        this.importBehaviour = new ImportBehaviour(true, 0);
    }

    public String getDefaultEventName() {
        return this.defaultEventName;
    }

    public void setDefaultEventName(final String defaultEventName) {
        this.defaultEventName = defaultEventName;
    }

    public List<FieldDescriptor> getDescriptors() {
        return this.descriptors;
    }

    public int getSize() {
        return this.descriptors.size();
    }

    public void setDescriptors(final List<FieldDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    /**
     * Returns true if empty lines should be ignored, otherwise false.
     * 
     * @return
     *          - 
     */
    public boolean getSkipEmptyLines() {
        if (this.importBehaviour == null) {
            return true;
        }
        return this.importBehaviour.getSkipEmptyLines();
    }

    public int getNoOfHeaderLines() {
        if (this.importBehaviour == null) {
            return 0;
        }
        return this.importBehaviour.getNoOfHeaderLines();
    }

    public void save(final IMemento memento) {
        memento.putString(GenericTextImportData.KEY_DEFAULT_EVENT_NAME,
                this.defaultEventName);
        memento.putString(GenericTextImportData.KEY_FILEID, this.fileId);
        memento.putInteger(GenericTextImportData.KEY_NOF_HEADERLINES,
                this.importBehaviour.getNoOfHeaderLines());
        memento.putInteger(GenericTextImportData.KEY_SKIP_EMPTY_LINES,
                this.importBehaviour.getSkipEmptyLines() ? 1 : 0);
        final IMemento fieldsMemento = memento
                .createChild(GenericTextImportData.KEY_FIELD_DESCRIPTORS);
        for (final FieldDescriptor descriptor : this.descriptors) {
            final IMemento fieldMemento = fieldsMemento
                    .createChild(GenericTextImportData.KEY_FIELD_DESCRIPTOR);
            descriptor.save(fieldMemento);
        }
        if (this.filters != null && this.filters.size() > 0) {
            final IMemento filtersMemento = memento
                    .createChild(GenericTextImportData.KEY_FILTERS_ROOT);
            for (final String filter : this.filters) {
                final IMemento filterMemento = filtersMemento
                        .createChild(GenericTextImportData.KEY_FILTER);
                filterMemento.putString(GenericTextImportData.KEY_FILTER_TEXT,
                        filter);
            }
        }
    }

    public static GenericTextImportData valueOf(final IMemento instance) {
        return new GenericTextImportData(instance);

    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(final String fileId) {
        this.fileId = fileId;
    }

    @Override
    public int hashCode() {
        return getDefaultEventName().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof GenericTextImportData)) {
            return false;
        }
        final GenericTextImportData other = (GenericTextImportData) obj;
        if (!getDefaultEventName().equals(other.getDefaultEventName())) {
            return false;
        }
        if (!getDescriptors().equals(other.getDescriptors())) {
            return false;
        }
        if (!getFilters().equals(other.getFilters())) {
            return false;
        }
        return true;
    }

    public void setNoOfHeaderLines(final int noOfHeaderLines) {
        this.importBehaviour.setNoOfHeaderLines(noOfHeaderLines);
    }

    public void setSkipEmptyLines(final boolean skipEmptyLines) {
        this.importBehaviour.setSkipEmptyLines(skipEmptyLines);
    }

    public Collection<String> getFilters() {
        if (this.filters == null) {
            this.filters = new ArrayList<String>();
        }
        return this.filters;
    }

    public void setFilters(final Collection<String> filters) {
        this.filters = filters;
    }

    public GenericTextImportData copy() {
        final GenericTextImportData copy = new GenericTextImportData();

        final List<FieldDescriptor> descs = new ArrayList<FieldDescriptor>();
        for (final FieldDescriptor descriptor : getDescriptors()) {
            descs.add(descriptor.copy());
        }
        final ArrayList<String> filtersCopy = new ArrayList<String>();
        filtersCopy.addAll(getFilters());

        copy.setDefaultEventName(getDefaultEventName());
        copy.setDescriptors(descs);
        copy.setFileId(getFileId());
        copy.setNoOfHeaderLines(getNoOfHeaderLines());
        copy.setFilters(filtersCopy);
        copy.setSkipEmptyLines(getSkipEmptyLines());
        return copy;
    }
}
