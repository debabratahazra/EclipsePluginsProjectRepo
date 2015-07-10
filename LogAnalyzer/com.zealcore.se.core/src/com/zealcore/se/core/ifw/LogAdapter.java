package com.zealcore.se.core.ifw;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;

public class LogAdapter implements ILogAdapter {

    private IImporter importer;

    private ImporterDescriptor descriptor;

    private boolean isSet;

    private ImportContext context;

    private IFile file;

    public LogAdapter(final ImporterDescriptor desc) {
        descriptor = desc;
    }

    public boolean canRead(final IFile file) {
        if (!file.exists()) {
            return false;
        }
        try {
            return getImporter(false).canRead(toFile(file));
        } catch(Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public Iterable<IArtifact> getArtifacts() {
        // FIXME Consider using try-catch semantics here
        Iterable<IArtifact> artifacts = getImporter(true).getArtifacts();
        return artifacts == null ? Collections.EMPTY_LIST : artifacts;
    }

    public IFile getFile() {
        return file;
    }

    public String getId() {
        return descriptor.getId();
    }

    @SuppressWarnings("unchecked")
    public Iterable<ILogEvent> getLogEvents() {
        try {
            Iterable<ILogEvent> events = getImporter(true).getLogEvents();
            return events == null ? Collections.EMPTY_LIST : events;
        } catch(Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    public String getName() {
        return descriptor.getName();
    }

    public ILogAdapter setContext(final ImportContext context) {
        this.context = context;
        file = context.getResource();
        if (file == null) {
            throw new IllegalStateException(
                    "Context resource is null! Importer is " + context);
        }
        return this;
    }

    public int size() {
        /*
         * TODO Consider using try-catch semantics here TODO Once a full import
         * has been made, this logadapter knows the actual size of the logfile
         * and can use this value
         */
        return (int) context.getLog().getSize();
    }

    @Override
    public String toString() {
        return getName();
    }

    IImporter getImporter(final boolean setLog) {

        if (importer == null) {
            try {
                importer = descriptor.createImporter();
            } catch (CoreException e) {
                // FIXME This error is unlikely, but expected. So the user
                // should be made aware of it
                SeCorePlugin.logError(e);
                importer = new EmptyImporter();
            }
        }
        if (setLog && !isSet) {
            isSet = true;
            importer.setContext(getContext());
        }
        return importer;
    }

    ImportContext getContext() {
        return context;
    }

    private File toFile(final IFile file) {
        return file.getRawLocation().toFile();
    }

    private static class EmptyImporter implements IImporter {
        public boolean canRead(final File file) {
            return false;
        }

        public void setContext(final ImportContext context) {}

        public Iterable<ILogEvent> getLogEvents() {
           return Collections.emptyList();
        }

        public Iterable<IArtifact> getArtifacts() {
            return Collections.emptyList();
        }

        public int size() {
            return 0;
        }
    }

    public void close() {
        IImporter reader = getImporter(true);
        if (reader instanceof Closeable) {
            Closeable closable = (Closeable) reader;
            try {
                closable.close();
            } catch (IOException e) {
                SeCorePlugin.logError(e);
            }
        }
    }
}
