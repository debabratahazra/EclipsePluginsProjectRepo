package com.zealcore.se.core.ifw;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;

public class ExtendedLogAdapter implements ILogAdapter {
    private final ImporterDescriptor descriptor;

    private IExtendedImporter importer;

    private ImportContext context;

    private IFile file;

    private boolean isSet;

    public ExtendedLogAdapter(final ImporterDescriptor desc) {
        descriptor = desc;
    }

    public boolean canRead(final IFile file) {
        if (!file.exists()) {
            return false;
        }
        // FIXME: Consider using try-catch semantics here.
        return getImporter(false).canRead(toFile(file));
    }

    public String getName() {
        return descriptor.getName();
    }

    public String getId() {
        return descriptor.getId();
    }

    public IFile getFile() {
        return file;
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

    public ImportContext getContext() {
        return context;
    }

    public IExtendedImporter getImporter(final boolean setLog) {
        if (importer == null) {
            try {
                importer = descriptor.createExtendedImporter();
            } catch (CoreException e) {
                // FIXME: This error is unlikely, but expected. So the user
                // should be made aware of it.
                SeCorePlugin.logError(e);
                importer = new EmptyExtendedImporter();
            }
        }
        if (setLog && !isSet) {
            isSet = true;
            importer.setContext(getContext());
        }
        return importer;
    }

    public int size() {
        /* TODO: Once a full import has been made, this log adapter knows the
         * actual size of the logfile and can use this value.
         */
        // TODO Consider using try-catch semantics here.
        return (int) context.getLog().getSize();
    }

    public Iterable<ILogEvent> getLogEvents() {
        return Collections.emptyList();
    }

    public Iterable<IArtifact> getArtifacts() {
        return Collections.emptyList();
    }

    public void close() {
        IExtendedImporter reader = getImporter(true);
        if (reader instanceof Closeable) {
            Closeable closable = (Closeable) reader;
            try {
                closable.close();
            } catch (IOException e) {
                SeCorePlugin.logError(e);
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    private static File toFile(final IFile file) {
        return file.getRawLocation().toFile();
    }

    private static class EmptyExtendedImporter implements IExtendedImporter {
        public boolean canRead(final File file) {
            return false;
        }

        public void setContext(final ImportContext context) {}

        public Iterable<TypeDescription> getTypeDescriptions() {
            return Collections.emptyList();
        }

        public Iterable<GenericEventInfo> getEvents() {
            return Collections.emptyList();
        }

        public Iterable<GenericArtifactInfo> getArtifacts() {
            return Collections.emptyList();
        }
    }
}
